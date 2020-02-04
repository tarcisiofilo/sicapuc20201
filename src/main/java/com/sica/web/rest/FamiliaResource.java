package com.sica.web.rest;

import com.sica.domain.Familia;
import com.sica.repository.FamiliaRepository;
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
 * REST controller for managing {@link com.sica.domain.Familia}.
 */
@RestController
@RequestMapping("/api")
public class FamiliaResource {

    private final Logger log = LoggerFactory.getLogger(FamiliaResource.class);

    private static final String ENTITY_NAME = "familia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamiliaRepository familiaRepository;

    public FamiliaResource(FamiliaRepository familiaRepository) {
        this.familiaRepository = familiaRepository;
    }

    /**
     * {@code POST  /familias} : Create a new familia.
     *
     * @param familia the familia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familia, or with status {@code 400 (Bad Request)} if the familia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/familias")
    public ResponseEntity<Familia> createFamilia(@Valid @RequestBody Familia familia) throws URISyntaxException {
        log.debug("REST request to save Familia : {}", familia);
        if (familia.getId() != null) {
            throw new BadRequestAlertException("A new familia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Familia result = familiaRepository.save(familia);
        return ResponseEntity.created(new URI("/api/familias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /familias} : Updates an existing familia.
     *
     * @param familia the familia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familia,
     * or with status {@code 400 (Bad Request)} if the familia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/familias")
    public ResponseEntity<Familia> updateFamilia(@Valid @RequestBody Familia familia) throws URISyntaxException {
        log.debug("REST request to update Familia : {}", familia);
        if (familia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Familia result = familiaRepository.save(familia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /familias} : get all the familias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familias in body.
     */
    @GetMapping("/familias")
    public List<Familia> getAllFamilias() {
        log.debug("REST request to get all Familias");
        return familiaRepository.findAll();
    }

    /**
     * {@code GET  /familias/:id} : get the "id" familia.
     *
     * @param id the id of the familia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/familias/{id}")
    public ResponseEntity<Familia> getFamilia(@PathVariable Long id) {
        log.debug("REST request to get Familia : {}", id);
        Optional<Familia> familia = familiaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(familia);
    }

    /**
     * {@code DELETE  /familias/:id} : delete the "id" familia.
     *
     * @param id the id of the familia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/familias/{id}")
    public ResponseEntity<Void> deleteFamilia(@PathVariable Long id) {
        log.debug("REST request to delete Familia : {}", id);
        familiaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
