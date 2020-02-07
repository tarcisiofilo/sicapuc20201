package com.sica.web.rest;

import com.sica.service.InstrumentoMonitoramentoService;
import com.sica.web.rest.errors.BadRequestAlertException;
import com.sica.service.dto.InstrumentoMonitoramentoDTO;

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
 * REST controller for managing {@link com.sica.domain.InstrumentoMonitoramento}.
 */
@RestController
@RequestMapping("/api")
public class InstrumentoMonitoramentoResource {

    private final Logger log = LoggerFactory.getLogger(InstrumentoMonitoramentoResource.class);

    private static final String ENTITY_NAME = "instrumentoMonitoramento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstrumentoMonitoramentoService instrumentoMonitoramentoService;

    public InstrumentoMonitoramentoResource(InstrumentoMonitoramentoService instrumentoMonitoramentoService) {
        this.instrumentoMonitoramentoService = instrumentoMonitoramentoService;
    }

    /**
     * {@code POST  /instrumento-monitoramentos} : Create a new instrumentoMonitoramento.
     *
     * @param instrumentoMonitoramentoDTO the instrumentoMonitoramentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instrumentoMonitoramentoDTO, or with status {@code 400 (Bad Request)} if the instrumentoMonitoramento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instrumento-monitoramentos")
    public ResponseEntity<InstrumentoMonitoramentoDTO> createInstrumentoMonitoramento(@Valid @RequestBody InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO) throws URISyntaxException {
        log.debug("REST request to save InstrumentoMonitoramento : {}", instrumentoMonitoramentoDTO);
        if (instrumentoMonitoramentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new instrumentoMonitoramento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstrumentoMonitoramentoDTO result = instrumentoMonitoramentoService.save(instrumentoMonitoramentoDTO);
        return ResponseEntity.created(new URI("/api/instrumento-monitoramentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instrumento-monitoramentos} : Updates an existing instrumentoMonitoramento.
     *
     * @param instrumentoMonitoramentoDTO the instrumentoMonitoramentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instrumentoMonitoramentoDTO,
     * or with status {@code 400 (Bad Request)} if the instrumentoMonitoramentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instrumentoMonitoramentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instrumento-monitoramentos")
    public ResponseEntity<InstrumentoMonitoramentoDTO> updateInstrumentoMonitoramento(@Valid @RequestBody InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO) throws URISyntaxException {
        log.debug("REST request to update InstrumentoMonitoramento : {}", instrumentoMonitoramentoDTO);
        if (instrumentoMonitoramentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstrumentoMonitoramentoDTO result = instrumentoMonitoramentoService.save(instrumentoMonitoramentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instrumentoMonitoramentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instrumento-monitoramentos} : get all the instrumentoMonitoramentos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instrumentoMonitoramentos in body.
     */
    @GetMapping("/instrumento-monitoramentos")
    public List<InstrumentoMonitoramentoDTO> getAllInstrumentoMonitoramentos(@RequestParam(required = false) String filter) {
        if ("vistoria-is-null".equals(filter)) {
            log.debug("REST request to get all InstrumentoMonitoramentos where vistoria is null");
            return instrumentoMonitoramentoService.findAllWhereVistoriaIsNull();
        }
        log.debug("REST request to get all InstrumentoMonitoramentos");
        return instrumentoMonitoramentoService.findAll();
    }

    /**
     * {@code GET  /instrumento-monitoramentos/:id} : get the "id" instrumentoMonitoramento.
     *
     * @param id the id of the instrumentoMonitoramentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instrumentoMonitoramentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instrumento-monitoramentos/{id}")
    public ResponseEntity<InstrumentoMonitoramentoDTO> getInstrumentoMonitoramento(@PathVariable Long id) {
        log.debug("REST request to get InstrumentoMonitoramento : {}", id);
        Optional<InstrumentoMonitoramentoDTO> instrumentoMonitoramentoDTO = instrumentoMonitoramentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instrumentoMonitoramentoDTO);
    }

    /**
     * {@code DELETE  /instrumento-monitoramentos/:id} : delete the "id" instrumentoMonitoramento.
     *
     * @param id the id of the instrumentoMonitoramentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instrumento-monitoramentos/{id}")
    public ResponseEntity<Void> deleteInstrumentoMonitoramento(@PathVariable Long id) {
        log.debug("REST request to delete InstrumentoMonitoramento : {}", id);
        instrumentoMonitoramentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
