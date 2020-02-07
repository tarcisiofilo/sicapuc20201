package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.AreaSusceptivel;
import com.sica.repository.AreaSusceptivelRepository;
import com.sica.service.AreaSusceptivelService;
import com.sica.service.dto.AreaSusceptivelDTO;
import com.sica.service.mapper.AreaSusceptivelMapper;
import com.sica.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sica.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AreaSusceptivelResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class AreaSusceptivelResourceIT {

    private static final String DEFAULT_IDENTIFICACAO = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACAO = "BBBBBBBBBB";

    private static final Double DEFAULT_NIVEL_PROXIMIDADE = 1D;
    private static final Double UPDATED_NIVEL_PROXIMIDADE = 2D;

    @Autowired
    private AreaSusceptivelRepository areaSusceptivelRepository;

    @Autowired
    private AreaSusceptivelMapper areaSusceptivelMapper;

    @Autowired
    private AreaSusceptivelService areaSusceptivelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAreaSusceptivelMockMvc;

    private AreaSusceptivel areaSusceptivel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AreaSusceptivelResource areaSusceptivelResource = new AreaSusceptivelResource(areaSusceptivelService);
        this.restAreaSusceptivelMockMvc = MockMvcBuilders.standaloneSetup(areaSusceptivelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaSusceptivel createEntity(EntityManager em) {
        AreaSusceptivel areaSusceptivel = new AreaSusceptivel()
            .identificacao(DEFAULT_IDENTIFICACAO)
            .nivelProximidade(DEFAULT_NIVEL_PROXIMIDADE);
        return areaSusceptivel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaSusceptivel createUpdatedEntity(EntityManager em) {
        AreaSusceptivel areaSusceptivel = new AreaSusceptivel()
            .identificacao(UPDATED_IDENTIFICACAO)
            .nivelProximidade(UPDATED_NIVEL_PROXIMIDADE);
        return areaSusceptivel;
    }

    @BeforeEach
    public void initTest() {
        areaSusceptivel = createEntity(em);
    }

    @Test
    @Transactional
    public void createAreaSusceptivel() throws Exception {
        int databaseSizeBeforeCreate = areaSusceptivelRepository.findAll().size();

        // Create the AreaSusceptivel
        AreaSusceptivelDTO areaSusceptivelDTO = areaSusceptivelMapper.toDto(areaSusceptivel);
        restAreaSusceptivelMockMvc.perform(post("/api/area-susceptivels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaSusceptivelDTO)))
            .andExpect(status().isCreated());

        // Validate the AreaSusceptivel in the database
        List<AreaSusceptivel> areaSusceptivelList = areaSusceptivelRepository.findAll();
        assertThat(areaSusceptivelList).hasSize(databaseSizeBeforeCreate + 1);
        AreaSusceptivel testAreaSusceptivel = areaSusceptivelList.get(areaSusceptivelList.size() - 1);
        assertThat(testAreaSusceptivel.getIdentificacao()).isEqualTo(DEFAULT_IDENTIFICACAO);
        assertThat(testAreaSusceptivel.getNivelProximidade()).isEqualTo(DEFAULT_NIVEL_PROXIMIDADE);
    }

    @Test
    @Transactional
    public void createAreaSusceptivelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = areaSusceptivelRepository.findAll().size();

        // Create the AreaSusceptivel with an existing ID
        areaSusceptivel.setId(1L);
        AreaSusceptivelDTO areaSusceptivelDTO = areaSusceptivelMapper.toDto(areaSusceptivel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaSusceptivelMockMvc.perform(post("/api/area-susceptivels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaSusceptivelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AreaSusceptivel in the database
        List<AreaSusceptivel> areaSusceptivelList = areaSusceptivelRepository.findAll();
        assertThat(areaSusceptivelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentificacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaSusceptivelRepository.findAll().size();
        // set the field null
        areaSusceptivel.setIdentificacao(null);

        // Create the AreaSusceptivel, which fails.
        AreaSusceptivelDTO areaSusceptivelDTO = areaSusceptivelMapper.toDto(areaSusceptivel);

        restAreaSusceptivelMockMvc.perform(post("/api/area-susceptivels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaSusceptivelDTO)))
            .andExpect(status().isBadRequest());

        List<AreaSusceptivel> areaSusceptivelList = areaSusceptivelRepository.findAll();
        assertThat(areaSusceptivelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNivelProximidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaSusceptivelRepository.findAll().size();
        // set the field null
        areaSusceptivel.setNivelProximidade(null);

        // Create the AreaSusceptivel, which fails.
        AreaSusceptivelDTO areaSusceptivelDTO = areaSusceptivelMapper.toDto(areaSusceptivel);

        restAreaSusceptivelMockMvc.perform(post("/api/area-susceptivels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaSusceptivelDTO)))
            .andExpect(status().isBadRequest());

        List<AreaSusceptivel> areaSusceptivelList = areaSusceptivelRepository.findAll();
        assertThat(areaSusceptivelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAreaSusceptivels() throws Exception {
        // Initialize the database
        areaSusceptivelRepository.saveAndFlush(areaSusceptivel);

        // Get all the areaSusceptivelList
        restAreaSusceptivelMockMvc.perform(get("/api/area-susceptivels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaSusceptivel.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificacao").value(hasItem(DEFAULT_IDENTIFICACAO.toString())))
            .andExpect(jsonPath("$.[*].nivelProximidade").value(hasItem(DEFAULT_NIVEL_PROXIMIDADE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAreaSusceptivel() throws Exception {
        // Initialize the database
        areaSusceptivelRepository.saveAndFlush(areaSusceptivel);

        // Get the areaSusceptivel
        restAreaSusceptivelMockMvc.perform(get("/api/area-susceptivels/{id}", areaSusceptivel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(areaSusceptivel.getId().intValue()))
            .andExpect(jsonPath("$.identificacao").value(DEFAULT_IDENTIFICACAO.toString()))
            .andExpect(jsonPath("$.nivelProximidade").value(DEFAULT_NIVEL_PROXIMIDADE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAreaSusceptivel() throws Exception {
        // Get the areaSusceptivel
        restAreaSusceptivelMockMvc.perform(get("/api/area-susceptivels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAreaSusceptivel() throws Exception {
        // Initialize the database
        areaSusceptivelRepository.saveAndFlush(areaSusceptivel);

        int databaseSizeBeforeUpdate = areaSusceptivelRepository.findAll().size();

        // Update the areaSusceptivel
        AreaSusceptivel updatedAreaSusceptivel = areaSusceptivelRepository.findById(areaSusceptivel.getId()).get();
        // Disconnect from session so that the updates on updatedAreaSusceptivel are not directly saved in db
        em.detach(updatedAreaSusceptivel);
        updatedAreaSusceptivel
            .identificacao(UPDATED_IDENTIFICACAO)
            .nivelProximidade(UPDATED_NIVEL_PROXIMIDADE);
        AreaSusceptivelDTO areaSusceptivelDTO = areaSusceptivelMapper.toDto(updatedAreaSusceptivel);

        restAreaSusceptivelMockMvc.perform(put("/api/area-susceptivels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaSusceptivelDTO)))
            .andExpect(status().isOk());

        // Validate the AreaSusceptivel in the database
        List<AreaSusceptivel> areaSusceptivelList = areaSusceptivelRepository.findAll();
        assertThat(areaSusceptivelList).hasSize(databaseSizeBeforeUpdate);
        AreaSusceptivel testAreaSusceptivel = areaSusceptivelList.get(areaSusceptivelList.size() - 1);
        assertThat(testAreaSusceptivel.getIdentificacao()).isEqualTo(UPDATED_IDENTIFICACAO);
        assertThat(testAreaSusceptivel.getNivelProximidade()).isEqualTo(UPDATED_NIVEL_PROXIMIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingAreaSusceptivel() throws Exception {
        int databaseSizeBeforeUpdate = areaSusceptivelRepository.findAll().size();

        // Create the AreaSusceptivel
        AreaSusceptivelDTO areaSusceptivelDTO = areaSusceptivelMapper.toDto(areaSusceptivel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaSusceptivelMockMvc.perform(put("/api/area-susceptivels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaSusceptivelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AreaSusceptivel in the database
        List<AreaSusceptivel> areaSusceptivelList = areaSusceptivelRepository.findAll();
        assertThat(areaSusceptivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAreaSusceptivel() throws Exception {
        // Initialize the database
        areaSusceptivelRepository.saveAndFlush(areaSusceptivel);

        int databaseSizeBeforeDelete = areaSusceptivelRepository.findAll().size();

        // Delete the areaSusceptivel
        restAreaSusceptivelMockMvc.perform(delete("/api/area-susceptivels/{id}", areaSusceptivel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AreaSusceptivel> areaSusceptivelList = areaSusceptivelRepository.findAll();
        assertThat(areaSusceptivelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaSusceptivel.class);
        AreaSusceptivel areaSusceptivel1 = new AreaSusceptivel();
        areaSusceptivel1.setId(1L);
        AreaSusceptivel areaSusceptivel2 = new AreaSusceptivel();
        areaSusceptivel2.setId(areaSusceptivel1.getId());
        assertThat(areaSusceptivel1).isEqualTo(areaSusceptivel2);
        areaSusceptivel2.setId(2L);
        assertThat(areaSusceptivel1).isNotEqualTo(areaSusceptivel2);
        areaSusceptivel1.setId(null);
        assertThat(areaSusceptivel1).isNotEqualTo(areaSusceptivel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaSusceptivelDTO.class);
        AreaSusceptivelDTO areaSusceptivelDTO1 = new AreaSusceptivelDTO();
        areaSusceptivelDTO1.setId(1L);
        AreaSusceptivelDTO areaSusceptivelDTO2 = new AreaSusceptivelDTO();
        assertThat(areaSusceptivelDTO1).isNotEqualTo(areaSusceptivelDTO2);
        areaSusceptivelDTO2.setId(areaSusceptivelDTO1.getId());
        assertThat(areaSusceptivelDTO1).isEqualTo(areaSusceptivelDTO2);
        areaSusceptivelDTO2.setId(2L);
        assertThat(areaSusceptivelDTO1).isNotEqualTo(areaSusceptivelDTO2);
        areaSusceptivelDTO1.setId(null);
        assertThat(areaSusceptivelDTO1).isNotEqualTo(areaSusceptivelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(areaSusceptivelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(areaSusceptivelMapper.fromId(null)).isNull();
    }
}
