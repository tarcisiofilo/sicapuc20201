package com.sica.web.rest;

import com.sica.domain.InstrumentoMonitoramento;
import com.sica.repository.InstrumentoMonitoramentoRepository;
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
 * REST controller for managing {@link com.sica.domain.InstrumentoMonitoramento}.
 */
@RestController
@RequestMapping("/api")
public class InstrumentoMonitoramentoResource {

    private final Logger log = LoggerFactory.getLogger(InstrumentoMonitoramentoResource.class);

    private static final String ENTITY_NAME = "instrumentoMonitoramento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstrumentoMonitoramentoRepository instrumentoMonitoramentoRepository;

    public InstrumentoMonitoramentoResource(InstrumentoMonitoramentoRepository instrumentoMonitoramentoRepository) {
        this.instrumentoMonitoramentoRepository = instrumentoMonitoramentoRepository;
    }

    /**
     * {@code POST  /instrumento-monitoramentos} : Create a new instrumentoMonitoramento.
     *
     * @param instrumentoMonitoramento the instrumentoMonitoramento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instrumentoMonitoramento, or with status {@code 400 (Bad Request)} if the instrumentoMonitoramento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instrumento-monitoramentos")
    public ResponseEntity<InstrumentoMonitoramento> createInstrumentoMonitoramento(@Valid @RequestBody InstrumentoMonitoramento instrumentoMonitoramento) throws URISyntaxException {
        log.debug("REST request to save InstrumentoMonitoramento : {}", instrumentoMonitoramento);
        if (instrumentoMonitoramento.getId() != null) {
            throw new BadRequestAlertException("A new instrumentoMonitoramento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstrumentoMonitoramento result = instrumentoMonitoramentoRepository.save(instrumentoMonitoramento);
        return ResponseEntity.created(new URI("/api/instrumento-monitoramentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instrumento-monitoramentos} : Updates an existing instrumentoMonitoramento.
     *
     * @param instrumentoMonitoramento the instrumentoMonitoramento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instrumentoMonitoramento,
     * or with status {@code 400 (Bad Request)} if the instrumentoMonitoramento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instrumentoMonitoramento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instrumento-monitoramentos")
    public ResponseEntity<InstrumentoMonitoramento> updateInstrumentoMonitoramento(@Valid @RequestBody InstrumentoMonitoramento instrumentoMonitoramento) throws URISyntaxException {
        log.debug("REST request to update InstrumentoMonitoramento : {}", instrumentoMonitoramento);
        if (instrumentoMonitoramento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstrumentoMonitoramento result = instrumentoMonitoramentoRepository.save(instrumentoMonitoramento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instrumentoMonitoramento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instrumento-monitoramentos} : get all the instrumentoMonitoramentos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instrumentoMonitoramentos in body.
     */
    @GetMapping("/instrumento-monitoramentos")
    public List<InstrumentoMonitoramento> getAllInstrumentoMonitoramentos(@RequestParam(required = false) String filter) {
        if ("vistoria-is-null".equals(filter)) {
            log.debug("REST request to get all InstrumentoMonitoramentos where vistoria is null");
            return StreamSupport
                .stream(instrumentoMonitoramentoRepository.findAll().spliterator(), false)
                .filter(instrumentoMonitoramento -> instrumentoMonitoramento.getVistoria() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all InstrumentoMonitoramentos");
        return instrumentoMonitoramentoRepository.findAll();
    }

    /**
     * {@code GET  /instrumento-monitoramentos/:id} : get the "id" instrumentoMonitoramento.
     *
     * @param id the id of the instrumentoMonitoramento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instrumentoMonitoramento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instrumento-monitoramentos/{id}")
    public ResponseEntity<InstrumentoMonitoramento> getInstrumentoMonitoramento(@PathVariable Long id) {
        log.debug("REST request to get InstrumentoMonitoramento : {}", id);
        Optional<InstrumentoMonitoramento> instrumentoMonitoramento = instrumentoMonitoramentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(instrumentoMonitoramento);
    }

    /**
     * {@code DELETE  /instrumento-monitoramentos/:id} : delete the "id" instrumentoMonitoramento.
     *
     * @param id the id of the instrumentoMonitoramento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instrumento-monitoramentos/{id}")
    public ResponseEntity<Void> deleteInstrumentoMonitoramento(@PathVariable Long id) {
        log.debug("REST request to delete InstrumentoMonitoramento : {}", id);
        instrumentoMonitoramentoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
