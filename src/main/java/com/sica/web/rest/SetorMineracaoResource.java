package com.sica.web.rest;

import com.sica.domain.SetorMineracao;
import com.sica.repository.SetorMineracaoRepository;
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
 * REST controller for managing {@link com.sica.domain.SetorMineracao}.
 */
@RestController
@RequestMapping("/api")
public class SetorMineracaoResource {

    private final Logger log = LoggerFactory.getLogger(SetorMineracaoResource.class);

    private static final String ENTITY_NAME = "setorMineracao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SetorMineracaoRepository setorMineracaoRepository;

    public SetorMineracaoResource(SetorMineracaoRepository setorMineracaoRepository) {
        this.setorMineracaoRepository = setorMineracaoRepository;
    }

    /**
     * {@code POST  /setor-mineracaos} : Create a new setorMineracao.
     *
     * @param setorMineracao the setorMineracao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new setorMineracao, or with status {@code 400 (Bad Request)} if the setorMineracao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/setor-mineracaos")
    public ResponseEntity<SetorMineracao> createSetorMineracao(@Valid @RequestBody SetorMineracao setorMineracao) throws URISyntaxException {
        log.debug("REST request to save SetorMineracao : {}", setorMineracao);
        if (setorMineracao.getId() != null) {
            throw new BadRequestAlertException("A new setorMineracao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SetorMineracao result = setorMineracaoRepository.save(setorMineracao);
        return ResponseEntity.created(new URI("/api/setor-mineracaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /setor-mineracaos} : Updates an existing setorMineracao.
     *
     * @param setorMineracao the setorMineracao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated setorMineracao,
     * or with status {@code 400 (Bad Request)} if the setorMineracao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the setorMineracao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/setor-mineracaos")
    public ResponseEntity<SetorMineracao> updateSetorMineracao(@Valid @RequestBody SetorMineracao setorMineracao) throws URISyntaxException {
        log.debug("REST request to update SetorMineracao : {}", setorMineracao);
        if (setorMineracao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SetorMineracao result = setorMineracaoRepository.save(setorMineracao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, setorMineracao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /setor-mineracaos} : get all the setorMineracaos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of setorMineracaos in body.
     */
    @GetMapping("/setor-mineracaos")
    public List<SetorMineracao> getAllSetorMineracaos(@RequestParam(required = false) String filter) {
        if ("ativo-is-null".equals(filter)) {
            log.debug("REST request to get all SetorMineracaos where ativo is null");
            return StreamSupport
                .stream(setorMineracaoRepository.findAll().spliterator(), false)
                .filter(setorMineracao -> setorMineracao.getAtivo() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all SetorMineracaos");
        return setorMineracaoRepository.findAll();
    }

    /**
     * {@code GET  /setor-mineracaos/:id} : get the "id" setorMineracao.
     *
     * @param id the id of the setorMineracao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the setorMineracao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/setor-mineracaos/{id}")
    public ResponseEntity<SetorMineracao> getSetorMineracao(@PathVariable Long id) {
        log.debug("REST request to get SetorMineracao : {}", id);
        Optional<SetorMineracao> setorMineracao = setorMineracaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(setorMineracao);
    }

    /**
     * {@code DELETE  /setor-mineracaos/:id} : delete the "id" setorMineracao.
     *
     * @param id the id of the setorMineracao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/setor-mineracaos/{id}")
    public ResponseEntity<Void> deleteSetorMineracao(@PathVariable Long id) {
        log.debug("REST request to delete SetorMineracao : {}", id);
        setorMineracaoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
