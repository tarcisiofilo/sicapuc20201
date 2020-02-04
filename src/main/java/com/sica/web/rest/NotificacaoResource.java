package com.sica.web.rest;

import com.sica.domain.Notificacao;
import com.sica.repository.NotificacaoRepository;
import com.sica.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sica.domain.Notificacao}.
 */
@RestController
@RequestMapping("/api")
public class NotificacaoResource {

    private final Logger log = LoggerFactory.getLogger(NotificacaoResource.class);

    private static final String ENTITY_NAME = "notificacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoResource(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    /**
     * {@code POST  /notificacaos} : Create a new notificacao.
     *
     * @param notificacao the notificacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificacao, or with status {@code 400 (Bad Request)} if the notificacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notificacaos")
    public ResponseEntity<Notificacao> createNotificacao(@Valid @RequestBody Notificacao notificacao) throws URISyntaxException {
        log.debug("REST request to save Notificacao : {}", notificacao);
        if (notificacao.getId() != null) {
            throw new BadRequestAlertException("A new notificacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Notificacao result = notificacaoRepository.save(notificacao);
        return ResponseEntity.created(new URI("/api/notificacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notificacaos} : Updates an existing notificacao.
     *
     * @param notificacao the notificacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificacao,
     * or with status {@code 400 (Bad Request)} if the notificacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notificacaos")
    public ResponseEntity<Notificacao> updateNotificacao(@Valid @RequestBody Notificacao notificacao) throws URISyntaxException {
        log.debug("REST request to update Notificacao : {}", notificacao);
        if (notificacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Notificacao result = notificacaoRepository.save(notificacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notificacaos} : get all the notificacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificacaos in body.
     */
    @GetMapping("/notificacaos")
    public List<Notificacao> getAllNotificacaos() {
        log.debug("REST request to get all Notificacaos");
        return notificacaoRepository.findAll();
    }

    /**
     * {@code GET  /notificacaos/:id} : get the "id" notificacao.
     *
     * @param id the id of the notificacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notificacaos/{id}")
    public ResponseEntity<Notificacao> getNotificacao(@PathVariable Long id) {
        log.debug("REST request to get Notificacao : {}", id);
        Optional<Notificacao> notificacao = notificacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notificacao);
    }

    /**
     * {@code DELETE  /notificacaos/:id} : delete the "id" notificacao.
     *
     * @param id the id of the notificacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notificacaos/{id}")
    public ResponseEntity<Void> deleteNotificacao(@PathVariable Long id) {
        log.debug("REST request to delete Notificacao : {}", id);
        notificacaoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
