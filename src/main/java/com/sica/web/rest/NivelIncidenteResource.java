package com.sica.web.rest;

import com.sica.service.NivelIncidenteService;
import com.sica.web.rest.errors.BadRequestAlertException;
import com.sica.service.dto.NivelIncidenteDTO;

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
 * REST controller for managing {@link com.sica.domain.NivelIncidente}.
 */
@RestController
@RequestMapping("/api")
public class NivelIncidenteResource {

    private final Logger log = LoggerFactory.getLogger(NivelIncidenteResource.class);

    private static final String ENTITY_NAME = "nivelIncidente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NivelIncidenteService nivelIncidenteService;

    public NivelIncidenteResource(NivelIncidenteService nivelIncidenteService) {
        this.nivelIncidenteService = nivelIncidenteService;
    }

    /**
     * {@code POST  /nivel-incidentes} : Create a new nivelIncidente.
     *
     * @param nivelIncidenteDTO the nivelIncidenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nivelIncidenteDTO, or with status {@code 400 (Bad Request)} if the nivelIncidente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nivel-incidentes")
    public ResponseEntity<NivelIncidenteDTO> createNivelIncidente(@Valid @RequestBody NivelIncidenteDTO nivelIncidenteDTO) throws URISyntaxException {
        log.debug("REST request to save NivelIncidente : {}", nivelIncidenteDTO);
        if (nivelIncidenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new nivelIncidente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NivelIncidenteDTO result = nivelIncidenteService.save(nivelIncidenteDTO);
        return ResponseEntity.created(new URI("/api/nivel-incidentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nivel-incidentes} : Updates an existing nivelIncidente.
     *
     * @param nivelIncidenteDTO the nivelIncidenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nivelIncidenteDTO,
     * or with status {@code 400 (Bad Request)} if the nivelIncidenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nivelIncidenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nivel-incidentes")
    public ResponseEntity<NivelIncidenteDTO> updateNivelIncidente(@Valid @RequestBody NivelIncidenteDTO nivelIncidenteDTO) throws URISyntaxException {
        log.debug("REST request to update NivelIncidente : {}", nivelIncidenteDTO);
        if (nivelIncidenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NivelIncidenteDTO result = nivelIncidenteService.save(nivelIncidenteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nivelIncidenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nivel-incidentes} : get all the nivelIncidentes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nivelIncidentes in body.
     */
    @GetMapping("/nivel-incidentes")
    public List<NivelIncidenteDTO> getAllNivelIncidentes() {
        log.debug("REST request to get all NivelIncidentes");
        return nivelIncidenteService.findAll();
    }

    /**
     * {@code GET  /nivel-incidentes/:id} : get the "id" nivelIncidente.
     *
     * @param id the id of the nivelIncidenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nivelIncidenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nivel-incidentes/{id}")
    public ResponseEntity<NivelIncidenteDTO> getNivelIncidente(@PathVariable Long id) {
        log.debug("REST request to get NivelIncidente : {}", id);
        Optional<NivelIncidenteDTO> nivelIncidenteDTO = nivelIncidenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nivelIncidenteDTO);
    }

    /**
     * {@code DELETE  /nivel-incidentes/:id} : delete the "id" nivelIncidente.
     *
     * @param id the id of the nivelIncidenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nivel-incidentes/{id}")
    public ResponseEntity<Void> deleteNivelIncidente(@PathVariable Long id) {
        log.debug("REST request to delete NivelIncidente : {}", id);
        nivelIncidenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
