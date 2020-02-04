package com.sica.web.rest;

import com.sica.domain.NivelIncidente;
import com.sica.repository.NivelIncidenteRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    private final NivelIncidenteRepository nivelIncidenteRepository;

    public NivelIncidenteResource(NivelIncidenteRepository nivelIncidenteRepository) {
        this.nivelIncidenteRepository = nivelIncidenteRepository;
    }

    /**
     * {@code POST  /nivel-incidentes} : Create a new nivelIncidente.
     *
     * @param nivelIncidente the nivelIncidente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nivelIncidente, or with status {@code 400 (Bad Request)} if the nivelIncidente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nivel-incidentes")
    public ResponseEntity<NivelIncidente> createNivelIncidente(@Valid @RequestBody NivelIncidente nivelIncidente) throws URISyntaxException {
        log.debug("REST request to save NivelIncidente : {}", nivelIncidente);
        if (nivelIncidente.getId() != null) {
            throw new BadRequestAlertException("A new nivelIncidente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NivelIncidente result = nivelIncidenteRepository.save(nivelIncidente);
        return ResponseEntity.created(new URI("/api/nivel-incidentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nivel-incidentes} : Updates an existing nivelIncidente.
     *
     * @param nivelIncidente the nivelIncidente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nivelIncidente,
     * or with status {@code 400 (Bad Request)} if the nivelIncidente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nivelIncidente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nivel-incidentes")
    public ResponseEntity<NivelIncidente> updateNivelIncidente(@Valid @RequestBody NivelIncidente nivelIncidente) throws URISyntaxException {
        log.debug("REST request to update NivelIncidente : {}", nivelIncidente);
        if (nivelIncidente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NivelIncidente result = nivelIncidenteRepository.save(nivelIncidente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nivelIncidente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nivel-incidentes} : get all the nivelIncidentes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nivelIncidentes in body.
     */
    @GetMapping("/nivel-incidentes")
    public List<NivelIncidente> getAllNivelIncidentes(@RequestParam(required = false) String filter) {
        if ("incidente-is-null".equals(filter)) {
            log.debug("REST request to get all NivelIncidentes where incidente is null");
            return StreamSupport
                .stream(nivelIncidenteRepository.findAll().spliterator(), false)
                .filter(nivelIncidente -> nivelIncidente.getIncidente() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all NivelIncidentes");
        return nivelIncidenteRepository.findAll();
    }

    /**
     * {@code GET  /nivel-incidentes/:id} : get the "id" nivelIncidente.
     *
     * @param id the id of the nivelIncidente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nivelIncidente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nivel-incidentes/{id}")
    public ResponseEntity<NivelIncidente> getNivelIncidente(@PathVariable Long id) {
        log.debug("REST request to get NivelIncidente : {}", id);
        Optional<NivelIncidente> nivelIncidente = nivelIncidenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nivelIncidente);
    }

    /**
     * {@code DELETE  /nivel-incidentes/:id} : delete the "id" nivelIncidente.
     *
     * @param id the id of the nivelIncidente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nivel-incidentes/{id}")
    public ResponseEntity<Void> deleteNivelIncidente(@PathVariable Long id) {
        log.debug("REST request to delete NivelIncidente : {}", id);
        nivelIncidenteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
