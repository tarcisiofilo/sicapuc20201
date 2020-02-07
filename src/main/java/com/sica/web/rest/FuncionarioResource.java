package com.sica.web.rest;

import com.sica.service.FuncionarioService;
import com.sica.web.rest.errors.BadRequestAlertException;
import com.sica.service.dto.FuncionarioDTO;

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
 * REST controller for managing {@link com.sica.domain.Funcionario}.
 */
@RestController
@RequestMapping("/api")
public class FuncionarioResource {

    private final Logger log = LoggerFactory.getLogger(FuncionarioResource.class);

    private static final String ENTITY_NAME = "funcionario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuncionarioService funcionarioService;

    public FuncionarioResource(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    /**
     * {@code POST  /funcionarios} : Create a new funcionario.
     *
     * @param funcionarioDTO the funcionarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcionarioDTO, or with status {@code 400 (Bad Request)} if the funcionario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funcionarios")
    public ResponseEntity<FuncionarioDTO> createFuncionario(@Valid @RequestBody FuncionarioDTO funcionarioDTO) throws URISyntaxException {
        log.debug("REST request to save Funcionario : {}", funcionarioDTO);
        if (funcionarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new funcionario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuncionarioDTO result = funcionarioService.save(funcionarioDTO);
        return ResponseEntity.created(new URI("/api/funcionarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funcionarios} : Updates an existing funcionario.
     *
     * @param funcionarioDTO the funcionarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionarioDTO,
     * or with status {@code 400 (Bad Request)} if the funcionarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcionarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funcionarios")
    public ResponseEntity<FuncionarioDTO> updateFuncionario(@Valid @RequestBody FuncionarioDTO funcionarioDTO) throws URISyntaxException {
        log.debug("REST request to update Funcionario : {}", funcionarioDTO);
        if (funcionarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FuncionarioDTO result = funcionarioService.save(funcionarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /funcionarios} : get all the funcionarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcionarios in body.
     */
    @GetMapping("/funcionarios")
    public List<FuncionarioDTO> getAllFuncionarios() {
        log.debug("REST request to get all Funcionarios");
        return funcionarioService.findAll();
    }

    /**
     * {@code GET  /funcionarios/:id} : get the "id" funcionario.
     *
     * @param id the id of the funcionarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcionarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<FuncionarioDTO> getFuncionario(@PathVariable Long id) {
        log.debug("REST request to get Funcionario : {}", id);
        Optional<FuncionarioDTO> funcionarioDTO = funcionarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(funcionarioDTO);
    }

    /**
     * {@code DELETE  /funcionarios/:id} : delete the "id" funcionario.
     *
     * @param id the id of the funcionarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funcionarios/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        log.debug("REST request to delete Funcionario : {}", id);
        funcionarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
