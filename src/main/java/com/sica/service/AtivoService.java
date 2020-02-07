package com.sica.service;

import com.sica.domain.Ativo;
import com.sica.repository.AtivoRepository;
import com.sica.service.dto.AtivoDTO;
import com.sica.service.mapper.AtivoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Ativo}.
 */
@Service
@Transactional
public class AtivoService {

    private final Logger log = LoggerFactory.getLogger(AtivoService.class);

    private final AtivoRepository ativoRepository;

    private final AtivoMapper ativoMapper;

    public AtivoService(AtivoRepository ativoRepository, AtivoMapper ativoMapper) {
        this.ativoRepository = ativoRepository;
        this.ativoMapper = ativoMapper;
    }

    /**
     * Save a ativo.
     *
     * @param ativoDTO the entity to save.
     * @return the persisted entity.
     */
    public AtivoDTO save(AtivoDTO ativoDTO) {
        log.debug("Request to save Ativo : {}", ativoDTO);
        Ativo ativo = ativoMapper.toEntity(ativoDTO);
        ativo = ativoRepository.save(ativo);
        return ativoMapper.toDto(ativo);
    }

    /**
     * Get all the ativos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AtivoDTO> findAll() {
        log.debug("Request to get all Ativos");
        return ativoRepository.findAll().stream()
            .map(ativoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one ativo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AtivoDTO> findOne(Long id) {
        log.debug("Request to get Ativo : {}", id);
        return ativoRepository.findById(id)
            .map(ativoMapper::toDto);
    }

    /**
     * Delete the ativo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ativo : {}", id);
        ativoRepository.deleteById(id);
    }
}
