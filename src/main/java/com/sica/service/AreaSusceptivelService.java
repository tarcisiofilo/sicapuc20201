package com.sica.service;

import com.sica.domain.AreaSusceptivel;
import com.sica.repository.AreaSusceptivelRepository;
import com.sica.service.dto.AreaSusceptivelDTO;
import com.sica.service.mapper.AreaSusceptivelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AreaSusceptivel}.
 */
@Service
@Transactional
public class AreaSusceptivelService {

    private final Logger log = LoggerFactory.getLogger(AreaSusceptivelService.class);

    private final AreaSusceptivelRepository areaSusceptivelRepository;

    private final AreaSusceptivelMapper areaSusceptivelMapper;

    public AreaSusceptivelService(AreaSusceptivelRepository areaSusceptivelRepository, AreaSusceptivelMapper areaSusceptivelMapper) {
        this.areaSusceptivelRepository = areaSusceptivelRepository;
        this.areaSusceptivelMapper = areaSusceptivelMapper;
    }

    /**
     * Save a areaSusceptivel.
     *
     * @param areaSusceptivelDTO the entity to save.
     * @return the persisted entity.
     */
    public AreaSusceptivelDTO save(AreaSusceptivelDTO areaSusceptivelDTO) {
        log.debug("Request to save AreaSusceptivel : {}", areaSusceptivelDTO);
        AreaSusceptivel areaSusceptivel = areaSusceptivelMapper.toEntity(areaSusceptivelDTO);
        areaSusceptivel = areaSusceptivelRepository.save(areaSusceptivel);
        return areaSusceptivelMapper.toDto(areaSusceptivel);
    }

    /**
     * Get all the areaSusceptivels.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AreaSusceptivelDTO> findAll() {
        log.debug("Request to get all AreaSusceptivels");
        return areaSusceptivelRepository.findAll().stream()
            .map(areaSusceptivelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one areaSusceptivel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AreaSusceptivelDTO> findOne(Long id) {
        log.debug("Request to get AreaSusceptivel : {}", id);
        return areaSusceptivelRepository.findById(id)
            .map(areaSusceptivelMapper::toDto);
    }

    /**
     * Delete the areaSusceptivel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AreaSusceptivel : {}", id);
        areaSusceptivelRepository.deleteById(id);
    }
}
