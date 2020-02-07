package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.Familia;
import com.sica.repository.FamiliaRepository;
import com.sica.service.FamiliaService;
import com.sica.service.dto.FamiliaDTO;
import com.sica.service.mapper.FamiliaMapper;
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
 * Integration tests for the {@Link FamiliaResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class FamiliaResourceIT {

    private static final String DEFAULT_IDENTIFICACAO = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACAO = "BBBBBBBBBB";

    @Autowired
    private FamiliaRepository familiaRepository;

    @Autowired
    private FamiliaMapper familiaMapper;

    @Autowired
    private FamiliaService familiaService;

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

    private MockMvc restFamiliaMockMvc;

    private Familia familia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamiliaResource familiaResource = new FamiliaResource(familiaService);
        this.restFamiliaMockMvc = MockMvcBuilders.standaloneSetup(familiaResource)
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
    public static Familia createEntity(EntityManager em) {
        Familia familia = new Familia()
            .identificacao(DEFAULT_IDENTIFICACAO);
        return familia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Familia createUpdatedEntity(EntityManager em) {
        Familia familia = new Familia()
            .identificacao(UPDATED_IDENTIFICACAO);
        return familia;
    }

    @BeforeEach
    public void initTest() {
        familia = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilia() throws Exception {
        int databaseSizeBeforeCreate = familiaRepository.findAll().size();

        // Create the Familia
        FamiliaDTO familiaDTO = familiaMapper.toDto(familia);
        restFamiliaMockMvc.perform(post("/api/familias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Familia in the database
        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeCreate + 1);
        Familia testFamilia = familiaList.get(familiaList.size() - 1);
        assertThat(testFamilia.getIdentificacao()).isEqualTo(DEFAULT_IDENTIFICACAO);
    }

    @Test
    @Transactional
    public void createFamiliaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familiaRepository.findAll().size();

        // Create the Familia with an existing ID
        familia.setId(1L);
        FamiliaDTO familiaDTO = familiaMapper.toDto(familia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamiliaMockMvc.perform(post("/api/familias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Familia in the database
        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentificacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = familiaRepository.findAll().size();
        // set the field null
        familia.setIdentificacao(null);

        // Create the Familia, which fails.
        FamiliaDTO familiaDTO = familiaMapper.toDto(familia);

        restFamiliaMockMvc.perform(post("/api/familias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiaDTO)))
            .andExpect(status().isBadRequest());

        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFamilias() throws Exception {
        // Initialize the database
        familiaRepository.saveAndFlush(familia);

        // Get all the familiaList
        restFamiliaMockMvc.perform(get("/api/familias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familia.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificacao").value(hasItem(DEFAULT_IDENTIFICACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getFamilia() throws Exception {
        // Initialize the database
        familiaRepository.saveAndFlush(familia);

        // Get the familia
        restFamiliaMockMvc.perform(get("/api/familias/{id}", familia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(familia.getId().intValue()))
            .andExpect(jsonPath("$.identificacao").value(DEFAULT_IDENTIFICACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFamilia() throws Exception {
        // Get the familia
        restFamiliaMockMvc.perform(get("/api/familias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilia() throws Exception {
        // Initialize the database
        familiaRepository.saveAndFlush(familia);

        int databaseSizeBeforeUpdate = familiaRepository.findAll().size();

        // Update the familia
        Familia updatedFamilia = familiaRepository.findById(familia.getId()).get();
        // Disconnect from session so that the updates on updatedFamilia are not directly saved in db
        em.detach(updatedFamilia);
        updatedFamilia
            .identificacao(UPDATED_IDENTIFICACAO);
        FamiliaDTO familiaDTO = familiaMapper.toDto(updatedFamilia);

        restFamiliaMockMvc.perform(put("/api/familias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiaDTO)))
            .andExpect(status().isOk());

        // Validate the Familia in the database
        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeUpdate);
        Familia testFamilia = familiaList.get(familiaList.size() - 1);
        assertThat(testFamilia.getIdentificacao()).isEqualTo(UPDATED_IDENTIFICACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilia() throws Exception {
        int databaseSizeBeforeUpdate = familiaRepository.findAll().size();

        // Create the Familia
        FamiliaDTO familiaDTO = familiaMapper.toDto(familia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamiliaMockMvc.perform(put("/api/familias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Familia in the database
        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFamilia() throws Exception {
        // Initialize the database
        familiaRepository.saveAndFlush(familia);

        int databaseSizeBeforeDelete = familiaRepository.findAll().size();

        // Delete the familia
        restFamiliaMockMvc.perform(delete("/api/familias/{id}", familia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Familia.class);
        Familia familia1 = new Familia();
        familia1.setId(1L);
        Familia familia2 = new Familia();
        familia2.setId(familia1.getId());
        assertThat(familia1).isEqualTo(familia2);
        familia2.setId(2L);
        assertThat(familia1).isNotEqualTo(familia2);
        familia1.setId(null);
        assertThat(familia1).isNotEqualTo(familia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamiliaDTO.class);
        FamiliaDTO familiaDTO1 = new FamiliaDTO();
        familiaDTO1.setId(1L);
        FamiliaDTO familiaDTO2 = new FamiliaDTO();
        assertThat(familiaDTO1).isNotEqualTo(familiaDTO2);
        familiaDTO2.setId(familiaDTO1.getId());
        assertThat(familiaDTO1).isEqualTo(familiaDTO2);
        familiaDTO2.setId(2L);
        assertThat(familiaDTO1).isNotEqualTo(familiaDTO2);
        familiaDTO1.setId(null);
        assertThat(familiaDTO1).isNotEqualTo(familiaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(familiaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(familiaMapper.fromId(null)).isNull();
    }
}
