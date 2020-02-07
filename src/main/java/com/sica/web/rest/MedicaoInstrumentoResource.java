package com.sica.web.rest;

import com.sica.service.MedicaoInstrumentoService;
import com.sica.web.rest.errors.BadRequestAlertException;
import com.sica.service.dto.MedicaoInstrumentoDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.sica.domain.MedicaoInstrumento}.
 */
@RestController
@RequestMapping("/api")
public class MedicaoInstrumentoResource {

    private final Logger log = LoggerFactory.getLogger(MedicaoInstrumentoResource.class);

    private static final String ENTITY_NAME = "medicaoInstrumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicaoInstrumentoService medicaoInstrumentoService;

    public MedicaoInstrumentoResource(MedicaoInstrumentoService medicaoInstrumentoService) {
        this.medicaoInstrumentoService = medicaoInstrumentoService;
    }

    /**
     * {@code POST  /medicao-instrumentos} : Create a new medicaoInstrumento.
     *
     * @param medicaoInstrumentoDTO the medicaoInstrumentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicaoInstrumentoDTO, or with status {@code 400 (Bad Request)} if the medicaoInstrumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicao-instrumentos")
    public ResponseEntity<MedicaoInstrumentoDTO> createMedicaoInstrumento(@Valid @RequestBody MedicaoInstrumentoDTO medicaoInstrumentoDTO) throws URISyntaxException {
        log.debug("REST request to save MedicaoInstrumento : {}", medicaoInstrumentoDTO);
        if (medicaoInstrumentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicaoInstrumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicaoInstrumentoDTO result = medicaoInstrumentoService.save(medicaoInstrumentoDTO);
        return ResponseEntity.created(new URI("/api/medicao-instrumentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicao-instrumentos} : Updates an existing medicaoInstrumento.
     *
     * @param medicaoInstrumentoDTO the medicaoInstrumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicaoInstrumentoDTO,
     * or with status {@code 400 (Bad Request)} if the medicaoInstrumentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicaoInstrumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicao-instrumentos")
    public ResponseEntity<MedicaoInstrumentoDTO> updateMedicaoInstrumento(@Valid @RequestBody MedicaoInstrumentoDTO medicaoInstrumentoDTO) throws URISyntaxException {
        log.debug("REST request to update MedicaoInstrumento : {}", medicaoInstrumentoDTO);
        if (medicaoInstrumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicaoInstrumentoDTO result = medicaoInstrumentoService.save(medicaoInstrumentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicaoInstrumentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medicao-instrumentos} : get all the medicaoInstrumentos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicaoInstrumentos in body.
     */
    @GetMapping("/medicao-instrumentos")
    public List<MedicaoInstrumentoDTO> getAllMedicaoInstrumentos(@RequestParam(required = false) String filter) {
        if ("incidente-is-null".equals(filter)) {
            log.debug("REST request to get all MedicaoInstrumentos where incidente is null");
            return medicaoInstrumentoService.findAllWhereIncidenteIsNull();
        }
        log.debug("REST request to get all MedicaoInstrumentos");
        return medicaoInstrumentoService.findAll();
    }

    /**
     * {@code GET  /medicao-instrumentos/:id} : get the "id" medicaoInstrumento.
     *
     * @param id the id of the medicaoInstrumentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicaoInstrumentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicao-instrumentos/{id}")
    public ResponseEntity<MedicaoInstrumentoDTO> getMedicaoInstrumento(@PathVariable Long id) {
        log.debug("REST request to get MedicaoInstrumento : {}", id);
        Optional<MedicaoInstrumentoDTO> medicaoInstrumentoDTO = medicaoInstrumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicaoInstrumentoDTO);
    }

    /**
     * {@code DELETE  /medicao-instrumentos/:id} : delete the "id" medicaoInstrumento.
     *
     * @param id the id of the medicaoInstrumentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicao-instrumentos/{id}")
    public ResponseEntity<Void> deleteMedicaoInstrumento(@PathVariable Long id) {
        log.debug("REST request to delete MedicaoInstrumento : {}", id);
        medicaoInstrumentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
