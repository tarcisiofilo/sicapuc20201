package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.Sirene;
import com.sica.repository.SireneRepository;
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
 * Integration tests for the {@Link SireneResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class SireneResourceIT {

    private static final String DEFAULT_IDENTIFICACAO = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACAO = "BBBBBBBBBB";

    private static final String DEFAULT_URL_ATIVAR = "AAAAAAAAAA";
    private static final String UPDATED_URL_ATIVAR = "BBBBBBBBBB";

    @Autowired
    private SireneRepository sireneRepository;

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

    private MockMvc restSireneMockMvc;

    private Sirene sirene;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SireneResource sireneResource = new SireneResource(sireneRepository);
        this.restSireneMockMvc = MockMvcBuilders.standaloneSetup(sireneResource)
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
    public static Sirene createEntity(EntityManager em) {
        Sirene sirene = new Sirene()
            .identificacao(DEFAULT_IDENTIFICACAO)
            .urlAtivar(DEFAULT_URL_ATIVAR);
        return sirene;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sirene createUpdatedEntity(EntityManager em) {
        Sirene sirene = new Sirene()
            .identificacao(UPDATED_IDENTIFICACAO)
            .urlAtivar(UPDATED_URL_ATIVAR);
        return sirene;
    }

    @BeforeEach
    public void initTest() {
        sirene = createEntity(em);
    }

    @Test
    @Transactional
    public void createSirene() throws Exception {
        int databaseSizeBeforeCreate = sireneRepository.findAll().size();

        // Create the Sirene
        restSireneMockMvc.perform(post("/api/sirenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sirene)))
            .andExpect(status().isCreated());

        // Validate the Sirene in the database
        List<Sirene> sireneList = sireneRepository.findAll();
        assertThat(sireneList).hasSize(databaseSizeBeforeCreate + 1);
        Sirene testSirene = sireneList.get(sireneList.size() - 1);
        assertThat(testSirene.getIdentificacao()).isEqualTo(DEFAULT_IDENTIFICACAO);
        assertThat(testSirene.getUrlAtivar()).isEqualTo(DEFAULT_URL_ATIVAR);
    }

    @Test
    @Transactional
    public void createSireneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sireneRepository.findAll().size();

        // Create the Sirene with an existing ID
        sirene.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSireneMockMvc.perform(post("/api/sirenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sirene)))
            .andExpect(status().isBadRequest());

        // Validate the Sirene in the database
        List<Sirene> sireneList = sireneRepository.findAll();
        assertThat(sireneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentificacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = sireneRepository.findAll().size();
        // set the field null
        sirene.setIdentificacao(null);

        // Create the Sirene, which fails.

        restSireneMockMvc.perform(post("/api/sirenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sirene)))
            .andExpect(status().isBadRequest());

        List<Sirene> sireneList = sireneRepository.findAll();
        assertThat(sireneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlAtivarIsRequired() throws Exception {
        int databaseSizeBeforeTest = sireneRepository.findAll().size();
        // set the field null
        sirene.setUrlAtivar(null);

        // Create the Sirene, which fails.

        restSireneMockMvc.perform(post("/api/sirenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sirene)))
            .andExpect(status().isBadRequest());

        List<Sirene> sireneList = sireneRepository.findAll();
        assertThat(sireneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSirenes() throws Exception {
        // Initialize the database
        sireneRepository.saveAndFlush(sirene);

        // Get all the sireneList
        restSireneMockMvc.perform(get("/api/sirenes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sirene.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificacao").value(hasItem(DEFAULT_IDENTIFICACAO.toString())))
            .andExpect(jsonPath("$.[*].urlAtivar").value(hasItem(DEFAULT_URL_ATIVAR.toString())));
    }
    
    @Test
    @Transactional
    public void getSirene() throws Exception {
        // Initialize the database
        sireneRepository.saveAndFlush(sirene);

        // Get the sirene
        restSireneMockMvc.perform(get("/api/sirenes/{id}", sirene.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sirene.getId().intValue()))
            .andExpect(jsonPath("$.identificacao").value(DEFAULT_IDENTIFICACAO.toString()))
            .andExpect(jsonPath("$.urlAtivar").value(DEFAULT_URL_ATIVAR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSirene() throws Exception {
        // Get the sirene
        restSireneMockMvc.perform(get("/api/sirenes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSirene() throws Exception {
        // Initialize the database
        sireneRepository.saveAndFlush(sirene);

        int databaseSizeBeforeUpdate = sireneRepository.findAll().size();

        // Update the sirene
        Sirene updatedSirene = sireneRepository.findById(sirene.getId()).get();
        // Disconnect from session so that the updates on updatedSirene are not directly saved in db
        em.detach(updatedSirene);
        updatedSirene
            .identificacao(UPDATED_IDENTIFICACAO)
            .urlAtivar(UPDATED_URL_ATIVAR);

        restSireneMockMvc.perform(put("/api/sirenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSirene)))
            .andExpect(status().isOk());

        // Validate the Sirene in the database
        List<Sirene> sireneList = sireneRepository.findAll();
        assertThat(sireneList).hasSize(databaseSizeBeforeUpdate);
        Sirene testSirene = sireneList.get(sireneList.size() - 1);
        assertThat(testSirene.getIdentificacao()).isEqualTo(UPDATED_IDENTIFICACAO);
        assertThat(testSirene.getUrlAtivar()).isEqualTo(UPDATED_URL_ATIVAR);
    }

    @Test
    @Transactional
    public void updateNonExistingSirene() throws Exception {
        int databaseSizeBeforeUpdate = sireneRepository.findAll().size();

        // Create the Sirene

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSireneMockMvc.perform(put("/api/sirenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sirene)))
            .andExpect(status().isBadRequest());

        // Validate the Sirene in the database
        List<Sirene> sireneList = sireneRepository.findAll();
        assertThat(sireneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSirene() throws Exception {
        // Initialize the database
        sireneRepository.saveAndFlush(sirene);

        int databaseSizeBeforeDelete = sireneRepository.findAll().size();

        // Delete the sirene
        restSireneMockMvc.perform(delete("/api/sirenes/{id}", sirene.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Sirene> sireneList = sireneRepository.findAll();
        assertThat(sireneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sirene.class);
        Sirene sirene1 = new Sirene();
        sirene1.setId(1L);
        Sirene sirene2 = new Sirene();
        sirene2.setId(sirene1.getId());
        assertThat(sirene1).isEqualTo(sirene2);
        sirene2.setId(2L);
        assertThat(sirene1).isNotEqualTo(sirene2);
        sirene1.setId(null);
        assertThat(sirene1).isNotEqualTo(sirene2);
    }
}
