package com.sica.web.rest;

import com.sica.Sicapuc20201App;
import com.sica.domain.Notificacao;
import com.sica.repository.NotificacaoRepository;
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

import com.sica.domain.enumeration.TipoNoficacao;
/**
 * Integration tests for the {@Link NotificacaoResource} REST controller.
 */
@SpringBootTest(classes = Sicapuc20201App.class)
public class NotificacaoResourceIT {

    private static final ZonedDateTime DEFAULT_DATA_NOTIFACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_NOTIFACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final TipoNoficacao DEFAULT_TIPO_NOTIFICACAO = TipoNoficacao.DNPM;
    private static final TipoNoficacao UPDATED_TIPO_NOTIFICACAO = TipoNoficacao.EMAIL;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

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

    private MockMvc restNotificacaoMockMvc;

    private Notificacao notificacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotificacaoResource notificacaoResource = new NotificacaoResource(notificacaoRepository);
        this.restNotificacaoMockMvc = MockMvcBuilders.standaloneSetup(notificacaoResource)
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
    public static Notificacao createEntity(EntityManager em) {
        Notificacao notificacao = new Notificacao()
            .dataNotifacao(DEFAULT_DATA_NOTIFACAO)
            .tipoNotificacao(DEFAULT_TIPO_NOTIFICACAO);
        return notificacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notificacao createUpdatedEntity(EntityManager em) {
        Notificacao notificacao = new Notificacao()
            .dataNotifacao(UPDATED_DATA_NOTIFACAO)
            .tipoNotificacao(UPDATED_TIPO_NOTIFICACAO);
        return notificacao;
    }

    @BeforeEach
    public void initTest() {
        notificacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificacao() throws Exception {
        int databaseSizeBeforeCreate = notificacaoRepository.findAll().size();

        // Create the Notificacao
        restNotificacaoMockMvc.perform(post("/api/notificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificacao)))
            .andExpect(status().isCreated());

        // Validate the Notificacao in the database
        List<Notificacao> notificacaoList = notificacaoRepository.findAll();
        assertThat(notificacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Notificacao testNotificacao = notificacaoList.get(notificacaoList.size() - 1);
        assertThat(testNotificacao.getDataNotifacao()).isEqualTo(DEFAULT_DATA_NOTIFACAO);
        assertThat(testNotificacao.getTipoNotificacao()).isEqualTo(DEFAULT_TIPO_NOTIFICACAO);
    }

    @Test
    @Transactional
    public void createNotificacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificacaoRepository.findAll().size();

        // Create the Notificacao with an existing ID
        notificacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificacaoMockMvc.perform(post("/api/notificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificacao)))
            .andExpect(status().isBadRequest());

        // Validate the Notificacao in the database
        List<Notificacao> notificacaoList = notificacaoRepository.findAll();
        assertThat(notificacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataNotifacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacaoRepository.findAll().size();
        // set the field null
        notificacao.setDataNotifacao(null);

        // Create the Notificacao, which fails.

        restNotificacaoMockMvc.perform(post("/api/notificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificacao)))
            .andExpect(status().isBadRequest());

        List<Notificacao> notificacaoList = notificacaoRepository.findAll();
        assertThat(notificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoNotificacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacaoRepository.findAll().size();
        // set the field null
        notificacao.setTipoNotificacao(null);

        // Create the Notificacao, which fails.

        restNotificacaoMockMvc.perform(post("/api/notificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificacao)))
            .andExpect(status().isBadRequest());

        List<Notificacao> notificacaoList = notificacaoRepository.findAll();
        assertThat(notificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotificacaos() throws Exception {
        // Initialize the database
        notificacaoRepository.saveAndFlush(notificacao);

        // Get all the notificacaoList
        restNotificacaoMockMvc.perform(get("/api/notificacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataNotifacao").value(hasItem(sameInstant(DEFAULT_DATA_NOTIFACAO))))
            .andExpect(jsonPath("$.[*].tipoNotificacao").value(hasItem(DEFAULT_TIPO_NOTIFICACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getNotificacao() throws Exception {
        // Initialize the database
        notificacaoRepository.saveAndFlush(notificacao);

        // Get the notificacao
        restNotificacaoMockMvc.perform(get("/api/notificacaos/{id}", notificacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notificacao.getId().intValue()))
            .andExpect(jsonPath("$.dataNotifacao").value(sameInstant(DEFAULT_DATA_NOTIFACAO)))
            .andExpect(jsonPath("$.tipoNotificacao").value(DEFAULT_TIPO_NOTIFICACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNotificacao() throws Exception {
        // Get the notificacao
        restNotificacaoMockMvc.perform(get("/api/notificacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificacao() throws Exception {
        // Initialize the database
        notificacaoRepository.saveAndFlush(notificacao);

        int databaseSizeBeforeUpdate = notificacaoRepository.findAll().size();

        // Update the notificacao
        Notificacao updatedNotificacao = notificacaoRepository.findById(notificacao.getId()).get();
        // Disconnect from session so that the updates on updatedNotificacao are not directly saved in db
        em.detach(updatedNotificacao);
        updatedNotificacao
            .dataNotifacao(UPDATED_DATA_NOTIFACAO)
            .tipoNotificacao(UPDATED_TIPO_NOTIFICACAO);

        restNotificacaoMockMvc.perform(put("/api/notificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotificacao)))
            .andExpect(status().isOk());

        // Validate the Notificacao in the database
        List<Notificacao> notificacaoList = notificacaoRepository.findAll();
        assertThat(notificacaoList).hasSize(databaseSizeBeforeUpdate);
        Notificacao testNotificacao = notificacaoList.get(notificacaoList.size() - 1);
        assertThat(testNotificacao.getDataNotifacao()).isEqualTo(UPDATED_DATA_NOTIFACAO);
        assertThat(testNotificacao.getTipoNotificacao()).isEqualTo(UPDATED_TIPO_NOTIFICACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificacao() throws Exception {
        int databaseSizeBeforeUpdate = notificacaoRepository.findAll().size();

        // Create the Notificacao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificacaoMockMvc.perform(put("/api/notificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificacao)))
            .andExpect(status().isBadRequest());

        // Validate the Notificacao in the database
        List<Notificacao> notificacaoList = notificacaoRepository.findAll();
        assertThat(notificacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotificacao() throws Exception {
        // Initialize the database
        notificacaoRepository.saveAndFlush(notificacao);

        int databaseSizeBeforeDelete = notificacaoRepository.findAll().size();

        // Delete the notificacao
        restNotificacaoMockMvc.perform(delete("/api/notificacaos/{id}", notificacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Notificacao> notificacaoList = notificacaoRepository.findAll();
        assertThat(notificacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notificacao.class);
        Notificacao notificacao1 = new Notificacao();
        notificacao1.setId(1L);
        Notificacao notificacao2 = new Notificacao();
        notificacao2.setId(notificacao1.getId());
        assertThat(notificacao1).isEqualTo(notificacao2);
        notificacao2.setId(2L);
        assertThat(notificacao1).isNotEqualTo(notificacao2);
        notificacao1.setId(null);
        assertThat(notificacao1).isNotEqualTo(notificacao2);
    }
}
