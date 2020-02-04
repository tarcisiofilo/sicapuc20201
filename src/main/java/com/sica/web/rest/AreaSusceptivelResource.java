package com.sica.web.rest;

import com.sica.domain.AreaSusceptivel;
import com.sica.repository.AreaSusceptivelRepository;
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
 * REST controller for managing {@link com.sica.domain.AreaSusceptivel}.
 */
@RestController
@RequestMapping("/api")
public class AreaSusceptivelResource {

    private final Logger log = LoggerFactory.getLogger(AreaSusceptivelResource.class);

    private static final String ENTITY_NAME = "areaSusceptivel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaSusceptivelRepository areaSusceptivelRepository;

    public AreaSusceptivelResource(AreaSusceptivelRepository areaSusceptivelRepository) {
        this.areaSusceptivelRepository = areaSusceptivelRepository;
    }

    /**
     * {@code POST  /area-susceptivels} : Create a new areaSusceptivel.
     *
     * @param areaSusceptivel the areaSusceptivel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaSusceptivel, or with status {@code 400 (Bad Request)} if the areaSusceptivel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/area-susceptivels")
    public ResponseEntity<AreaSusceptivel> createAreaSusceptivel(@Valid @RequestBody AreaSusceptivel areaSusceptivel) throws URISyntaxException {
        log.debug("REST request to save AreaSusceptivel : {}", areaSusceptivel);
        if (areaSusceptivel.getId() != null) {
            throw new BadRequestAlertException("A new areaSusceptivel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AreaSusceptivel result = areaSusceptivelRepository.save(areaSusceptivel);
        return ResponseEntity.created(new URI("/api/area-susceptivels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /area-susceptivels} : Updates an existing areaSusceptivel.
     *
     * @param areaSusceptivel the areaSusceptivel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaSusceptivel,
     * or with status {@code 400 (Bad Request)} if the areaSusceptivel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaSusceptivel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/area-susceptivels")
    public ResponseEntity<AreaSusceptivel> updateAreaSusceptivel(@Valid @RequestBody AreaSusceptivel areaSusceptivel) throws URISyntaxException {
        log.debug("REST request to update AreaSusceptivel : {}", areaSusceptivel);
        if (areaSusceptivel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AreaSusceptivel result = areaSusceptivelRepository.save(areaSusceptivel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaSusceptivel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /area-susceptivels} : get all the areaSusceptivels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaSusceptivels in body.
     */
    @GetMapping("/area-susceptivels")
    public List<AreaSusceptivel> getAllAreaSusceptivels() {
        log.debug("REST request to get all AreaSusceptivels");
        return areaSusceptivelRepository.findAll();
    }

    /**
     * {@code GET  /area-susceptivels/:id} : get the "id" areaSusceptivel.
     *
     * @param id the id of the areaSusceptivel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaSusceptivel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/area-susceptivels/{id}")
    public ResponseEntity<AreaSusceptivel> getAreaSusceptivel(@PathVariable Long id) {
        log.debug("REST request to get AreaSusceptivel : {}", id);
        Optional<AreaSusceptivel> areaSusceptivel = areaSusceptivelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(areaSusceptivel);
    }

    /**
     * {@code DELETE  /area-susceptivels/:id} : delete the "id" areaSusceptivel.
     *
     * @param id the id of the areaSusceptivel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/area-susceptivels/{id}")
    public ResponseEntity<Void> deleteAreaSusceptivel(@PathVariable Long id) {
        log.debug("REST request to delete AreaSusceptivel : {}", id);
        areaSusceptivelRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
