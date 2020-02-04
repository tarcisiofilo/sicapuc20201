package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.NivelIncidente;
import com.sica.repository.NivelIncidenteRepository;
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
 * Integration tests for the {@Link NivelIncidenteResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class NivelIncidenteResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NOTIFICA_DNPM = false;
    private static final Boolean UPDATED_NOTIFICA_DNPM = true;

    private static final Boolean DEFAULT_NOTIFICA_EMAIL = false;
    private static final Boolean UPDATED_NOTIFICA_EMAIL = true;

    private static final Boolean DEFAULT_NOTIFICACAO_SMS_WHATSAPP = false;
    private static final Boolean UPDATED_NOTIFICACAO_SMS_WHATSAPP = true;

    private static final Boolean DEFAULT_NOTIFICACAO_DISPOSITIVO_SEGURANCA = false;
    private static final Boolean UPDATED_NOTIFICACAO_DISPOSITIVO_SEGURANCA = true;

    private static final Boolean DEFAULT_NOTIFICA_SIRENE = false;
    private static final Boolean UPDATED_NOTIFICA_SIRENE = true;

    @Autowired
    private NivelIncidenteRepository nivelIncidenteRepository;

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

    private MockMvc restNivelIncidenteMockMvc;

    private NivelIncidente nivelIncidente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NivelIncidenteResource nivelIncidenteResource = new NivelIncidenteResource(nivelIncidenteRepository);
        this.restNivelIncidenteMockMvc = MockMvcBuilders.standaloneSetup(nivelIncidenteResource)
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
    public static NivelIncidente createEntity(EntityManager em) {
        NivelIncidente nivelIncidente = new NivelIncidente()
            .nome(DEFAULT_NOME)
            .notificaDNPM(DEFAULT_NOTIFICA_DNPM)
            .notificaEmail(DEFAULT_NOTIFICA_EMAIL)
            .notificacaoSmsWhatsapp(DEFAULT_NOTIFICACAO_SMS_WHATSAPP)
            .notificacaoDispositivoSeguranca(DEFAULT_NOTIFICACAO_DISPOSITIVO_SEGURANCA)
            .notificaSirene(DEFAULT_NOTIFICA_SIRENE);
        return nivelIncidente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NivelIncidente createUpdatedEntity(EntityManager em) {
        NivelIncidente nivelIncidente = new NivelIncidente()
            .nome(UPDATED_NOME)
            .notificaDNPM(UPDATED_NOTIFICA_DNPM)
            .notificaEmail(UPDATED_NOTIFICA_EMAIL)
            .notificacaoSmsWhatsapp(UPDATED_NOTIFICACAO_SMS_WHATSAPP)
            .notificacaoDispositivoSeguranca(UPDATED_NOTIFICACAO_DISPOSITIVO_SEGURANCA)
            .notificaSirene(UPDATED_NOTIFICA_SIRENE);
        return nivelIncidente;
    }

    @BeforeEach
    public void initTest() {
        nivelIncidente = createEntity(em);
    }

    @Test
    @Transactional
    public void createNivelIncidente() throws Exception {
        int databaseSizeBeforeCreate = nivelIncidenteRepository.findAll().size();

        // Create the NivelIncidente
        restNivelIncidenteMockMvc.perform(post("/api/nivel-incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelIncidente)))
            .andExpect(status().isCreated());

        // Validate the NivelIncidente in the database
        List<NivelIncidente> nivelIncidenteList = nivelIncidenteRepository.findAll();
        assertThat(nivelIncidenteList).hasSize(databaseSizeBeforeCreate + 1);
        NivelIncidente testNivelIncidente = nivelIncidenteList.get(nivelIncidenteList.size() - 1);
        assertThat(testNivelIncidente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testNivelIncidente.isNotificaDNPM()).isEqualTo(DEFAULT_NOTIFICA_DNPM);
        assertThat(testNivelIncidente.isNotificaEmail()).isEqualTo(DEFAULT_NOTIFICA_EMAIL);
        assertThat(testNivelIncidente.isNotificacaoSmsWhatsapp()).isEqualTo(DEFAULT_NOTIFICACAO_SMS_WHATSAPP);
        assertThat(testNivelIncidente.isNotificacaoDispositivoSeguranca()).isEqualTo(DEFAULT_NOTIFICACAO_DISPOSITIVO_SEGURANCA);
        assertThat(testNivelIncidente.isNotificaSirene()).isEqualTo(DEFAULT_NOTIFICA_SIRENE);
    }

    @Test
    @Transactional
    public void createNivelIncidenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nivelIncidenteRepository.findAll().size();

        // Create the NivelIncidente with an existing ID
        nivelIncidente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNivelIncidenteMockMvc.perform(post("/api/nivel-incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelIncidente)))
            .andExpect(status().isBadRequest());

        // Validate the NivelIncidente in the database
        List<NivelIncidente> nivelIncidenteList = nivelIncidenteRepository.findAll();
        assertThat(nivelIncidenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelIncidenteRepository.findAll().size();
        // set the field null
        nivelIncidente.setNome(null);

        // Create the NivelIncidente, which fails.

        restNivelIncidenteMockMvc.perform(post("/api/nivel-incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelIncidente)))
            .andExpect(status().isBadRequest());

        List<NivelIncidente> nivelIncidenteList = nivelIncidenteRepository.findAll();
        assertThat(nivelIncidenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificaDNPMIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelIncidenteRepository.findAll().size();
        // set the field null
        nivelIncidente.setNotificaDNPM(null);

        // Create the NivelIncidente, which fails.

        restNivelIncidenteMockMvc.perform(post("/api/nivel-incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelIncidente)))
            .andExpect(status().isBadRequest());

        List<NivelIncidente> nivelIncidenteList = nivelIncidenteRepository.findAll();
        assertThat(nivelIncidenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificaEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelIncidenteRepository.findAll().size();
        // set the field null
        nivelIncidente.setNotificaEmail(null);

        // Create the NivelIncidente, which fails.

        restNivelIncidenteMockMvc.perform(post("/api/nivel-incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelIncidente)))
            .andExpect(status().isBadRequest());

        List<NivelIncidente> nivelIncidenteList = nivelIncidenteRepository.findAll();
        assertThat(nivelIncidenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificacaoSmsWhatsappIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelIncidenteRepository.findAll().size();
        // set the field null
        nivelIncidente.setNotificacaoSmsWhatsapp(null);

        // Create the NivelIncidente, which fails.

        restNivelIncidenteMockMvc.perform(post("/api/nivel-incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelIncidente)))
            .andExpect(status().isBadRequest());

        List<NivelIncidente> nivelIncidenteList = nivelIncidenteRepository.findAll();
        assertThat(nivelIncidenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificacaoDispositivoSegurancaIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelIncidenteRepository.findAll().size();
        // set the field null
        nivelIncidente.setNotificacaoDispositivoSeguranca(null);

        // Create the NivelIncidente, which fails.

        restNivelIncidenteMockMvc.perform(post("/api/nivel-incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelIncidente)))
            .andExpect(status().isBadRequest());

        List<NivelIncidente> nivelIncidenteList = nivelIncidenteRepository.findAll();
        assertThat(nivelIncidenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificaSireneIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelIncidenteRepository.findAll().size();
        // set the field null
        nivelIncidente.setNotificaSirene(null);

        // Create the NivelIncidente, which fails.

        restNivelIncidenteMockMvc.perform(post("/api/nivel-incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelIncidente)))
            .andExpect(status().isBadRequest());

        List<NivelIncidente> nivelIncidenteList = nivelIncidenteRepository.findAll();
        assertThat(nivelIncidenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNivelIncidentes() throws Exception {
        // Initialize the database
        nivelIncidenteRepository.saveAndFlush(nivelIncidente);

        // Get all the nivelIncidenteList
        restNivelIncidenteMockMvc.perform(get("/api/nivel-incidentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nivelIncidente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].notificaDNPM").value(hasItem(DEFAULT_NOTIFICA_DNPM.booleanValue())))
            .andExpect(jsonPath("$.[*].notificaEmail").value(hasItem(DEFAULT_NOTIFICA_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].notificacaoSmsWhatsapp").value(hasItem(DEFAULT_NOTIFICACAO_SMS_WHATSAPP.booleanValue())))
            .andExpect(jsonPath("$.[*].notificacaoDispositivoSeguranca").value(hasItem(DEFAULT_NOTIFICACAO_DISPOSITIVO_SEGURANCA.booleanValue())))
            .andExpect(jsonPath("$.[*].notificaSirene").value(hasItem(DEFAULT_NOTIFICA_SIRENE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getNivelIncidente() throws Exception {
        // Initialize the database
        nivelIncidenteRepository.saveAndFlush(nivelIncidente);

        // Get the nivelIncidente
        restNivelIncidenteMockMvc.perform(get("/api/nivel-incidentes/{id}", nivelIncidente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nivelIncidente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.notificaDNPM").value(DEFAULT_NOTIFICA_DNPM.booleanValue()))
            .andExpect(jsonPath("$.notificaEmail").value(DEFAULT_NOTIFICA_EMAIL.booleanValue()))
            .andExpect(jsonPath("$.notificacaoSmsWhatsapp").value(DEFAULT_NOTIFICACAO_SMS_WHATSAPP.booleanValue()))
            .andExpect(jsonPath("$.notificacaoDispositivoSeguranca").value(DEFAULT_NOTIFICACAO_DISPOSITIVO_SEGURANCA.booleanValue()))
            .andExpect(jsonPath("$.notificaSirene").value(DEFAULT_NOTIFICA_SIRENE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNivelIncidente() throws Exception {
        // Get the nivelIncidente
        restNivelIncidenteMockMvc.perform(get("/api/nivel-incidentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNivelIncidente() throws Exception {
        // Initialize the database
        nivelIncidenteRepository.saveAndFlush(nivelIncidente);

        int databaseSizeBeforeUpdate = nivelIncidenteRepository.findAll().size();

        // Update the nivelIncidente
        NivelIncidente updatedNivelIncidente = nivelIncidenteRepository.findById(nivelIncidente.getId()).get();
        // Disconnect from session so that the updates on updatedNivelIncidente are not directly saved in db
        em.detach(updatedNivelIncidente);
        updatedNivelIncidente
            .nome(UPDATED_NOME)
            .notificaDNPM(UPDATED_NOTIFICA_DNPM)
            .notificaEmail(UPDATED_NOTIFICA_EMAIL)
            .notificacaoSmsWhatsapp(UPDATED_NOTIFICACAO_SMS_WHATSAPP)
            .notificacaoDispositivoSeguranca(UPDATED_NOTIFICACAO_DISPOSITIVO_SEGURANCA)
            .notificaSirene(UPDATED_NOTIFICA_SIRENE);

        restNivelIncidenteMockMvc.perform(put("/api/nivel-incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNivelIncidente)))
            .andExpect(status().isOk());

        // Validate the NivelIncidente in the database
        List<NivelIncidente> nivelIncidenteList = nivelIncidenteRepository.findAll();
        assertThat(nivelIncidenteList).hasSize(databaseSizeBeforeUpdate);
        NivelIncidente testNivelIncidente = nivelIncidenteList.get(nivelIncidenteList.size() - 1);
        assertThat(testNivelIncidente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testNivelIncidente.isNotificaDNPM()).isEqualTo(UPDATED_NOTIFICA_DNPM);
        assertThat(testNivelIncidente.isNotificaEmail()).isEqualTo(UPDATED_NOTIFICA_EMAIL);
        assertThat(testNivelIncidente.isNotificacaoSmsWhatsapp()).isEqualTo(UPDATED_NOTIFICACAO_SMS_WHATSAPP);
        assertThat(testNivelIncidente.isNotificacaoDispositivoSeguranca()).isEqualTo(UPDATED_NOTIFICACAO_DISPOSITIVO_SEGURANCA);
        assertThat(testNivelIncidente.isNotificaSirene()).isEqualTo(UPDATED_NOTIFICA_SIRENE);
    }

    @Test
    @Transactional
    public void updateNonExistingNivelIncidente() throws Exception {
        int databaseSizeBeforeUpdate = nivelIncidenteRepository.findAll().size();

        // Create the NivelIncidente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNivelIncidenteMockMvc.perform(put("/api/nivel-incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelIncidente)))
            .andExpect(status().isBadRequest());

        // Validate the NivelIncidente in the database
        List<NivelIncidente> nivelIncidenteList = nivelIncidenteRepository.findAll();
        assertThat(nivelIncidenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNivelIncidente() throws Exception {
        // Initialize the database
        nivelIncidenteRepository.saveAndFlush(nivelIncidente);

        int databaseSizeBeforeDelete = nivelIncidenteRepository.findAll().size();

        // Delete the nivelIncidente
        restNivelIncidenteMockMvc.perform(delete("/api/nivel-incidentes/{id}", nivelIncidente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<NivelIncidente> nivelIncidenteList = nivelIncidenteRepository.findAll();
        assertThat(nivelIncidenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NivelIncidente.class);
        NivelIncidente nivelIncidente1 = new NivelIncidente();
        nivelIncidente1.setId(1L);
        NivelIncidente nivelIncidente2 = new NivelIncidente();
        nivelIncidente2.setId(nivelIncidente1.getId());
        assertThat(nivelIncidente1).isEqualTo(nivelIncidente2);
        nivelIncidente2.setId(2L);
        assertThat(nivelIncidente1).isNotEqualTo(nivelIncidente2);
        nivelIncidente1.setId(null);
        assertThat(nivelIncidente1).isNotEqualTo(nivelIncidente2);
    }
}
