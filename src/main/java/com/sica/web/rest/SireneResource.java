package com.sica.web.rest;

import com.sica.service.SireneService;
import com.sica.web.rest.errors.BadRequestAlertException;
import com.sica.service.dto.SireneDTO;

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

    private final SireneService sireneService;

    public SireneResource(SireneService sireneService) {
        this.sireneService = sireneService;
    }

    /**
     * {@code POST  /sirenes} : Create a new sirene.
     *
     * @param sireneDTO the sireneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sireneDTO, or with status {@code 400 (Bad Request)} if the sirene has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sirenes")
    public ResponseEntity<SireneDTO> createSirene(@Valid @RequestBody SireneDTO sireneDTO) throws URISyntaxException {
        log.debug("REST request to save Sirene : {}", sireneDTO);
        if (sireneDTO.getId() != null) {
            throw new BadRequestAlertException("A new sirene cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SireneDTO result = sireneService.save(sireneDTO);
        return ResponseEntity.created(new URI("/api/sirenes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sirenes} : Updates an existing sirene.
     *
     * @param sireneDTO the sireneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sireneDTO,
     * or with status {@code 400 (Bad Request)} if the sireneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sireneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sirenes")
    public ResponseEntity<SireneDTO> updateSirene(@Valid @RequestBody SireneDTO sireneDTO) throws URISyntaxException {
        log.debug("REST request to update Sirene : {}", sireneDTO);
        if (sireneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SireneDTO result = sireneService.save(sireneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sireneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sirenes} : get all the sirenes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sirenes in body.
     */
    @GetMapping("/sirenes")
    public List<SireneDTO> getAllSirenes() {
        log.debug("REST request to get all Sirenes");
        return sireneService.findAll();
    }

    /**
     * {@code GET  /sirenes/:id} : get the "id" sirene.
     *
     * @param id the id of the sireneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sireneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sirenes/{id}")
    public ResponseEntity<SireneDTO> getSirene(@PathVariable Long id) {
        log.debug("REST request to get Sirene : {}", id);
        Optional<SireneDTO> sireneDTO = sireneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sireneDTO);
    }

    /**
     * {@code DELETE  /sirenes/:id} : delete the "id" sirene.
     *
     * @param id the id of the sireneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sirenes/{id}")
    public ResponseEntity<Void> deleteSirene(@PathVariable Long id) {
        log.debug("REST request to delete Sirene : {}", id);
        sireneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
