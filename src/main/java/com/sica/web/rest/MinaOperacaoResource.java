package com.sica.web.rest;

import com.sica.domain.MinaOperacao;
import com.sica.repository.MinaOperacaoRepository;
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
 * REST controller for managing {@link com.sica.domain.MinaOperacao}.
 */
@RestController
@RequestMapping("/api")
public class MinaOperacaoResource {

    private final Logger log = LoggerFactory.getLogger(MinaOperacaoResource.class);

    private static final String ENTITY_NAME = "minaOperacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MinaOperacaoRepository minaOperacaoRepository;

    public MinaOperacaoResource(MinaOperacaoRepository minaOperacaoRepository) {
        this.minaOperacaoRepository = minaOperacaoRepository;
    }

    /**
     * {@code POST  /mina-operacaos} : Create a new minaOperacao.
     *
     * @param minaOperacao the minaOperacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new minaOperacao, or with status {@code 400 (Bad Request)} if the minaOperacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mina-operacaos")
    public ResponseEntity<MinaOperacao> createMinaOperacao(@Valid @RequestBody MinaOperacao minaOperacao) throws URISyntaxException {
        log.debug("REST request to save MinaOperacao : {}", minaOperacao);
        if (minaOperacao.getId() != null) {
            throw new BadRequestAlertException("A new minaOperacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MinaOperacao result = minaOperacaoRepository.save(minaOperacao);
        return ResponseEntity.created(new URI("/api/mina-operacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mina-operacaos} : Updates an existing minaOperacao.
     *
     * @param minaOperacao the minaOperacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated minaOperacao,
     * or with status {@code 400 (Bad Request)} if the minaOperacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the minaOperacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mina-operacaos")
    public ResponseEntity<MinaOperacao> updateMinaOperacao(@Valid @RequestBody MinaOperacao minaOperacao) throws URISyntaxException {
        log.debug("REST request to update MinaOperacao : {}", minaOperacao);
        if (minaOperacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MinaOperacao result = minaOperacaoRepository.save(minaOperacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, minaOperacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mina-operacaos} : get all the minaOperacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of minaOperacaos in body.
     */
    @GetMapping("/mina-operacaos")
    public List<MinaOperacao> getAllMinaOperacaos() {
        log.debug("REST request to get all MinaOperacaos");
        return minaOperacaoRepository.findAll();
    }

    /**
     * {@code GET  /mina-operacaos/:id} : get the "id" minaOperacao.
     *
     * @param id the id of the minaOperacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the minaOperacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mina-operacaos/{id}")
    public ResponseEntity<MinaOperacao> getMinaOperacao(@PathVariable Long id) {
        log.debug("REST request to get MinaOperacao : {}", id);
        Optional<MinaOperacao> minaOperacao = minaOperacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(minaOperacao);
    }

    /**
     * {@code DELETE  /mina-operacaos/:id} : delete the "id" minaOperacao.
     *
     * @param id the id of the minaOperacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mina-operacaos/{id}")
    public ResponseEntity<Void> deleteMinaOperacao(@PathVariable Long id) {
        log.debug("REST request to delete MinaOperacao : {}", id);
        minaOperacaoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
