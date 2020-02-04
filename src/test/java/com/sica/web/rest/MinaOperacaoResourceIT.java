package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.MinaOperacao;
import com.sica.repository.MinaOperacaoRepository;
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

import com.sica.domain.enumeration.TipoOperacao;
/**
 * Integration tests for the {@Link MinaOperacaoResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class MinaOperacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SEGMENTO = "AAAAAAAAAA";
    private static final String UPDATED_SEGMENTO = "BBBBBBBBBB";

    private static final TipoOperacao DEFAULT_TIPO_OPERACAO = TipoOperacao.MINERIO_FERRO_PELOTAS;
    private static final TipoOperacao UPDATED_TIPO_OPERACAO = TipoOperacao.NIQUEL;

    @Autowired
    private MinaOperacaoRepository minaOperacaoRepository;

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

    private MockMvc restMinaOperacaoMockMvc;

    private MinaOperacao minaOperacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MinaOperacaoResource minaOperacaoResource = new MinaOperacaoResource(minaOperacaoRepository);
        this.restMinaOperacaoMockMvc = MockMvcBuilders.standaloneSetup(minaOperacaoResource)
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
    public static MinaOperacao createEntity(EntityManager em) {
        MinaOperacao minaOperacao = new MinaOperacao()
            .nome(DEFAULT_NOME)
            .segmento(DEFAULT_SEGMENTO)
            .tipoOperacao(DEFAULT_TIPO_OPERACAO);
        return minaOperacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MinaOperacao createUpdatedEntity(EntityManager em) {
        MinaOperacao minaOperacao = new MinaOperacao()
            .nome(UPDATED_NOME)
            .segmento(UPDATED_SEGMENTO)
            .tipoOperacao(UPDATED_TIPO_OPERACAO);
        return minaOperacao;
    }

    @BeforeEach
    public void initTest() {
        minaOperacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createMinaOperacao() throws Exception {
        int databaseSizeBeforeCreate = minaOperacaoRepository.findAll().size();

        // Create the MinaOperacao
        restMinaOperacaoMockMvc.perform(post("/api/mina-operacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(minaOperacao)))
            .andExpect(status().isCreated());

        // Validate the MinaOperacao in the database
        List<MinaOperacao> minaOperacaoList = minaOperacaoRepository.findAll();
        assertThat(minaOperacaoList).hasSize(databaseSizeBeforeCreate + 1);
        MinaOperacao testMinaOperacao = minaOperacaoList.get(minaOperacaoList.size() - 1);
        assertThat(testMinaOperacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMinaOperacao.getSegmento()).isEqualTo(DEFAULT_SEGMENTO);
        assertThat(testMinaOperacao.getTipoOperacao()).isEqualTo(DEFAULT_TIPO_OPERACAO);
    }

    @Test
    @Transactional
    public void createMinaOperacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = minaOperacaoRepository.findAll().size();

        // Create the MinaOperacao with an existing ID
        minaOperacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMinaOperacaoMockMvc.perform(post("/api/mina-operacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(minaOperacao)))
            .andExpect(status().isBadRequest());

        // Validate the MinaOperacao in the database
        List<MinaOperacao> minaOperacaoList = minaOperacaoRepository.findAll();
        assertThat(minaOperacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = minaOperacaoRepository.findAll().size();
        // set the field null
        minaOperacao.setNome(null);

        // Create the MinaOperacao, which fails.

        restMinaOperacaoMockMvc.perform(post("/api/mina-operacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(minaOperacao)))
            .andExpect(status().isBadRequest());

        List<MinaOperacao> minaOperacaoList = minaOperacaoRepository.findAll();
        assertThat(minaOperacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSegmentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = minaOperacaoRepository.findAll().size();
        // set the field null
        minaOperacao.setSegmento(null);

        // Create the MinaOperacao, which fails.

        restMinaOperacaoMockMvc.perform(post("/api/mina-operacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(minaOperacao)))
            .andExpect(status().isBadRequest());

        List<MinaOperacao> minaOperacaoList = minaOperacaoRepository.findAll();
        assertThat(minaOperacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoOperacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = minaOperacaoRepository.findAll().size();
        // set the field null
        minaOperacao.setTipoOperacao(null);

        // Create the MinaOperacao, which fails.

        restMinaOperacaoMockMvc.perform(post("/api/mina-operacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(minaOperacao)))
            .andExpect(status().isBadRequest());

        List<MinaOperacao> minaOperacaoList = minaOperacaoRepository.findAll();
        assertThat(minaOperacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMinaOperacaos() throws Exception {
        // Initialize the database
        minaOperacaoRepository.saveAndFlush(minaOperacao);

        // Get all the minaOperacaoList
        restMinaOperacaoMockMvc.perform(get("/api/mina-operacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(minaOperacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].segmento").value(hasItem(DEFAULT_SEGMENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoOperacao").value(hasItem(DEFAULT_TIPO_OPERACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getMinaOperacao() throws Exception {
        // Initialize the database
        minaOperacaoRepository.saveAndFlush(minaOperacao);

        // Get the minaOperacao
        restMinaOperacaoMockMvc.perform(get("/api/mina-operacaos/{id}", minaOperacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(minaOperacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.segmento").value(DEFAULT_SEGMENTO.toString()))
            .andExpect(jsonPath("$.tipoOperacao").value(DEFAULT_TIPO_OPERACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMinaOperacao() throws Exception {
        // Get the minaOperacao
        restMinaOperacaoMockMvc.perform(get("/api/mina-operacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMinaOperacao() throws Exception {
        // Initialize the database
        minaOperacaoRepository.saveAndFlush(minaOperacao);

        int databaseSizeBeforeUpdate = minaOperacaoRepository.findAll().size();

        // Update the minaOperacao
        MinaOperacao updatedMinaOperacao = minaOperacaoRepository.findById(minaOperacao.getId()).get();
        // Disconnect from session so that the updates on updatedMinaOperacao are not directly saved in db
        em.detach(updatedMinaOperacao);
        updatedMinaOperacao
            .nome(UPDATED_NOME)
            .segmento(UPDATED_SEGMENTO)
            .tipoOperacao(UPDATED_TIPO_OPERACAO);

        restMinaOperacaoMockMvc.perform(put("/api/mina-operacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMinaOperacao)))
            .andExpect(status().isOk());

        // Validate the MinaOperacao in the database
        List<MinaOperacao> minaOperacaoList = minaOperacaoRepository.findAll();
        assertThat(minaOperacaoList).hasSize(databaseSizeBeforeUpdate);
        MinaOperacao testMinaOperacao = minaOperacaoList.get(minaOperacaoList.size() - 1);
        assertThat(testMinaOperacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMinaOperacao.getSegmento()).isEqualTo(UPDATED_SEGMENTO);
        assertThat(testMinaOperacao.getTipoOperacao()).isEqualTo(UPDATED_TIPO_OPERACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingMinaOperacao() throws Exception {
        int databaseSizeBeforeUpdate = minaOperacaoRepository.findAll().size();

        // Create the MinaOperacao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMinaOperacaoMockMvc.perform(put("/api/mina-operacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(minaOperacao)))
            .andExpect(status().isBadRequest());

        // Validate the MinaOperacao in the database
        List<MinaOperacao> minaOperacaoList = minaOperacaoRepository.findAll();
        assertThat(minaOperacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMinaOperacao() throws Exception {
        // Initialize the database
        minaOperacaoRepository.saveAndFlush(minaOperacao);

        int databaseSizeBeforeDelete = minaOperacaoRepository.findAll().size();

        // Delete the minaOperacao
        restMinaOperacaoMockMvc.perform(delete("/api/mina-operacaos/{id}", minaOperacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<MinaOperacao> minaOperacaoList = minaOperacaoRepository.findAll();
        assertThat(minaOperacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MinaOperacao.class);
        MinaOperacao minaOperacao1 = new MinaOperacao();
        minaOperacao1.setId(1L);
        MinaOperacao minaOperacao2 = new MinaOperacao();
        minaOperacao2.setId(minaOperacao1.getId());
        assertThat(minaOperacao1).isEqualTo(minaOperacao2);
        minaOperacao2.setId(2L);
        assertThat(minaOperacao1).isNotEqualTo(minaOperacao2);
        minaOperacao1.setId(null);
        assertThat(minaOperacao1).isNotEqualTo(minaOperacao2);
    }
}
