package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.InstrumentoMonitoramento;
import com.sica.repository.InstrumentoMonitoramentoRepository;
import com.sica.service.InstrumentoMonitoramentoService;
import com.sica.service.dto.InstrumentoMonitoramentoDTO;
import com.sica.service.mapper.InstrumentoMonitoramentoMapper;
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

import com.sica.domain.enumeration.TipoInstrumentoMonitoramento;
import com.sica.domain.enumeration.TipoMedicao;
/**
 * Integration tests for the {@Link InstrumentoMonitoramentoResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class InstrumentoMonitoramentoResourceIT {

    private static final String DEFAULT_IDENTIFICAO = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICAO = "BBBBBBBBBB";

    private static final TipoInstrumentoMonitoramento DEFAULT_TIPO_INSTRUMENTO_MONITORAMENTO = TipoInstrumentoMonitoramento.PIEZOMETRO;
    private static final TipoInstrumentoMonitoramento UPDATED_TIPO_INSTRUMENTO_MONITORAMENTO = TipoInstrumentoMonitoramento.MEDIDOR_NIVEL_AGUA;

    private static final TipoMedicao DEFAULT_TIPO_MEDICAO = TipoMedicao.MANUAL;
    private static final TipoMedicao UPDATED_TIPO_MEDICAO = TipoMedicao.API;

    private static final String DEFAULT_URL_GET_MEDICAO = "AAAAAAAAAA";
    private static final String UPDATED_URL_GET_MEDICAO = "BBBBBBBBBB";

    private static final Double DEFAULT_INTERVALO_MEDICAO_API = 1D;
    private static final Double UPDATED_INTERVALO_MEDICAO_API = 2D;

    private static final Double DEFAULT_VARIANCIA_TOLERADA = 1D;
    private static final Double UPDATED_VARIANCIA_TOLERADA = 2D;

    private static final Double DEFAULT_LIMITE_SUPERIOR = 1D;
    private static final Double UPDATED_LIMITE_SUPERIOR = 2D;

    @Autowired
    private InstrumentoMonitoramentoRepository instrumentoMonitoramentoRepository;

    @Autowired
    private InstrumentoMonitoramentoMapper instrumentoMonitoramentoMapper;

    @Autowired
    private InstrumentoMonitoramentoService instrumentoMonitoramentoService;

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

    private MockMvc restInstrumentoMonitoramentoMockMvc;

    private InstrumentoMonitoramento instrumentoMonitoramento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstrumentoMonitoramentoResource instrumentoMonitoramentoResource = new InstrumentoMonitoramentoResource(instrumentoMonitoramentoService);
        this.restInstrumentoMonitoramentoMockMvc = MockMvcBuilders.standaloneSetup(instrumentoMonitoramentoResource)
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
    public static InstrumentoMonitoramento createEntity(EntityManager em) {
        InstrumentoMonitoramento instrumentoMonitoramento = new InstrumentoMonitoramento()
            .identificao(DEFAULT_IDENTIFICAO)
            .tipoInstrumentoMonitoramento(DEFAULT_TIPO_INSTRUMENTO_MONITORAMENTO)
            .tipoMedicao(DEFAULT_TIPO_MEDICAO)
            .urlGetMedicao(DEFAULT_URL_GET_MEDICAO)
            .intervaloMedicaoAPI(DEFAULT_INTERVALO_MEDICAO_API)
            .varianciaTolerada(DEFAULT_VARIANCIA_TOLERADA)
            .limiteSuperior(DEFAULT_LIMITE_SUPERIOR);
        return instrumentoMonitoramento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstrumentoMonitoramento createUpdatedEntity(EntityManager em) {
        InstrumentoMonitoramento instrumentoMonitoramento = new InstrumentoMonitoramento()
            .identificao(UPDATED_IDENTIFICAO)
            .tipoInstrumentoMonitoramento(UPDATED_TIPO_INSTRUMENTO_MONITORAMENTO)
            .tipoMedicao(UPDATED_TIPO_MEDICAO)
            .urlGetMedicao(UPDATED_URL_GET_MEDICAO)
            .intervaloMedicaoAPI(UPDATED_INTERVALO_MEDICAO_API)
            .varianciaTolerada(UPDATED_VARIANCIA_TOLERADA)
            .limiteSuperior(UPDATED_LIMITE_SUPERIOR);
        return instrumentoMonitoramento;
    }

    @BeforeEach
    public void initTest() {
        instrumentoMonitoramento = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstrumentoMonitoramento() throws Exception {
        int databaseSizeBeforeCreate = instrumentoMonitoramentoRepository.findAll().size();

        // Create the InstrumentoMonitoramento
        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO = instrumentoMonitoramentoMapper.toDto(instrumentoMonitoramento);
        restInstrumentoMonitoramentoMockMvc.perform(post("/api/instrumento-monitoramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoMonitoramentoDTO)))
            .andExpect(status().isCreated());

        // Validate the InstrumentoMonitoramento in the database
        List<InstrumentoMonitoramento> instrumentoMonitoramentoList = instrumentoMonitoramentoRepository.findAll();
        assertThat(instrumentoMonitoramentoList).hasSize(databaseSizeBeforeCreate + 1);
        InstrumentoMonitoramento testInstrumentoMonitoramento = instrumentoMonitoramentoList.get(instrumentoMonitoramentoList.size() - 1);
        assertThat(testInstrumentoMonitoramento.getIdentificao()).isEqualTo(DEFAULT_IDENTIFICAO);
        assertThat(testInstrumentoMonitoramento.getTipoInstrumentoMonitoramento()).isEqualTo(DEFAULT_TIPO_INSTRUMENTO_MONITORAMENTO);
        assertThat(testInstrumentoMonitoramento.getTipoMedicao()).isEqualTo(DEFAULT_TIPO_MEDICAO);
        assertThat(testInstrumentoMonitoramento.getUrlGetMedicao()).isEqualTo(DEFAULT_URL_GET_MEDICAO);
        assertThat(testInstrumentoMonitoramento.getIntervaloMedicaoAPI()).isEqualTo(DEFAULT_INTERVALO_MEDICAO_API);
        assertThat(testInstrumentoMonitoramento.getVarianciaTolerada()).isEqualTo(DEFAULT_VARIANCIA_TOLERADA);
        assertThat(testInstrumentoMonitoramento.getLimiteSuperior()).isEqualTo(DEFAULT_LIMITE_SUPERIOR);
    }

    @Test
    @Transactional
    public void createInstrumentoMonitoramentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instrumentoMonitoramentoRepository.findAll().size();

        // Create the InstrumentoMonitoramento with an existing ID
        instrumentoMonitoramento.setId(1L);
        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO = instrumentoMonitoramentoMapper.toDto(instrumentoMonitoramento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstrumentoMonitoramentoMockMvc.perform(post("/api/instrumento-monitoramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoMonitoramentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InstrumentoMonitoramento in the database
        List<InstrumentoMonitoramento> instrumentoMonitoramentoList = instrumentoMonitoramentoRepository.findAll();
        assertThat(instrumentoMonitoramentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentificaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instrumentoMonitoramentoRepository.findAll().size();
        // set the field null
        instrumentoMonitoramento.setIdentificao(null);

        // Create the InstrumentoMonitoramento, which fails.
        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO = instrumentoMonitoramentoMapper.toDto(instrumentoMonitoramento);

        restInstrumentoMonitoramentoMockMvc.perform(post("/api/instrumento-monitoramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoMonitoramentoDTO)))
            .andExpect(status().isBadRequest());

        List<InstrumentoMonitoramento> instrumentoMonitoramentoList = instrumentoMonitoramentoRepository.findAll();
        assertThat(instrumentoMonitoramentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoInstrumentoMonitoramentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instrumentoMonitoramentoRepository.findAll().size();
        // set the field null
        instrumentoMonitoramento.setTipoInstrumentoMonitoramento(null);

        // Create the InstrumentoMonitoramento, which fails.
        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO = instrumentoMonitoramentoMapper.toDto(instrumentoMonitoramento);

        restInstrumentoMonitoramentoMockMvc.perform(post("/api/instrumento-monitoramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoMonitoramentoDTO)))
            .andExpect(status().isBadRequest());

        List<InstrumentoMonitoramento> instrumentoMonitoramentoList = instrumentoMonitoramentoRepository.findAll();
        assertThat(instrumentoMonitoramentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoMedicaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instrumentoMonitoramentoRepository.findAll().size();
        // set the field null
        instrumentoMonitoramento.setTipoMedicao(null);

        // Create the InstrumentoMonitoramento, which fails.
        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO = instrumentoMonitoramentoMapper.toDto(instrumentoMonitoramento);

        restInstrumentoMonitoramentoMockMvc.perform(post("/api/instrumento-monitoramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoMonitoramentoDTO)))
            .andExpect(status().isBadRequest());

        List<InstrumentoMonitoramento> instrumentoMonitoramentoList = instrumentoMonitoramentoRepository.findAll();
        assertThat(instrumentoMonitoramentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVarianciaToleradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = instrumentoMonitoramentoRepository.findAll().size();
        // set the field null
        instrumentoMonitoramento.setVarianciaTolerada(null);

        // Create the InstrumentoMonitoramento, which fails.
        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO = instrumentoMonitoramentoMapper.toDto(instrumentoMonitoramento);

        restInstrumentoMonitoramentoMockMvc.perform(post("/api/instrumento-monitoramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoMonitoramentoDTO)))
            .andExpect(status().isBadRequest());

        List<InstrumentoMonitoramento> instrumentoMonitoramentoList = instrumentoMonitoramentoRepository.findAll();
        assertThat(instrumentoMonitoramentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLimiteSuperiorIsRequired() throws Exception {
        int databaseSizeBeforeTest = instrumentoMonitoramentoRepository.findAll().size();
        // set the field null
        instrumentoMonitoramento.setLimiteSuperior(null);

        // Create the InstrumentoMonitoramento, which fails.
        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO = instrumentoMonitoramentoMapper.toDto(instrumentoMonitoramento);

        restInstrumentoMonitoramentoMockMvc.perform(post("/api/instrumento-monitoramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoMonitoramentoDTO)))
            .andExpect(status().isBadRequest());

        List<InstrumentoMonitoramento> instrumentoMonitoramentoList = instrumentoMonitoramentoRepository.findAll();
        assertThat(instrumentoMonitoramentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstrumentoMonitoramentos() throws Exception {
        // Initialize the database
        instrumentoMonitoramentoRepository.saveAndFlush(instrumentoMonitoramento);

        // Get all the instrumentoMonitoramentoList
        restInstrumentoMonitoramentoMockMvc.perform(get("/api/instrumento-monitoramentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instrumentoMonitoramento.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificao").value(hasItem(DEFAULT_IDENTIFICAO.toString())))
            .andExpect(jsonPath("$.[*].tipoInstrumentoMonitoramento").value(hasItem(DEFAULT_TIPO_INSTRUMENTO_MONITORAMENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoMedicao").value(hasItem(DEFAULT_TIPO_MEDICAO.toString())))
            .andExpect(jsonPath("$.[*].urlGetMedicao").value(hasItem(DEFAULT_URL_GET_MEDICAO.toString())))
            .andExpect(jsonPath("$.[*].intervaloMedicaoAPI").value(hasItem(DEFAULT_INTERVALO_MEDICAO_API.doubleValue())))
            .andExpect(jsonPath("$.[*].varianciaTolerada").value(hasItem(DEFAULT_VARIANCIA_TOLERADA.doubleValue())))
            .andExpect(jsonPath("$.[*].limiteSuperior").value(hasItem(DEFAULT_LIMITE_SUPERIOR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getInstrumentoMonitoramento() throws Exception {
        // Initialize the database
        instrumentoMonitoramentoRepository.saveAndFlush(instrumentoMonitoramento);

        // Get the instrumentoMonitoramento
        restInstrumentoMonitoramentoMockMvc.perform(get("/api/instrumento-monitoramentos/{id}", instrumentoMonitoramento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(instrumentoMonitoramento.getId().intValue()))
            .andExpect(jsonPath("$.identificao").value(DEFAULT_IDENTIFICAO.toString()))
            .andExpect(jsonPath("$.tipoInstrumentoMonitoramento").value(DEFAULT_TIPO_INSTRUMENTO_MONITORAMENTO.toString()))
            .andExpect(jsonPath("$.tipoMedicao").value(DEFAULT_TIPO_MEDICAO.toString()))
            .andExpect(jsonPath("$.urlGetMedicao").value(DEFAULT_URL_GET_MEDICAO.toString()))
            .andExpect(jsonPath("$.intervaloMedicaoAPI").value(DEFAULT_INTERVALO_MEDICAO_API.doubleValue()))
            .andExpect(jsonPath("$.varianciaTolerada").value(DEFAULT_VARIANCIA_TOLERADA.doubleValue()))
            .andExpect(jsonPath("$.limiteSuperior").value(DEFAULT_LIMITE_SUPERIOR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstrumentoMonitoramento() throws Exception {
        // Get the instrumentoMonitoramento
        restInstrumentoMonitoramentoMockMvc.perform(get("/api/instrumento-monitoramentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstrumentoMonitoramento() throws Exception {
        // Initialize the database
        instrumentoMonitoramentoRepository.saveAndFlush(instrumentoMonitoramento);

        int databaseSizeBeforeUpdate = instrumentoMonitoramentoRepository.findAll().size();

        // Update the instrumentoMonitoramento
        InstrumentoMonitoramento updatedInstrumentoMonitoramento = instrumentoMonitoramentoRepository.findById(instrumentoMonitoramento.getId()).get();
        // Disconnect from session so that the updates on updatedInstrumentoMonitoramento are not directly saved in db
        em.detach(updatedInstrumentoMonitoramento);
        updatedInstrumentoMonitoramento
            .identificao(UPDATED_IDENTIFICAO)
            .tipoInstrumentoMonitoramento(UPDATED_TIPO_INSTRUMENTO_MONITORAMENTO)
            .tipoMedicao(UPDATED_TIPO_MEDICAO)
            .urlGetMedicao(UPDATED_URL_GET_MEDICAO)
            .intervaloMedicaoAPI(UPDATED_INTERVALO_MEDICAO_API)
            .varianciaTolerada(UPDATED_VARIANCIA_TOLERADA)
            .limiteSuperior(UPDATED_LIMITE_SUPERIOR);
        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO = instrumentoMonitoramentoMapper.toDto(updatedInstrumentoMonitoramento);

        restInstrumentoMonitoramentoMockMvc.perform(put("/api/instrumento-monitoramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoMonitoramentoDTO)))
            .andExpect(status().isOk());

        // Validate the InstrumentoMonitoramento in the database
        List<InstrumentoMonitoramento> instrumentoMonitoramentoList = instrumentoMonitoramentoRepository.findAll();
        assertThat(instrumentoMonitoramentoList).hasSize(databaseSizeBeforeUpdate);
        InstrumentoMonitoramento testInstrumentoMonitoramento = instrumentoMonitoramentoList.get(instrumentoMonitoramentoList.size() - 1);
        assertThat(testInstrumentoMonitoramento.getIdentificao()).isEqualTo(UPDATED_IDENTIFICAO);
        assertThat(testInstrumentoMonitoramento.getTipoInstrumentoMonitoramento()).isEqualTo(UPDATED_TIPO_INSTRUMENTO_MONITORAMENTO);
        assertThat(testInstrumentoMonitoramento.getTipoMedicao()).isEqualTo(UPDATED_TIPO_MEDICAO);
        assertThat(testInstrumentoMonitoramento.getUrlGetMedicao()).isEqualTo(UPDATED_URL_GET_MEDICAO);
        assertThat(testInstrumentoMonitoramento.getIntervaloMedicaoAPI()).isEqualTo(UPDATED_INTERVALO_MEDICAO_API);
        assertThat(testInstrumentoMonitoramento.getVarianciaTolerada()).isEqualTo(UPDATED_VARIANCIA_TOLERADA);
        assertThat(testInstrumentoMonitoramento.getLimiteSuperior()).isEqualTo(UPDATED_LIMITE_SUPERIOR);
    }

    @Test
    @Transactional
    public void updateNonExistingInstrumentoMonitoramento() throws Exception {
        int databaseSizeBeforeUpdate = instrumentoMonitoramentoRepository.findAll().size();

        // Create the InstrumentoMonitoramento
        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO = instrumentoMonitoramentoMapper.toDto(instrumentoMonitoramento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstrumentoMonitoramentoMockMvc.perform(put("/api/instrumento-monitoramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoMonitoramentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InstrumentoMonitoramento in the database
        List<InstrumentoMonitoramento> instrumentoMonitoramentoList = instrumentoMonitoramentoRepository.findAll();
        assertThat(instrumentoMonitoramentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstrumentoMonitoramento() throws Exception {
        // Initialize the database
        instrumentoMonitoramentoRepository.saveAndFlush(instrumentoMonitoramento);

        int databaseSizeBeforeDelete = instrumentoMonitoramentoRepository.findAll().size();

        // Delete the instrumentoMonitoramento
        restInstrumentoMonitoramentoMockMvc.perform(delete("/api/instrumento-monitoramentos/{id}", instrumentoMonitoramento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<InstrumentoMonitoramento> instrumentoMonitoramentoList = instrumentoMonitoramentoRepository.findAll();
        assertThat(instrumentoMonitoramentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstrumentoMonitoramento.class);
        InstrumentoMonitoramento instrumentoMonitoramento1 = new InstrumentoMonitoramento();
        instrumentoMonitoramento1.setId(1L);
        InstrumentoMonitoramento instrumentoMonitoramento2 = new InstrumentoMonitoramento();
        instrumentoMonitoramento2.setId(instrumentoMonitoramento1.getId());
        assertThat(instrumentoMonitoramento1).isEqualTo(instrumentoMonitoramento2);
        instrumentoMonitoramento2.setId(2L);
        assertThat(instrumentoMonitoramento1).isNotEqualTo(instrumentoMonitoramento2);
        instrumentoMonitoramento1.setId(null);
        assertThat(instrumentoMonitoramento1).isNotEqualTo(instrumentoMonitoramento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstrumentoMonitoramentoDTO.class);
        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO1 = new InstrumentoMonitoramentoDTO();
        instrumentoMonitoramentoDTO1.setId(1L);
        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO2 = new InstrumentoMonitoramentoDTO();
        assertThat(instrumentoMonitoramentoDTO1).isNotEqualTo(instrumentoMonitoramentoDTO2);
        instrumentoMonitoramentoDTO2.setId(instrumentoMonitoramentoDTO1.getId());
        assertThat(instrumentoMonitoramentoDTO1).isEqualTo(instrumentoMonitoramentoDTO2);
        instrumentoMonitoramentoDTO2.setId(2L);
        assertThat(instrumentoMonitoramentoDTO1).isNotEqualTo(instrumentoMonitoramentoDTO2);
        instrumentoMonitoramentoDTO1.setId(null);
        assertThat(instrumentoMonitoramentoDTO1).isNotEqualTo(instrumentoMonitoramentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(instrumentoMonitoramentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(instrumentoMonitoramentoMapper.fromId(null)).isNull();
    }
}
