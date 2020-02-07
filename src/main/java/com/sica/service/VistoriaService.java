package com.sica.service;

import com.sica.domain.Vistoria;
import com.sica.repository.VistoriaRepository;
import com.sica.service.dto.VistoriaDTO;
import com.sica.service.mapper.VistoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Vistoria}.
 */
@Service
@Transactional
public class VistoriaService {

    private final Logger log = LoggerFactory.getLogger(VistoriaService.class);

    private final VistoriaRepository vistoriaRepository;

    private final VistoriaMapper vistoriaMapper;

    public VistoriaService(VistoriaRepository vistoriaRepository, VistoriaMapper vistoriaMapper) {
        this.vistoriaRepository = vistoriaRepository;
        this.vistoriaMapper = vistoriaMapper;
    }

    /**
     * Save a vistoria.
     *
     * @param vistoriaDTO the entity to save.
     * @return the persisted entity.
     */
    public VistoriaDTO save(VistoriaDTO vistoriaDTO) {
        log.debug("Request to save Vistoria : {}", vistoriaDTO);
        Vistoria vistoria = vistoriaMapper.toEntity(vistoriaDTO);
        vistoria = vistoriaRepository.save(vistoria);
        return vistoriaMapper.toDto(vistoria);
    }

    /**
     * Get all the vistorias.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VistoriaDTO> findAll() {
        log.debug("Request to get all Vistorias");
        return vistoriaRepository.findAll().stream()
            .map(vistoriaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vistoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VistoriaDTO> findOne(Long id) {
        log.debug("Request to get Vistoria : {}", id);
        return vistoriaRepository.findById(id)
            .map(vistoriaMapper::toDto);
    }

    /**
     * Delete the vistoria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vistoria : {}", id);
        vistoriaRepository.deleteById(id);
    }
}
