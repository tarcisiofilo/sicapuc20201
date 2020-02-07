package com.sica.web.rest;

import com.sica.service.MinaOperacaoService;
import com.sica.web.rest.errors.BadRequestAlertException;
import com.sica.service.dto.MinaOperacaoDTO;

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
 * REST controller for managing {@link com.sica.domain.MinaOperacao}.
 */
@RestController
@RequestMapping("/api")
public class MinaOperacaoResource {

    private final Logger log = LoggerFactory.getLogger(MinaOperacaoResource.class);

    private static final String ENTITY_NAME = "minaOperacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MinaOperacaoService minaOperacaoService;

    public MinaOperacaoResource(MinaOperacaoService minaOperacaoService) {
        this.minaOperacaoService = minaOperacaoService;
    }

    /**
     * {@code POST  /mina-operacaos} : Create a new minaOperacao.
     *
     * @param minaOperacaoDTO the minaOperacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new minaOperacaoDTO, or with status {@code 400 (Bad Request)} if the minaOperacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mina-operacaos")
    public ResponseEntity<MinaOperacaoDTO> createMinaOperacao(@Valid @RequestBody MinaOperacaoDTO minaOperacaoDTO) throws URISyntaxException {
        log.debug("REST request to save MinaOperacao : {}", minaOperacaoDTO);
        if (minaOperacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new minaOperacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MinaOperacaoDTO result = minaOperacaoService.save(minaOperacaoDTO);
        return ResponseEntity.created(new URI("/api/mina-operacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mina-operacaos} : Updates an existing minaOperacao.
     *
     * @param minaOperacaoDTO the minaOperacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated minaOperacaoDTO,
     * or with status {@code 400 (Bad Request)} if the minaOperacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the minaOperacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mina-operacaos")
    public ResponseEntity<MinaOperacaoDTO> updateMinaOperacao(@Valid @RequestBody MinaOperacaoDTO minaOperacaoDTO) throws URISyntaxException {
        log.debug("REST request to update MinaOperacao : {}", minaOperacaoDTO);
        if (minaOperacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MinaOperacaoDTO result = minaOperacaoService.save(minaOperacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, minaOperacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mina-operacaos} : get all the minaOperacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of minaOperacaos in body.
     */
    @GetMapping("/mina-operacaos")
    public List<MinaOperacaoDTO> getAllMinaOperacaos() {
        log.debug("REST request to get all MinaOperacaos");
        return minaOperacaoService.findAll();
    }

    /**
     * {@code GET  /mina-operacaos/:id} : get the "id" minaOperacao.
     *
     * @param id the id of the minaOperacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the minaOperacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mina-operacaos/{id}")
    public ResponseEntity<MinaOperacaoDTO> getMinaOperacao(@PathVariable Long id) {
        log.debug("REST request to get MinaOperacao : {}", id);
        Optional<MinaOperacaoDTO> minaOperacaoDTO = minaOperacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(minaOperacaoDTO);
    }

    /**
     * {@code DELETE  /mina-operacaos/:id} : delete the "id" minaOperacao.
     *
     * @param id the id of the minaOperacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mina-operacaos/{id}")
    public ResponseEntity<Void> deleteMinaOperacao(@PathVariable Long id) {
        log.debug("REST request to delete MinaOperacao : {}", id);
        minaOperacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
