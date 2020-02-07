package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.Vistoria;
import com.sica.repository.VistoriaRepository;
import com.sica.service.VistoriaService;
import com.sica.service.dto.VistoriaDTO;
import com.sica.service.mapper.VistoriaMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.sica.web.rest.TestUtil.sameInstant;
import static com.sica.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link VistoriaResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class VistoriaResourceIT {

    private static final String DEFAULT_IDENTIFICAO = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    @Autowired
    private VistoriaRepository vistoriaRepository;

    @Autowired
    private VistoriaMapper vistoriaMapper;

    @Autowired
    private VistoriaService vistoriaService;

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

    private MockMvc restVistoriaMockMvc;

    private Vistoria vistoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VistoriaResource vistoriaResource = new VistoriaResource(vistoriaService);
        this.restVistoriaMockMvc = MockMvcBuilders.standaloneSetup(vistoriaResource)
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
    public static Vistoria createEntity(EntityManager em) {
        Vistoria vistoria = new Vistoria()
            .identificao(DEFAULT_IDENTIFICAO)
            .data(DEFAULT_DATA)
            .valor(DEFAULT_VALOR);
        return vistoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vistoria createUpdatedEntity(EntityManager em) {
        Vistoria vistoria = new Vistoria()
            .identificao(UPDATED_IDENTIFICAO)
            .data(UPDATED_DATA)
            .valor(UPDATED_VALOR);
        return vistoria;
    }

    @BeforeEach
    public void initTest() {
        vistoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createVistoria() throws Exception {
        int databaseSizeBeforeCreate = vistoriaRepository.findAll().size();

        // Create the Vistoria
        VistoriaDTO vistoriaDTO = vistoriaMapper.toDto(vistoria);
        restVistoriaMockMvc.perform(post("/api/vistorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistoriaDTO)))
            .andExpect(status().isCreated());

        // Validate the Vistoria in the database
        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeCreate + 1);
        Vistoria testVistoria = vistoriaList.get(vistoriaList.size() - 1);
        assertThat(testVistoria.getIdentificao()).isEqualTo(DEFAULT_IDENTIFICAO);
        assertThat(testVistoria.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testVistoria.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createVistoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vistoriaRepository.findAll().size();

        // Create the Vistoria with an existing ID
        vistoria.setId(1L);
        VistoriaDTO vistoriaDTO = vistoriaMapper.toDto(vistoria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVistoriaMockMvc.perform(post("/api/vistorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vistoria in the database
        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentificaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = vistoriaRepository.findAll().size();
        // set the field null
        vistoria.setIdentificao(null);

        // Create the Vistoria, which fails.
        VistoriaDTO vistoriaDTO = vistoriaMapper.toDto(vistoria);

        restVistoriaMockMvc.perform(post("/api/vistorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistoriaDTO)))
            .andExpect(status().isBadRequest());

        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = vistoriaRepository.findAll().size();
        // set the field null
        vistoria.setData(null);

        // Create the Vistoria, which fails.
        VistoriaDTO vistoriaDTO = vistoriaMapper.toDto(vistoria);

        restVistoriaMockMvc.perform(post("/api/vistorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistoriaDTO)))
            .andExpect(status().isBadRequest());

        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = vistoriaRepository.findAll().size();
        // set the field null
        vistoria.setValor(null);

        // Create the Vistoria, which fails.
        VistoriaDTO vistoriaDTO = vistoriaMapper.toDto(vistoria);

        restVistoriaMockMvc.perform(post("/api/vistorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistoriaDTO)))
            .andExpect(status().isBadRequest());

        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVistorias() throws Exception {
        // Initialize the database
        vistoriaRepository.saveAndFlush(vistoria);

        // Get all the vistoriaList
        restVistoriaMockMvc.perform(get("/api/vistorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vistoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificao").value(hasItem(DEFAULT_IDENTIFICAO.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getVistoria() throws Exception {
        // Initialize the database
        vistoriaRepository.saveAndFlush(vistoria);

        // Get the vistoria
        restVistoriaMockMvc.perform(get("/api/vistorias/{id}", vistoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vistoria.getId().intValue()))
            .andExpect(jsonPath("$.identificao").value(DEFAULT_IDENTIFICAO.toString()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVistoria() throws Exception {
        // Get the vistoria
        restVistoriaMockMvc.perform(get("/api/vistorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVistoria() throws Exception {
        // Initialize the database
        vistoriaRepository.saveAndFlush(vistoria);

        int databaseSizeBeforeUpdate = vistoriaRepository.findAll().size();

        // Update the vistoria
        Vistoria updatedVistoria = vistoriaRepository.findById(vistoria.getId()).get();
        // Disconnect from session so that the updates on updatedVistoria are not directly saved in db
        em.detach(updatedVistoria);
        updatedVistoria
            .identificao(UPDATED_IDENTIFICAO)
            .data(UPDATED_DATA)
            .valor(UPDATED_VALOR);
        VistoriaDTO vistoriaDTO = vistoriaMapper.toDto(updatedVistoria);

        restVistoriaMockMvc.perform(put("/api/vistorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistoriaDTO)))
            .andExpect(status().isOk());

        // Validate the Vistoria in the database
        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeUpdate);
        Vistoria testVistoria = vistoriaList.get(vistoriaList.size() - 1);
        assertThat(testVistoria.getIdentificao()).isEqualTo(UPDATED_IDENTIFICAO);
        assertThat(testVistoria.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testVistoria.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingVistoria() throws Exception {
        int databaseSizeBeforeUpdate = vistoriaRepository.findAll().size();

        // Create the Vistoria
        VistoriaDTO vistoriaDTO = vistoriaMapper.toDto(vistoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVistoriaMockMvc.perform(put("/api/vistorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vistoria in the database
        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVistoria() throws Exception {
        // Initialize the database
        vistoriaRepository.saveAndFlush(vistoria);

        int databaseSizeBeforeDelete = vistoriaRepository.findAll().size();

        // Delete the vistoria
        restVistoriaMockMvc.perform(delete("/api/vistorias/{id}", vistoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vistoria.class);
        Vistoria vistoria1 = new Vistoria();
        vistoria1.setId(1L);
        Vistoria vistoria2 = new Vistoria();
        vistoria2.setId(vistoria1.getId());
        assertThat(vistoria1).isEqualTo(vistoria2);
        vistoria2.setId(2L);
        assertThat(vistoria1).isNotEqualTo(vistoria2);
        vistoria1.setId(null);
        assertThat(vistoria1).isNotEqualTo(vistoria2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VistoriaDTO.class);
        VistoriaDTO vistoriaDTO1 = new VistoriaDTO();
        vistoriaDTO1.setId(1L);
        VistoriaDTO vistoriaDTO2 = new VistoriaDTO();
        assertThat(vistoriaDTO1).isNotEqualTo(vistoriaDTO2);
        vistoriaDTO2.setId(vistoriaDTO1.getId());
        assertThat(vistoriaDTO1).isEqualTo(vistoriaDTO2);
        vistoriaDTO2.setId(2L);
        assertThat(vistoriaDTO1).isNotEqualTo(vistoriaDTO2);
        vistoriaDTO1.setId(null);
        assertThat(vistoriaDTO1).isNotEqualTo(vistoriaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vistoriaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vistoriaMapper.fromId(null)).isNull();
    }
}
