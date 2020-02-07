package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.MedicaoInstrumento;
import com.sica.repository.MedicaoInstrumentoRepository;
import com.sica.service.MedicaoInstrumentoService;
import com.sica.service.dto.MedicaoInstrumentoDTO;
import com.sica.service.mapper.MedicaoInstrumentoMapper;
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
 * Integration tests for the {@Link MedicaoInstrumentoResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class MedicaoInstrumentoResourceIT {

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    @Autowired
    private MedicaoInstrumentoRepository medicaoInstrumentoRepository;

    @Autowired
    private MedicaoInstrumentoMapper medicaoInstrumentoMapper;

    @Autowired
    private MedicaoInstrumentoService medicaoInstrumentoService;

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

    private MockMvc restMedicaoInstrumentoMockMvc;

    private MedicaoInstrumento medicaoInstrumento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicaoInstrumentoResource medicaoInstrumentoResource = new MedicaoInstrumentoResource(medicaoInstrumentoService);
        this.restMedicaoInstrumentoMockMvc = MockMvcBuilders.standaloneSetup(medicaoInstrumentoResource)
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
    public static MedicaoInstrumento createEntity(EntityManager em) {
        MedicaoInstrumento medicaoInstrumento = new MedicaoInstrumento()
            .data(DEFAULT_DATA)
            .valor(DEFAULT_VALOR);
        return medicaoInstrumento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicaoInstrumento createUpdatedEntity(EntityManager em) {
        MedicaoInstrumento medicaoInstrumento = new MedicaoInstrumento()
            .data(UPDATED_DATA)
            .valor(UPDATED_VALOR);
        return medicaoInstrumento;
    }

    @BeforeEach
    public void initTest() {
        medicaoInstrumento = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicaoInstrumento() throws Exception {
        int databaseSizeBeforeCreate = medicaoInstrumentoRepository.findAll().size();

        // Create the MedicaoInstrumento
        MedicaoInstrumentoDTO medicaoInstrumentoDTO = medicaoInstrumentoMapper.toDto(medicaoInstrumento);
        restMedicaoInstrumentoMockMvc.perform(post("/api/medicao-instrumentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicaoInstrumentoDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicaoInstrumento in the database
        List<MedicaoInstrumento> medicaoInstrumentoList = medicaoInstrumentoRepository.findAll();
        assertThat(medicaoInstrumentoList).hasSize(databaseSizeBeforeCreate + 1);
        MedicaoInstrumento testMedicaoInstrumento = medicaoInstrumentoList.get(medicaoInstrumentoList.size() - 1);
        assertThat(testMedicaoInstrumento.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testMedicaoInstrumento.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createMedicaoInstrumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicaoInstrumentoRepository.findAll().size();

        // Create the MedicaoInstrumento with an existing ID
        medicaoInstrumento.setId(1L);
        MedicaoInstrumentoDTO medicaoInstrumentoDTO = medicaoInstrumentoMapper.toDto(medicaoInstrumento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicaoInstrumentoMockMvc.perform(post("/api/medicao-instrumentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicaoInstrumentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicaoInstrumento in the database
        List<MedicaoInstrumento> medicaoInstrumentoList = medicaoInstrumentoRepository.findAll();
        assertThat(medicaoInstrumentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicaoInstrumentoRepository.findAll().size();
        // set the field null
        medicaoInstrumento.setData(null);

        // Create the MedicaoInstrumento, which fails.
        MedicaoInstrumentoDTO medicaoInstrumentoDTO = medicaoInstrumentoMapper.toDto(medicaoInstrumento);

        restMedicaoInstrumentoMockMvc.perform(post("/api/medicao-instrumentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicaoInstrumentoDTO)))
            .andExpect(status().isBadRequest());

        List<MedicaoInstrumento> medicaoInstrumentoList = medicaoInstrumentoRepository.findAll();
        assertThat(medicaoInstrumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicaoInstrumentoRepository.findAll().size();
        // set the field null
        medicaoInstrumento.setValor(null);

        // Create the MedicaoInstrumento, which fails.
        MedicaoInstrumentoDTO medicaoInstrumentoDTO = medicaoInstrumentoMapper.toDto(medicaoInstrumento);

        restMedicaoInstrumentoMockMvc.perform(post("/api/medicao-instrumentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicaoInstrumentoDTO)))
            .andExpect(status().isBadRequest());

        List<MedicaoInstrumento> medicaoInstrumentoList = medicaoInstrumentoRepository.findAll();
        assertThat(medicaoInstrumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedicaoInstrumentos() throws Exception {
        // Initialize the database
        medicaoInstrumentoRepository.saveAndFlush(medicaoInstrumento);

        // Get all the medicaoInstrumentoList
        restMedicaoInstrumentoMockMvc.perform(get("/api/medicao-instrumentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicaoInstrumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getMedicaoInstrumento() throws Exception {
        // Initialize the database
        medicaoInstrumentoRepository.saveAndFlush(medicaoInstrumento);

        // Get the medicaoInstrumento
        restMedicaoInstrumentoMockMvc.perform(get("/api/medicao-instrumentos/{id}", medicaoInstrumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicaoInstrumento.getId().intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicaoInstrumento() throws Exception {
        // Get the medicaoInstrumento
        restMedicaoInstrumentoMockMvc.perform(get("/api/medicao-instrumentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicaoInstrumento() throws Exception {
        // Initialize the database
        medicaoInstrumentoRepository.saveAndFlush(medicaoInstrumento);

        int databaseSizeBeforeUpdate = medicaoInstrumentoRepository.findAll().size();

        // Update the medicaoInstrumento
        MedicaoInstrumento updatedMedicaoInstrumento = medicaoInstrumentoRepository.findById(medicaoInstrumento.getId()).get();
        // Disconnect from session so that the updates on updatedMedicaoInstrumento are not directly saved in db
        em.detach(updatedMedicaoInstrumento);
        updatedMedicaoInstrumento
            .data(UPDATED_DATA)
            .valor(UPDATED_VALOR);
        MedicaoInstrumentoDTO medicaoInstrumentoDTO = medicaoInstrumentoMapper.toDto(updatedMedicaoInstrumento);

        restMedicaoInstrumentoMockMvc.perform(put("/api/medicao-instrumentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicaoInstrumentoDTO)))
            .andExpect(status().isOk());

        // Validate the MedicaoInstrumento in the database
        List<MedicaoInstrumento> medicaoInstrumentoList = medicaoInstrumentoRepository.findAll();
        assertThat(medicaoInstrumentoList).hasSize(databaseSizeBeforeUpdate);
        MedicaoInstrumento testMedicaoInstrumento = medicaoInstrumentoList.get(medicaoInstrumentoList.size() - 1);
        assertThat(testMedicaoInstrumento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testMedicaoInstrumento.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicaoInstrumento() throws Exception {
        int databaseSizeBeforeUpdate = medicaoInstrumentoRepository.findAll().size();

        // Create the MedicaoInstrumento
        MedicaoInstrumentoDTO medicaoInstrumentoDTO = medicaoInstrumentoMapper.toDto(medicaoInstrumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicaoInstrumentoMockMvc.perform(put("/api/medicao-instrumentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicaoInstrumentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicaoInstrumento in the database
        List<MedicaoInstrumento> medicaoInstrumentoList = medicaoInstrumentoRepository.findAll();
        assertThat(medicaoInstrumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicaoInstrumento() throws Exception {
        // Initialize the database
        medicaoInstrumentoRepository.saveAndFlush(medicaoInstrumento);

        int databaseSizeBeforeDelete = medicaoInstrumentoRepository.findAll().size();

        // Delete the medicaoInstrumento
        restMedicaoInstrumentoMockMvc.perform(delete("/api/medicao-instrumentos/{id}", medicaoInstrumento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<MedicaoInstrumento> medicaoInstrumentoList = medicaoInstrumentoRepository.findAll();
        assertThat(medicaoInstrumentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicaoInstrumento.class);
        MedicaoInstrumento medicaoInstrumento1 = new MedicaoInstrumento();
        medicaoInstrumento1.setId(1L);
        MedicaoInstrumento medicaoInstrumento2 = new MedicaoInstrumento();
        medicaoInstrumento2.setId(medicaoInstrumento1.getId());
        assertThat(medicaoInstrumento1).isEqualTo(medicaoInstrumento2);
        medicaoInstrumento2.setId(2L);
        assertThat(medicaoInstrumento1).isNotEqualTo(medicaoInstrumento2);
        medicaoInstrumento1.setId(null);
        assertThat(medicaoInstrumento1).isNotEqualTo(medicaoInstrumento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicaoInstrumentoDTO.class);
        MedicaoInstrumentoDTO medicaoInstrumentoDTO1 = new MedicaoInstrumentoDTO();
        medicaoInstrumentoDTO1.setId(1L);
        MedicaoInstrumentoDTO medicaoInstrumentoDTO2 = new MedicaoInstrumentoDTO();
        assertThat(medicaoInstrumentoDTO1).isNotEqualTo(medicaoInstrumentoDTO2);
        medicaoInstrumentoDTO2.setId(medicaoInstrumentoDTO1.getId());
        assertThat(medicaoInstrumentoDTO1).isEqualTo(medicaoInstrumentoDTO2);
        medicaoInstrumentoDTO2.setId(2L);
        assertThat(medicaoInstrumentoDTO1).isNotEqualTo(medicaoInstrumentoDTO2);
        medicaoInstrumentoDTO1.setId(null);
        assertThat(medicaoInstrumentoDTO1).isNotEqualTo(medicaoInstrumentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicaoInstrumentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicaoInstrumentoMapper.fromId(null)).isNull();
    }
}
