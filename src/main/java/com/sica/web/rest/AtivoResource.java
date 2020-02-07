package com.sica.web.rest;

import com.sica.service.AtivoService;
import com.sica.web.rest.errors.BadRequestAlertException;
import com.sica.service.dto.AtivoDTO;

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
 * REST controller for managing {@link com.sica.domain.Ativo}.
 */
@RestController
@RequestMapping("/api")
public class AtivoResource {

    private final Logger log = LoggerFactory.getLogger(AtivoResource.class);

    private static final String ENTITY_NAME = "ativo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtivoService ativoService;

    public AtivoResource(AtivoService ativoService) {
        this.ativoService = ativoService;
    }

    /**
     * {@code POST  /ativos} : Create a new ativo.
     *
     * @param ativoDTO the ativoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ativoDTO, or with status {@code 400 (Bad Request)} if the ativo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ativos")
    public ResponseEntity<AtivoDTO> createAtivo(@Valid @RequestBody AtivoDTO ativoDTO) throws URISyntaxException {
        log.debug("REST request to save Ativo : {}", ativoDTO);
        if (ativoDTO.getId() != null) {
            throw new BadRequestAlertException("A new ativo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AtivoDTO result = ativoService.save(ativoDTO);
        return ResponseEntity.created(new URI("/api/ativos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ativos} : Updates an existing ativo.
     *
     * @param ativoDTO the ativoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ativoDTO,
     * or with status {@code 400 (Bad Request)} if the ativoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ativoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ativos")
    public ResponseEntity<AtivoDTO> updateAtivo(@Valid @RequestBody AtivoDTO ativoDTO) throws URISyntaxException {
        log.debug("REST request to update Ativo : {}", ativoDTO);
        if (ativoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AtivoDTO result = ativoService.save(ativoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ativoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ativos} : get all the ativos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ativos in body.
     */
    @GetMapping("/ativos")
    public List<AtivoDTO> getAllAtivos() {
        log.debug("REST request to get all Ativos");
        return ativoService.findAll();
    }

    /**
     * {@code GET  /ativos/:id} : get the "id" ativo.
     *
     * @param id the id of the ativoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ativoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ativos/{id}")
    public ResponseEntity<AtivoDTO> getAtivo(@PathVariable Long id) {
        log.debug("REST request to get Ativo : {}", id);
        Optional<AtivoDTO> ativoDTO = ativoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ativoDTO);
    }

    /**
     * {@code DELETE  /ativos/:id} : delete the "id" ativo.
     *
     * @param id the id of the ativoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ativos/{id}")
    public ResponseEntity<Void> deleteAtivo(@PathVariable Long id) {
        log.debug("REST request to delete Ativo : {}", id);
        ativoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
