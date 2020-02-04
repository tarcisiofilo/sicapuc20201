package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.Ativo;
import com.sica.repository.AtivoRepository;
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
 * Integration tests for the {@Link AtivoResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class AtivoResourceIT {

    private static final Long DEFAULT_ID_TIPO_ATIVO = 1L;
    private static final Long UPDATED_ID_TIPO_ATIVO = 2L;

    private static final String DEFAULT_TIPO_ATIVO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ATIVO = "BBBBBBBBBB";

    private static final Long DEFAULT_PERIODICIDADE_DIAS_MANUTENCAO = 1L;
    private static final Long UPDATED_PERIODICIDADE_DIAS_MANUTENCAO = 2L;

    @Autowired
    private AtivoRepository ativoRepository;

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

    private MockMvc restAtivoMockMvc;

    private Ativo ativo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AtivoResource ativoResource = new AtivoResource(ativoRepository);
        this.restAtivoMockMvc = MockMvcBuilders.standaloneSetup(ativoResource)
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
    public static Ativo createEntity(EntityManager em) {
        Ativo ativo = new Ativo()
            .idTipoAtivo(DEFAULT_ID_TIPO_ATIVO)
            .tipoAtivo(DEFAULT_TIPO_ATIVO)
            .periodicidadeDiasManutencao(DEFAULT_PERIODICIDADE_DIAS_MANUTENCAO);
        return ativo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ativo createUpdatedEntity(EntityManager em) {
        Ativo ativo = new Ativo()
            .idTipoAtivo(UPDATED_ID_TIPO_ATIVO)
            .tipoAtivo(UPDATED_TIPO_ATIVO)
            .periodicidadeDiasManutencao(UPDATED_PERIODICIDADE_DIAS_MANUTENCAO);
        return ativo;
    }

    @BeforeEach
    public void initTest() {
        ativo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAtivo() throws Exception {
        int databaseSizeBeforeCreate = ativoRepository.findAll().size();

        // Create the Ativo
        restAtivoMockMvc.perform(post("/api/ativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ativo)))
            .andExpect(status().isCreated());

        // Validate the Ativo in the database
        List<Ativo> ativoList = ativoRepository.findAll();
        assertThat(ativoList).hasSize(databaseSizeBeforeCreate + 1);
        Ativo testAtivo = ativoList.get(ativoList.size() - 1);
        assertThat(testAtivo.getIdTipoAtivo()).isEqualTo(DEFAULT_ID_TIPO_ATIVO);
        assertThat(testAtivo.getTipoAtivo()).isEqualTo(DEFAULT_TIPO_ATIVO);
        assertThat(testAtivo.getPeriodicidadeDiasManutencao()).isEqualTo(DEFAULT_PERIODICIDADE_DIAS_MANUTENCAO);
    }

    @Test
    @Transactional
    public void createAtivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ativoRepository.findAll().size();

        // Create the Ativo with an existing ID
        ativo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtivoMockMvc.perform(post("/api/ativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ativo)))
            .andExpect(status().isBadRequest());

        // Validate the Ativo in the database
        List<Ativo> ativoList = ativoRepository.findAll();
        assertThat(ativoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdTipoAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ativoRepository.findAll().size();
        // set the field null
        ativo.setIdTipoAtivo(null);

        // Create the Ativo, which fails.

        restAtivoMockMvc.perform(post("/api/ativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ativo)))
            .andExpect(status().isBadRequest());

        List<Ativo> ativoList = ativoRepository.findAll();
        assertThat(ativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ativoRepository.findAll().size();
        // set the field null
        ativo.setTipoAtivo(null);

        // Create the Ativo, which fails.

        restAtivoMockMvc.perform(post("/api/ativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ativo)))
            .andExpect(status().isBadRequest());

        List<Ativo> ativoList = ativoRepository.findAll();
        assertThat(ativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeriodicidadeDiasManutencaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ativoRepository.findAll().size();
        // set the field null
        ativo.setPeriodicidadeDiasManutencao(null);

        // Create the Ativo, which fails.

        restAtivoMockMvc.perform(post("/api/ativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ativo)))
            .andExpect(status().isBadRequest());

        List<Ativo> ativoList = ativoRepository.findAll();
        assertThat(ativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAtivos() throws Exception {
        // Initialize the database
        ativoRepository.saveAndFlush(ativo);

        // Get all the ativoList
        restAtivoMockMvc.perform(get("/api/ativos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ativo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idTipoAtivo").value(hasItem(DEFAULT_ID_TIPO_ATIVO.intValue())))
            .andExpect(jsonPath("$.[*].tipoAtivo").value(hasItem(DEFAULT_TIPO_ATIVO.toString())))
            .andExpect(jsonPath("$.[*].periodicidadeDiasManutencao").value(hasItem(DEFAULT_PERIODICIDADE_DIAS_MANUTENCAO.intValue())));
    }
    
    @Test
    @Transactional
    public void getAtivo() throws Exception {
        // Initialize the database
        ativoRepository.saveAndFlush(ativo);

        // Get the ativo
        restAtivoMockMvc.perform(get("/api/ativos/{id}", ativo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ativo.getId().intValue()))
            .andExpect(jsonPath("$.idTipoAtivo").value(DEFAULT_ID_TIPO_ATIVO.intValue()))
            .andExpect(jsonPath("$.tipoAtivo").value(DEFAULT_TIPO_ATIVO.toString()))
            .andExpect(jsonPath("$.periodicidadeDiasManutencao").value(DEFAULT_PERIODICIDADE_DIAS_MANUTENCAO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAtivo() throws Exception {
        // Get the ativo
        restAtivoMockMvc.perform(get("/api/ativos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtivo() throws Exception {
        // Initialize the database
        ativoRepository.saveAndFlush(ativo);

        int databaseSizeBeforeUpdate = ativoRepository.findAll().size();

        // Update the ativo
        Ativo updatedAtivo = ativoRepository.findById(ativo.getId()).get();
        // Disconnect from session so that the updates on updatedAtivo are not directly saved in db
        em.detach(updatedAtivo);
        updatedAtivo
            .idTipoAtivo(UPDATED_ID_TIPO_ATIVO)
            .tipoAtivo(UPDATED_TIPO_ATIVO)
            .periodicidadeDiasManutencao(UPDATED_PERIODICIDADE_DIAS_MANUTENCAO);

        restAtivoMockMvc.perform(put("/api/ativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAtivo)))
            .andExpect(status().isOk());

        // Validate the Ativo in the database
        List<Ativo> ativoList = ativoRepository.findAll();
        assertThat(ativoList).hasSize(databaseSizeBeforeUpdate);
        Ativo testAtivo = ativoList.get(ativoList.size() - 1);
        assertThat(testAtivo.getIdTipoAtivo()).isEqualTo(UPDATED_ID_TIPO_ATIVO);
        assertThat(testAtivo.getTipoAtivo()).isEqualTo(UPDATED_TIPO_ATIVO);
        assertThat(testAtivo.getPeriodicidadeDiasManutencao()).isEqualTo(UPDATED_PERIODICIDADE_DIAS_MANUTENCAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAtivo() throws Exception {
        int databaseSizeBeforeUpdate = ativoRepository.findAll().size();

        // Create the Ativo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtivoMockMvc.perform(put("/api/ativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ativo)))
            .andExpect(status().isBadRequest());

        // Validate the Ativo in the database
        List<Ativo> ativoList = ativoRepository.findAll();
        assertThat(ativoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAtivo() throws Exception {
        // Initialize the database
        ativoRepository.saveAndFlush(ativo);

        int databaseSizeBeforeDelete = ativoRepository.findAll().size();

        // Delete the ativo
        restAtivoMockMvc.perform(delete("/api/ativos/{id}", ativo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Ativo> ativoList = ativoRepository.findAll();
        assertThat(ativoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ativo.class);
        Ativo ativo1 = new Ativo();
        ativo1.setId(1L);
        Ativo ativo2 = new Ativo();
        ativo2.setId(ativo1.getId());
        assertThat(ativo1).isEqualTo(ativo2);
        ativo2.setId(2L);
        assertThat(ativo1).isNotEqualTo(ativo2);
        ativo1.setId(null);
        assertThat(ativo1).isNotEqualTo(ativo2);
    }
}
