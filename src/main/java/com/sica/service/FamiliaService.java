package com.sica.service;

import com.sica.domain.Familia;
import com.sica.repository.FamiliaRepository;
import com.sica.service.dto.FamiliaDTO;
import com.sica.service.mapper.FamiliaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Familia}.
 */
@Service
@Transactional
public class FamiliaService {

    private final Logger log = LoggerFactory.getLogger(FamiliaService.class);

    private final FamiliaRepository familiaRepository;

    private final FamiliaMapper familiaMapper;

    public FamiliaService(FamiliaRepository familiaRepository, FamiliaMapper familiaMapper) {
        this.familiaRepository = familiaRepository;
        this.familiaMapper = familiaMapper;
    }

    /**
     * Save a familia.
     *
     * @param familiaDTO the entity to save.
     * @return the persisted entity.
     */
    public FamiliaDTO save(FamiliaDTO familiaDTO) {
        log.debug("Request to save Familia : {}", familiaDTO);
        Familia familia = familiaMapper.toEntity(familiaDTO);
        familia = familiaRepository.save(familia);
        return familiaMapper.toDto(familia);
    }

    /**
     * Get all the familias.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FamiliaDTO> findAll() {
        log.debug("Request to get all Familias");
        return familiaRepository.findAll().stream()
            .map(familiaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one familia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FamiliaDTO> findOne(Long id) {
        log.debug("Request to get Familia : {}", id);
        return familiaRepository.findById(id)
            .map(familiaMapper::toDto);
    }

    /**
     * Delete the familia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Familia : {}", id);
        familiaRepository.deleteById(id);
    }
}
