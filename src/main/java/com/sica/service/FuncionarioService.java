package com.sica.service;

import com.sica.domain.Funcionario;
import com.sica.repository.FuncionarioRepository;
import com.sica.service.dto.FuncionarioDTO;
import com.sica.service.mapper.FuncionarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Funcionario}.
 */
@Service
@Transactional
public class FuncionarioService {

    private final Logger log = LoggerFactory.getLogger(FuncionarioService.class);

    private final FuncionarioRepository funcionarioRepository;

    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    /**
     * Save a funcionario.
     *
     * @param funcionarioDTO the entity to save.
     * @return the persisted entity.
     */
    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO) {
        log.debug("Request to save Funcionario : {}", funcionarioDTO);
        Funcionario funcionario = funcionarioMapper.toEntity(funcionarioDTO);
        funcionario = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toDto(funcionario);
    }

    /**
     * Get all the funcionarios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FuncionarioDTO> findAll() {
        log.debug("Request to get all Funcionarios");
        return funcionarioRepository.findAll().stream()
            .map(funcionarioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one funcionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuncionarioDTO> findOne(Long id) {
        log.debug("Request to get Funcionario : {}", id);
        return funcionarioRepository.findById(id)
            .map(funcionarioMapper::toDto);
    }

    /**
     * Delete the funcionario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Funcionario : {}", id);
        funcionarioRepository.deleteById(id);
    }
}
