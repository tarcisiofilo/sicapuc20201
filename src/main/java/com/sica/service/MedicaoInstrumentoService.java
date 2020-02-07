package com.sica.service;

import com.sica.domain.MedicaoInstrumento;
import com.sica.repository.MedicaoInstrumentoRepository;
import com.sica.service.dto.MedicaoInstrumentoDTO;
import com.sica.service.mapper.MedicaoInstrumentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link MedicaoInstrumento}.
 */
@Service
@Transactional
public class MedicaoInstrumentoService {

    private final Logger log = LoggerFactory.getLogger(MedicaoInstrumentoService.class);

    private final MedicaoInstrumentoRepository medicaoInstrumentoRepository;

    private final MedicaoInstrumentoMapper medicaoInstrumentoMapper;

    public MedicaoInstrumentoService(MedicaoInstrumentoRepository medicaoInstrumentoRepository, MedicaoInstrumentoMapper medicaoInstrumentoMapper) {
        this.medicaoInstrumentoRepository = medicaoInstrumentoRepository;
        this.medicaoInstrumentoMapper = medicaoInstrumentoMapper;
    }

    /**
     * Save a medicaoInstrumento.
     *
     * @param medicaoInstrumentoDTO the entity to save.
     * @return the persisted entity.
     */
    public MedicaoInstrumentoDTO save(MedicaoInstrumentoDTO medicaoInstrumentoDTO) {
        log.debug("Request to save MedicaoInstrumento : {}", medicaoInstrumentoDTO);
        MedicaoInstrumento medicaoInstrumento = medicaoInstrumentoMapper.toEntity(medicaoInstrumentoDTO);
        medicaoInstrumento = medicaoInstrumentoRepository.save(medicaoInstrumento);
        return medicaoInstrumentoMapper.toDto(medicaoInstrumento);
    }

    /**
     * Get all the medicaoInstrumentos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MedicaoInstrumentoDTO> findAll() {
        log.debug("Request to get all MedicaoInstrumentos");
        return medicaoInstrumentoRepository.findAll().stream()
            .map(medicaoInstrumentoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the medicaoInstrumentos where Incidente is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<MedicaoInstrumentoDTO> findAllWhereIncidenteIsNull() {
        log.debug("Request to get all medicaoInstrumentos where Incidente is null");
        return StreamSupport
            .stream(medicaoInstrumentoRepository.findAll().spliterator(), false)
            .filter(medicaoInstrumento -> medicaoInstrumento.getIncidente() == null)
            .map(medicaoInstrumentoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one medicaoInstrumento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MedicaoInstrumentoDTO> findOne(Long id) {
        log.debug("Request to get MedicaoInstrumento : {}", id);
        return medicaoInstrumentoRepository.findById(id)
            .map(medicaoInstrumentoMapper::toDto);
    }

    /**
     * Delete the medicaoInstrumento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MedicaoInstrumento : {}", id);
        medicaoInstrumentoRepository.deleteById(id);
    }
}
