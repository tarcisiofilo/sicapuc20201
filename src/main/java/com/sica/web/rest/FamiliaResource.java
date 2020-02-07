package com.sica.web.rest;

import com.sica.service.FamiliaService;
import com.sica.web.rest.errors.BadRequestAlertException;
import com.sica.service.dto.FamiliaDTO;

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

    private final FamiliaService familiaService;

    public FamiliaResource(FamiliaService familiaService) {
        this.familiaService = familiaService;
    }

    /**
     * {@code POST  /familias} : Create a new familia.
     *
     * @param familiaDTO the familiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familiaDTO, or with status {@code 400 (Bad Request)} if the familia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/familias")
    public ResponseEntity<FamiliaDTO> createFamilia(@Valid @RequestBody FamiliaDTO familiaDTO) throws URISyntaxException {
        log.debug("REST request to save Familia : {}", familiaDTO);
        if (familiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new familia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamiliaDTO result = familiaService.save(familiaDTO);
        return ResponseEntity.created(new URI("/api/familias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /familias} : Updates an existing familia.
     *
     * @param familiaDTO the familiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familiaDTO,
     * or with status {@code 400 (Bad Request)} if the familiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/familias")
    public ResponseEntity<FamiliaDTO> updateFamilia(@Valid @RequestBody FamiliaDTO familiaDTO) throws URISyntaxException {
        log.debug("REST request to update Familia : {}", familiaDTO);
        if (familiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamiliaDTO result = familiaService.save(familiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /familias} : get all the familias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familias in body.
     */
    @GetMapping("/familias")
    public List<FamiliaDTO> getAllFamilias() {
        log.debug("REST request to get all Familias");
        return familiaService.findAll();
    }

    /**
     * {@code GET  /familias/:id} : get the "id" familia.
     *
     * @param id the id of the familiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/familias/{id}")
    public ResponseEntity<FamiliaDTO> getFamilia(@PathVariable Long id) {
        log.debug("REST request to get Familia : {}", id);
        Optional<FamiliaDTO> familiaDTO = familiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familiaDTO);
    }

    /**
     * {@code DELETE  /familias/:id} : delete the "id" familia.
     *
     * @param id the id of the familiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/familias/{id}")
    public ResponseEntity<Void> deleteFamilia(@PathVariable Long id) {
        log.debug("REST request to delete Familia : {}", id);
        familiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
