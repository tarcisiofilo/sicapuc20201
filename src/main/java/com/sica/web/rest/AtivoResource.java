package com.sica.web.rest;

import com.sica.domain.Ativo;
import com.sica.repository.AtivoRepository;
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
 * REST controller for managing {@link com.sica.domain.Ativo}.
 */
@RestController
@RequestMapping("/api")
public class AtivoResource {

    private final Logger log = LoggerFactory.getLogger(AtivoResource.class);

    private static final String ENTITY_NAME = "ativo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtivoRepository ativoRepository;

    public AtivoResource(AtivoRepository ativoRepository) {
        this.ativoRepository = ativoRepository;
    }

    /**
     * {@code POST  /ativos} : Create a new ativo.
     *
     * @param ativo the ativo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ativo, or with status {@code 400 (Bad Request)} if the ativo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ativos")
    public ResponseEntity<Ativo> createAtivo(@Valid @RequestBody Ativo ativo) throws URISyntaxException {
        log.debug("REST request to save Ativo : {}", ativo);
        if (ativo.getId() != null) {
            throw new BadRequestAlertException("A new ativo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ativo result = ativoRepository.save(ativo);
        return ResponseEntity.created(new URI("/api/ativos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ativos} : Updates an existing ativo.
     *
     * @param ativo the ativo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ativo,
     * or with status {@code 400 (Bad Request)} if the ativo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ativo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ativos")
    public ResponseEntity<Ativo> updateAtivo(@Valid @RequestBody Ativo ativo) throws URISyntaxException {
        log.debug("REST request to update Ativo : {}", ativo);
        if (ativo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ativo result = ativoRepository.save(ativo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ativo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ativos} : get all the ativos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ativos in body.
     */
    @GetMapping("/ativos")
    public List<Ativo> getAllAtivos() {
        log.debug("REST request to get all Ativos");
        return ativoRepository.findAll();
    }

    /**
     * {@code GET  /ativos/:id} : get the "id" ativo.
     *
     * @param id the id of the ativo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ativo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ativos/{id}")
    public ResponseEntity<Ativo> getAtivo(@PathVariable Long id) {
        log.debug("REST request to get Ativo : {}", id);
        Optional<Ativo> ativo = ativoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ativo);
    }

    /**
     * {@code DELETE  /ativos/:id} : delete the "id" ativo.
     *
     * @param id the id of the ativo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ativos/{id}")
    public ResponseEntity<Void> deleteAtivo(@PathVariable Long id) {
        log.debug("REST request to delete Ativo : {}", id);
        ativoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
