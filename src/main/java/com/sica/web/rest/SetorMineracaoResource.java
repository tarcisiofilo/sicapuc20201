package com.sica.web.rest;

import com.sica.service.SetorMineracaoService;
import com.sica.web.rest.errors.BadRequestAlertException;
import com.sica.service.dto.SetorMineracaoDTO;

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
 * REST controller for managing {@link com.sica.domain.SetorMineracao}.
 */
@RestController
@RequestMapping("/api")
public class SetorMineracaoResource {

    private final Logger log = LoggerFactory.getLogger(SetorMineracaoResource.class);

    private static final String ENTITY_NAME = "setorMineracao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SetorMineracaoService setorMineracaoService;

    public SetorMineracaoResource(SetorMineracaoService setorMineracaoService) {
        this.setorMineracaoService = setorMineracaoService;
    }

    /**
     * {@code POST  /setor-mineracaos} : Create a new setorMineracao.
     *
     * @param setorMineracaoDTO the setorMineracaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new setorMineracaoDTO, or with status {@code 400 (Bad Request)} if the setorMineracao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/setor-mineracaos")
    public ResponseEntity<SetorMineracaoDTO> createSetorMineracao(@Valid @RequestBody SetorMineracaoDTO setorMineracaoDTO) throws URISyntaxException {
        log.debug("REST request to save SetorMineracao : {}", setorMineracaoDTO);
        if (setorMineracaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new setorMineracao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SetorMineracaoDTO result = setorMineracaoService.save(setorMineracaoDTO);
        return ResponseEntity.created(new URI("/api/setor-mineracaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /setor-mineracaos} : Updates an existing setorMineracao.
     *
     * @param setorMineracaoDTO the setorMineracaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated setorMineracaoDTO,
     * or with status {@code 400 (Bad Request)} if the setorMineracaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the setorMineracaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/setor-mineracaos")
    public ResponseEntity<SetorMineracaoDTO> updateSetorMineracao(@Valid @RequestBody SetorMineracaoDTO setorMineracaoDTO) throws URISyntaxException {
        log.debug("REST request to update SetorMineracao : {}", setorMineracaoDTO);
        if (setorMineracaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SetorMineracaoDTO result = setorMineracaoService.save(setorMineracaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, setorMineracaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /setor-mineracaos} : get all the setorMineracaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of setorMineracaos in body.
     */
    @GetMapping("/setor-mineracaos")
    public List<SetorMineracaoDTO> getAllSetorMineracaos() {
        log.debug("REST request to get all SetorMineracaos");
        return setorMineracaoService.findAll();
    }

    /**
     * {@code GET  /setor-mineracaos/:id} : get the "id" setorMineracao.
     *
     * @param id the id of the setorMineracaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the setorMineracaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/setor-mineracaos/{id}")
    public ResponseEntity<SetorMineracaoDTO> getSetorMineracao(@PathVariable Long id) {
        log.debug("REST request to get SetorMineracao : {}", id);
        Optional<SetorMineracaoDTO> setorMineracaoDTO = setorMineracaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(setorMineracaoDTO);
    }

    /**
     * {@code DELETE  /setor-mineracaos/:id} : delete the "id" setorMineracao.
     *
     * @param id the id of the setorMineracaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/setor-mineracaos/{id}")
    public ResponseEntity<Void> deleteSetorMineracao(@PathVariable Long id) {
        log.debug("REST request to delete SetorMineracao : {}", id);
        setorMineracaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
