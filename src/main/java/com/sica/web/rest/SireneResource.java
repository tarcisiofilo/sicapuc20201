package com.sica.web.rest;

import com.sica.domain.Sirene;
import com.sica.repository.SireneRepository;
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
 * REST controller for managing {@link com.sica.domain.Sirene}.
 */
@RestController
@RequestMapping("/api")
public class SireneResource {

    private final Logger log = LoggerFactory.getLogger(SireneResource.class);

    private static final String ENTITY_NAME = "sirene";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SireneRepository sireneRepository;

    public SireneResource(SireneRepository sireneRepository) {
        this.sireneRepository = sireneRepository;
    }

    /**
     * {@code POST  /sirenes} : Create a new sirene.
     *
     * @param sirene the sirene to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sirene, or with status {@code 400 (Bad Request)} if the sirene has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sirenes")
    public ResponseEntity<Sirene> createSirene(@Valid @RequestBody Sirene sirene) throws URISyntaxException {
        log.debug("REST request to save Sirene : {}", sirene);
        if (sirene.getId() != null) {
            throw new BadRequestAlertException("A new sirene cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sirene result = sireneRepository.save(sirene);
        return ResponseEntity.created(new URI("/api/sirenes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sirenes} : Updates an existing sirene.
     *
     * @param sirene the sirene to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sirene,
     * or with status {@code 400 (Bad Request)} if the sirene is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sirene couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sirenes")
    public ResponseEntity<Sirene> updateSirene(@Valid @RequestBody Sirene sirene) throws URISyntaxException {
        log.debug("REST request to update Sirene : {}", sirene);
        if (sirene.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sirene result = sireneRepository.save(sirene);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sirene.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sirenes} : get all the sirenes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sirenes in body.
     */
    @GetMapping("/sirenes")
    public List<Sirene> getAllSirenes() {
        log.debug("REST request to get all Sirenes");
        return sireneRepository.findAll();
    }

    /**
     * {@code GET  /sirenes/:id} : get the "id" sirene.
     *
     * @param id the id of the sirene to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sirene, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sirenes/{id}")
    public ResponseEntity<Sirene> getSirene(@PathVariable Long id) {
        log.debug("REST request to get Sirene : {}", id);
        Optional<Sirene> sirene = sireneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sirene);
    }

    /**
     * {@code DELETE  /sirenes/:id} : delete the "id" sirene.
     *
     * @param id the id of the sirene to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sirenes/{id}")
    public ResponseEntity<Void> deleteSirene(@PathVariable Long id) {
        log.debug("REST request to delete Sirene : {}", id);
        sireneRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
