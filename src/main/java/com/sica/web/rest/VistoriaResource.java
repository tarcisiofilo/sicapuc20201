package com.sica.web.rest;

import com.sica.service.VistoriaService;
import com.sica.web.rest.errors.BadRequestAlertException;
import com.sica.service.dto.VistoriaDTO;

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

    private final VistoriaService vistoriaService;

    public VistoriaResource(VistoriaService vistoriaService) {
        this.vistoriaService = vistoriaService;
    }

    /**
     * {@code POST  /vistorias} : Create a new vistoria.
     *
     * @param vistoriaDTO the vistoriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vistoriaDTO, or with status {@code 400 (Bad Request)} if the vistoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vistorias")
    public ResponseEntity<VistoriaDTO> createVistoria(@Valid @RequestBody VistoriaDTO vistoriaDTO) throws URISyntaxException {
        log.debug("REST request to save Vistoria : {}", vistoriaDTO);
        if (vistoriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new vistoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VistoriaDTO result = vistoriaService.save(vistoriaDTO);
        return ResponseEntity.created(new URI("/api/vistorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vistorias} : Updates an existing vistoria.
     *
     * @param vistoriaDTO the vistoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vistoriaDTO,
     * or with status {@code 400 (Bad Request)} if the vistoriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vistoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vistorias")
    public ResponseEntity<VistoriaDTO> updateVistoria(@Valid @RequestBody VistoriaDTO vistoriaDTO) throws URISyntaxException {
        log.debug("REST request to update Vistoria : {}", vistoriaDTO);
        if (vistoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VistoriaDTO result = vistoriaService.save(vistoriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vistoriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vistorias} : get all the vistorias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vistorias in body.
     */
    @GetMapping("/vistorias")
    public List<VistoriaDTO> getAllVistorias() {
        log.debug("REST request to get all Vistorias");
        return vistoriaService.findAll();
    }

    /**
     * {@code GET  /vistorias/:id} : get the "id" vistoria.
     *
     * @param id the id of the vistoriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vistoriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vistorias/{id}")
    public ResponseEntity<VistoriaDTO> getVistoria(@PathVariable Long id) {
        log.debug("REST request to get Vistoria : {}", id);
        Optional<VistoriaDTO> vistoriaDTO = vistoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vistoriaDTO);
    }

    /**
     * {@code DELETE  /vistorias/:id} : delete the "id" vistoria.
     *
     * @param id the id of the vistoriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vistorias/{id}")
    public ResponseEntity<Void> deleteVistoria(@PathVariable Long id) {
        log.debug("REST request to delete Vistoria : {}", id);
        vistoriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
