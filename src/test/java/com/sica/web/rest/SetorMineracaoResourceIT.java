package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.SetorMineracao;
import com.sica.repository.SetorMineracaoRepository;
import com.sica.service.SetorMineracaoService;
import com.sica.service.dto.SetorMineracaoDTO;
import com.sica.service.mapper.SetorMineracaoMapper;
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
 * Integration tests for the {@Link SetorMineracaoResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class SetorMineracaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private SetorMineracaoRepository setorMineracaoRepository;

    @Autowired
    private SetorMineracaoMapper setorMineracaoMapper;

    @Autowired
    private SetorMineracaoService setorMineracaoService;

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

    private MockMvc restSetorMineracaoMockMvc;

    private SetorMineracao setorMineracao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SetorMineracaoResource setorMineracaoResource = new SetorMineracaoResource(setorMineracaoService);
        this.restSetorMineracaoMockMvc = MockMvcBuilders.standaloneSetup(setorMineracaoResource)
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
    public static SetorMineracao createEntity(EntityManager em) {
        SetorMineracao setorMineracao = new SetorMineracao()
            .nome(DEFAULT_NOME);
        return setorMineracao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SetorMineracao createUpdatedEntity(EntityManager em) {
        SetorMineracao setorMineracao = new SetorMineracao()
            .nome(UPDATED_NOME);
        return setorMineracao;
    }

    @BeforeEach
    public void initTest() {
        setorMineracao = createEntity(em);
    }

    @Test
    @Transactional
    public void createSetorMineracao() throws Exception {
        int databaseSizeBeforeCreate = setorMineracaoRepository.findAll().size();

        // Create the SetorMineracao
        SetorMineracaoDTO setorMineracaoDTO = setorMineracaoMapper.toDto(setorMineracao);
        restSetorMineracaoMockMvc.perform(post("/api/setor-mineracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setorMineracaoDTO)))
            .andExpect(status().isCreated());

        // Validate the SetorMineracao in the database
        List<SetorMineracao> setorMineracaoList = setorMineracaoRepository.findAll();
        assertThat(setorMineracaoList).hasSize(databaseSizeBeforeCreate + 1);
        SetorMineracao testSetorMineracao = setorMineracaoList.get(setorMineracaoList.size() - 1);
        assertThat(testSetorMineracao.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createSetorMineracaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = setorMineracaoRepository.findAll().size();

        // Create the SetorMineracao with an existing ID
        setorMineracao.setId(1L);
        SetorMineracaoDTO setorMineracaoDTO = setorMineracaoMapper.toDto(setorMineracao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSetorMineracaoMockMvc.perform(post("/api/setor-mineracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setorMineracaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SetorMineracao in the database
        List<SetorMineracao> setorMineracaoList = setorMineracaoRepository.findAll();
        assertThat(setorMineracaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = setorMineracaoRepository.findAll().size();
        // set the field null
        setorMineracao.setNome(null);

        // Create the SetorMineracao, which fails.
        SetorMineracaoDTO setorMineracaoDTO = setorMineracaoMapper.toDto(setorMineracao);

        restSetorMineracaoMockMvc.perform(post("/api/setor-mineracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setorMineracaoDTO)))
            .andExpect(status().isBadRequest());

        List<SetorMineracao> setorMineracaoList = setorMineracaoRepository.findAll();
        assertThat(setorMineracaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSetorMineracaos() throws Exception {
        // Initialize the database
        setorMineracaoRepository.saveAndFlush(setorMineracao);

        // Get all the setorMineracaoList
        restSetorMineracaoMockMvc.perform(get("/api/setor-mineracaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setorMineracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }
    
    @Test
    @Transactional
    public void getSetorMineracao() throws Exception {
        // Initialize the database
        setorMineracaoRepository.saveAndFlush(setorMineracao);

        // Get the setorMineracao
        restSetorMineracaoMockMvc.perform(get("/api/setor-mineracaos/{id}", setorMineracao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(setorMineracao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSetorMineracao() throws Exception {
        // Get the setorMineracao
        restSetorMineracaoMockMvc.perform(get("/api/setor-mineracaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSetorMineracao() throws Exception {
        // Initialize the database
        setorMineracaoRepository.saveAndFlush(setorMineracao);

        int databaseSizeBeforeUpdate = setorMineracaoRepository.findAll().size();

        // Update the setorMineracao
        SetorMineracao updatedSetorMineracao = setorMineracaoRepository.findById(setorMineracao.getId()).get();
        // Disconnect from session so that the updates on updatedSetorMineracao are not directly saved in db
        em.detach(updatedSetorMineracao);
        updatedSetorMineracao
            .nome(UPDATED_NOME);
        SetorMineracaoDTO setorMineracaoDTO = setorMineracaoMapper.toDto(updatedSetorMineracao);

        restSetorMineracaoMockMvc.perform(put("/api/setor-mineracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setorMineracaoDTO)))
            .andExpect(status().isOk());

        // Validate the SetorMineracao in the database
        List<SetorMineracao> setorMineracaoList = setorMineracaoRepository.findAll();
        assertThat(setorMineracaoList).hasSize(databaseSizeBeforeUpdate);
        SetorMineracao testSetorMineracao = setorMineracaoList.get(setorMineracaoList.size() - 1);
        assertThat(testSetorMineracao.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingSetorMineracao() throws Exception {
        int databaseSizeBeforeUpdate = setorMineracaoRepository.findAll().size();

        // Create the SetorMineracao
        SetorMineracaoDTO setorMineracaoDTO = setorMineracaoMapper.toDto(setorMineracao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSetorMineracaoMockMvc.perform(put("/api/setor-mineracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setorMineracaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SetorMineracao in the database
        List<SetorMineracao> setorMineracaoList = setorMineracaoRepository.findAll();
        assertThat(setorMineracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSetorMineracao() throws Exception {
        // Initialize the database
        setorMineracaoRepository.saveAndFlush(setorMineracao);

        int databaseSizeBeforeDelete = setorMineracaoRepository.findAll().size();

        // Delete the setorMineracao
        restSetorMineracaoMockMvc.perform(delete("/api/setor-mineracaos/{id}", setorMineracao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<SetorMineracao> setorMineracaoList = setorMineracaoRepository.findAll();
        assertThat(setorMineracaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SetorMineracao.class);
        SetorMineracao setorMineracao1 = new SetorMineracao();
        setorMineracao1.setId(1L);
        SetorMineracao setorMineracao2 = new SetorMineracao();
        setorMineracao2.setId(setorMineracao1.getId());
        assertThat(setorMineracao1).isEqualTo(setorMineracao2);
        setorMineracao2.setId(2L);
        assertThat(setorMineracao1).isNotEqualTo(setorMineracao2);
        setorMineracao1.setId(null);
        assertThat(setorMineracao1).isNotEqualTo(setorMineracao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SetorMineracaoDTO.class);
        SetorMineracaoDTO setorMineracaoDTO1 = new SetorMineracaoDTO();
        setorMineracaoDTO1.setId(1L);
        SetorMineracaoDTO setorMineracaoDTO2 = new SetorMineracaoDTO();
        assertThat(setorMineracaoDTO1).isNotEqualTo(setorMineracaoDTO2);
        setorMineracaoDTO2.setId(setorMineracaoDTO1.getId());
        assertThat(setorMineracaoDTO1).isEqualTo(setorMineracaoDTO2);
        setorMineracaoDTO2.setId(2L);
        assertThat(setorMineracaoDTO1).isNotEqualTo(setorMineracaoDTO2);
        setorMineracaoDTO1.setId(null);
        assertThat(setorMineracaoDTO1).isNotEqualTo(setorMineracaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(setorMineracaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(setorMineracaoMapper.fromId(null)).isNull();
    }
}
