package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.Incidente;
import com.sica.repository.IncidenteRepository;
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

import com.sica.domain.enumeration.OrigemIncidente;
/**
 * Integration tests for the {@Link IncidenteResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class IncidenteResourceIT {

    private static final String DEFAULT_IDENTIFICACAO = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACAO = "BBBBBBBBBB";

    private static final OrigemIncidente DEFAULT_ORIGEM = OrigemIncidente.MONITORAMENTO_SENSORES;
    private static final OrigemIncidente UPDATED_ORIGEM = OrigemIncidente.MANUAL;

    @Autowired
    private IncidenteRepository incidenteRepository;

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

    private MockMvc restIncidenteMockMvc;

    private Incidente incidente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncidenteResource incidenteResource = new IncidenteResource(incidenteRepository);
        this.restIncidenteMockMvc = MockMvcBuilders.standaloneSetup(incidenteResource)
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
    public static Incidente createEntity(EntityManager em) {
        Incidente incidente = new Incidente()
            .identificacao(DEFAULT_IDENTIFICACAO)
            .origem(DEFAULT_ORIGEM);
        return incidente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incidente createUpdatedEntity(EntityManager em) {
        Incidente incidente = new Incidente()
            .identificacao(UPDATED_IDENTIFICACAO)
            .origem(UPDATED_ORIGEM);
        return incidente;
    }

    @BeforeEach
    public void initTest() {
        incidente = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncidente() throws Exception {
        int databaseSizeBeforeCreate = incidenteRepository.findAll().size();

        // Create the Incidente
        restIncidenteMockMvc.perform(post("/api/incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidente)))
            .andExpect(status().isCreated());

        // Validate the Incidente in the database
        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeCreate + 1);
        Incidente testIncidente = incidenteList.get(incidenteList.size() - 1);
        assertThat(testIncidente.getIdentificacao()).isEqualTo(DEFAULT_IDENTIFICACAO);
        assertThat(testIncidente.getOrigem()).isEqualTo(DEFAULT_ORIGEM);
    }

    @Test
    @Transactional
    public void createIncidenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidenteRepository.findAll().size();

        // Create the Incidente with an existing ID
        incidente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidenteMockMvc.perform(post("/api/incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidente)))
            .andExpect(status().isBadRequest());

        // Validate the Incidente in the database
        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentificacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidenteRepository.findAll().size();
        // set the field null
        incidente.setIdentificacao(null);

        // Create the Incidente, which fails.

        restIncidenteMockMvc.perform(post("/api/incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidente)))
            .andExpect(status().isBadRequest());

        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrigemIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidenteRepository.findAll().size();
        // set the field null
        incidente.setOrigem(null);

        // Create the Incidente, which fails.

        restIncidenteMockMvc.perform(post("/api/incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidente)))
            .andExpect(status().isBadRequest());

        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncidentes() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList
        restIncidenteMockMvc.perform(get("/api/incidentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidente.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificacao").value(hasItem(DEFAULT_IDENTIFICACAO.toString())))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM.toString())));
    }
    
    @Test
    @Transactional
    public void getIncidente() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get the incidente
        restIncidenteMockMvc.perform(get("/api/incidentes/{id}", incidente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incidente.getId().intValue()))
            .andExpect(jsonPath("$.identificacao").value(DEFAULT_IDENTIFICACAO.toString()))
            .andExpect(jsonPath("$.origem").value(DEFAULT_ORIGEM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIncidente() throws Exception {
        // Get the incidente
        restIncidenteMockMvc.perform(get("/api/incidentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncidente() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        int databaseSizeBeforeUpdate = incidenteRepository.findAll().size();

        // Update the incidente
        Incidente updatedIncidente = incidenteRepository.findById(incidente.getId()).get();
        // Disconnect from session so that the updates on updatedIncidente are not directly saved in db
        em.detach(updatedIncidente);
        updatedIncidente
            .identificacao(UPDATED_IDENTIFICACAO)
            .origem(UPDATED_ORIGEM);

        restIncidenteMockMvc.perform(put("/api/incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIncidente)))
            .andExpect(status().isOk());

        // Validate the Incidente in the database
        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeUpdate);
        Incidente testIncidente = incidenteList.get(incidenteList.size() - 1);
        assertThat(testIncidente.getIdentificacao()).isEqualTo(UPDATED_IDENTIFICACAO);
        assertThat(testIncidente.getOrigem()).isEqualTo(UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    public void updateNonExistingIncidente() throws Exception {
        int databaseSizeBeforeUpdate = incidenteRepository.findAll().size();

        // Create the Incidente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidenteMockMvc.perform(put("/api/incidentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidente)))
            .andExpect(status().isBadRequest());

        // Validate the Incidente in the database
        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncidente() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        int databaseSizeBeforeDelete = incidenteRepository.findAll().size();

        // Delete the incidente
        restIncidenteMockMvc.perform(delete("/api/incidentes/{id}", incidente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Incidente> incidenteList = incidenteRepository.findAll();
        assertThat(incidenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incidente.class);
        Incidente incidente1 = new Incidente();
        incidente1.setId(1L);
        Incidente incidente2 = new Incidente();
        incidente2.setId(incidente1.getId());
        assertThat(incidente1).isEqualTo(incidente2);
        incidente2.setId(2L);
        assertThat(incidente1).isNotEqualTo(incidente2);
        incidente1.setId(null);
        assertThat(incidente1).isNotEqualTo(incidente2);
    }
}
