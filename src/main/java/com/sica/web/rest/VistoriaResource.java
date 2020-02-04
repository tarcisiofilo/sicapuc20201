package com.sica.web.rest;

import com.sica.domain.Vistoria;
import com.sica.repository.VistoriaRepository;
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
 * REST controller for managing {@link com.sica.domain.Vistoria}.
 */
@RestController
@RequestMapping("/api")
public class VistoriaResource {

    private final Logger log = LoggerFactory.getLogger(VistoriaResource.class);

    private static final String ENTITY_NAME = "vistoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VistoriaRepository vistoriaRepository;

    public VistoriaResource(VistoriaRepository vistoriaRepository) {
        this.vistoriaRepository = vistoriaRepository;
    }

    /**
     * {@code POST  /vistorias} : Create a new vistoria.
     *
     * @param vistoria the vistoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vistoria, or with status {@code 400 (Bad Request)} if the vistoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vistorias")
    public ResponseEntity<Vistoria> createVistoria(@Valid @RequestBody Vistoria vistoria) throws URISyntaxException {
        log.debug("REST request to save Vistoria : {}", vistoria);
        if (vistoria.getId() != null) {
            throw new BadRequestAlertException("A new vistoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vistoria result = vistoriaRepository.save(vistoria);
        return ResponseEntity.created(new URI("/api/vistorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vistorias} : Updates an existing vistoria.
     *
     * @param vistoria the vistoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vistoria,
     * or with status {@code 400 (Bad Request)} if the vistoria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vistoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vistorias")
    public ResponseEntity<Vistoria> updateVistoria(@Valid @RequestBody Vistoria vistoria) throws URISyntaxException {
        log.debug("REST request to update Vistoria : {}", vistoria);
        if (vistoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vistoria result = vistoriaRepository.save(vistoria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vistoria.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vistorias} : get all the vistorias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vistorias in body.
     */
    @GetMapping("/vistorias")
    public List<Vistoria> getAllVistorias() {
        log.debug("REST request to get all Vistorias");
        return vistoriaRepository.findAll();
    }

    /**
     * {@code GET  /vistorias/:id} : get the "id" vistoria.
     *
     * @param id the id of the vistoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vistoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vistorias/{id}")
    public ResponseEntity<Vistoria> getVistoria(@PathVariable Long id) {
        log.debug("REST request to get Vistoria : {}", id);
        Optional<Vistoria> vistoria = vistoriaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vistoria);
    }

    /**
     * {@code DELETE  /vistorias/:id} : delete the "id" vistoria.
     *
     * @param id the id of the vistoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vistorias/{id}")
    public ResponseEntity<Void> deleteVistoria(@PathVariable Long id) {
        log.debug("REST request to delete Vistoria : {}", id);
        vistoriaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
