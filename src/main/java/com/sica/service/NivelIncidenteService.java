package com.sica.service;

import com.sica.domain.NivelIncidente;
import com.sica.repository.NivelIncidenteRepository;
import com.sica.service.dto.NivelIncidenteDTO;
import com.sica.service.mapper.NivelIncidenteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link NivelIncidente}.
 */
@Service
@Transactional
public class NivelIncidenteService {

    private final Logger log = LoggerFactory.getLogger(NivelIncidenteService.class);

    private final NivelIncidenteRepository nivelIncidenteRepository;

    private final NivelIncidenteMapper nivelIncidenteMapper;

    public NivelIncidenteService(NivelIncidenteRepository nivelIncidenteRepository, NivelIncidenteMapper nivelIncidenteMapper) {
        this.nivelIncidenteRepository = nivelIncidenteRepository;
        this.nivelIncidenteMapper = nivelIncidenteMapper;
    }

    /**
     * Save a nivelIncidente.
     *
     * @param nivelIncidenteDTO the entity to save.
     * @return the persisted entity.
     */
    public NivelIncidenteDTO save(NivelIncidenteDTO nivelIncidenteDTO) {
        log.debug("Request to save NivelIncidente : {}", nivelIncidenteDTO);
        NivelIncidente nivelIncidente = nivelIncidenteMapper.toEntity(nivelIncidenteDTO);
        nivelIncidente = nivelIncidenteRepository.save(nivelIncidente);
        return nivelIncidenteMapper.toDto(nivelIncidente);
    }

    /**
     * Get all the nivelIncidentes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NivelIncidenteDTO> findAll() {
        log.debug("Request to get all NivelIncidentes");
        return nivelIncidenteRepository.findAll().stream()
            .map(nivelIncidenteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one nivelIncidente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NivelIncidenteDTO> findOne(Long id) {
        log.debug("Request to get NivelIncidente : {}", id);
        return nivelIncidenteRepository.findById(id)
            .map(nivelIncidenteMapper::toDto);
    }

    /**
     * Delete the nivelIncidente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NivelIncidente : {}", id);
        nivelIncidenteRepository.deleteById(id);
    }
}
