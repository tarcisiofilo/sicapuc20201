package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.Funcionario;
import com.sica.repository.FuncionarioRepository;
import com.sica.service.FuncionarioService;
import com.sica.service.dto.FuncionarioDTO;
import com.sica.service.mapper.FuncionarioMapper;
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
 * Integration tests for the {@Link FuncionarioResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class FuncionarioResourceIT {

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_DISPOSITIVO_MONITORAMENTO = 1L;
    private static final Long UPDATED_ID_DISPOSITIVO_MONITORAMENTO = 2L;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioMapper funcionarioMapper;

    @Autowired
    private FuncionarioService funcionarioService;

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

    private MockMvc restFuncionarioMockMvc;

    private Funcionario funcionario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FuncionarioResource funcionarioResource = new FuncionarioResource(funcionarioService);
        this.restFuncionarioMockMvc = MockMvcBuilders.standaloneSetup(funcionarioResource)
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
    public static Funcionario createEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .cargo(DEFAULT_CARGO)
            .idDispositivoMonitoramento(DEFAULT_ID_DISPOSITIVO_MONITORAMENTO);
        return funcionario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createUpdatedEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .cargo(UPDATED_CARGO)
            .idDispositivoMonitoramento(UPDATED_ID_DISPOSITIVO_MONITORAMENTO);
        return funcionario;
    }

    @BeforeEach
    public void initTest() {
        funcionario = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuncionario() throws Exception {
        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);
        restFuncionarioMockMvc.perform(post("/api/funcionarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate + 1);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getCargo()).isEqualTo(DEFAULT_CARGO);
        assertThat(testFuncionario.getIdDispositivoMonitoramento()).isEqualTo(DEFAULT_ID_DISPOSITIVO_MONITORAMENTO);
    }

    @Test
    @Transactional
    public void createFuncionarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();

        // Create the Funcionario with an existing ID
        funcionario.setId(1L);
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionarioMockMvc.perform(post("/api/funcionarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCargoIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioRepository.findAll().size();
        // set the field null
        funcionario.setCargo(null);

        // Create the Funcionario, which fails.
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        restFuncionarioMockMvc.perform(post("/api/funcionarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isBadRequest());

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdDispositivoMonitoramentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioRepository.findAll().size();
        // set the field null
        funcionario.setIdDispositivoMonitoramento(null);

        // Create the Funcionario, which fails.
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        restFuncionarioMockMvc.perform(post("/api/funcionarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isBadRequest());

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFuncionarios() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList
        restFuncionarioMockMvc.perform(get("/api/funcionarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO.toString())))
            .andExpect(jsonPath("$.[*].idDispositivoMonitoramento").value(hasItem(DEFAULT_ID_DISPOSITIVO_MONITORAMENTO.intValue())));
    }
    
    @Test
    @Transactional
    public void getFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get the funcionario
        restFuncionarioMockMvc.perform(get("/api/funcionarios/{id}", funcionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(funcionario.getId().intValue()))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO.toString()))
            .andExpect(jsonPath("$.idDispositivoMonitoramento").value(DEFAULT_ID_DISPOSITIVO_MONITORAMENTO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFuncionario() throws Exception {
        // Get the funcionario
        restFuncionarioMockMvc.perform(get("/api/funcionarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario
        Funcionario updatedFuncionario = funcionarioRepository.findById(funcionario.getId()).get();
        // Disconnect from session so that the updates on updatedFuncionario are not directly saved in db
        em.detach(updatedFuncionario);
        updatedFuncionario
            .cargo(UPDATED_CARGO)
            .idDispositivoMonitoramento(UPDATED_ID_DISPOSITIVO_MONITORAMENTO);
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(updatedFuncionario);

        restFuncionarioMockMvc.perform(put("/api/funcionarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getCargo()).isEqualTo(UPDATED_CARGO);
        assertThat(testFuncionario.getIdDispositivoMonitoramento()).isEqualTo(UPDATED_ID_DISPOSITIVO_MONITORAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc.perform(put("/api/funcionarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeDelete = funcionarioRepository.findAll().size();

        // Delete the funcionario
        restFuncionarioMockMvc.perform(delete("/api/funcionarios/{id}", funcionario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funcionario.class);
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setId(1L);
        Funcionario funcionario2 = new Funcionario();
        funcionario2.setId(funcionario1.getId());
        assertThat(funcionario1).isEqualTo(funcionario2);
        funcionario2.setId(2L);
        assertThat(funcionario1).isNotEqualTo(funcionario2);
        funcionario1.setId(null);
        assertThat(funcionario1).isNotEqualTo(funcionario2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuncionarioDTO.class);
        FuncionarioDTO funcionarioDTO1 = new FuncionarioDTO();
        funcionarioDTO1.setId(1L);
        FuncionarioDTO funcionarioDTO2 = new FuncionarioDTO();
        assertThat(funcionarioDTO1).isNotEqualTo(funcionarioDTO2);
        funcionarioDTO2.setId(funcionarioDTO1.getId());
        assertThat(funcionarioDTO1).isEqualTo(funcionarioDTO2);
        funcionarioDTO2.setId(2L);
        assertThat(funcionarioDTO1).isNotEqualTo(funcionarioDTO2);
        funcionarioDTO1.setId(null);
        assertThat(funcionarioDTO1).isNotEqualTo(funcionarioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(funcionarioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(funcionarioMapper.fromId(null)).isNull();
    }
}
