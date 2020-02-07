package com.sica.service;

import com.sica.domain.Sirene;
import com.sica.repository.SireneRepository;
import com.sica.service.dto.SireneDTO;
import com.sica.service.mapper.SireneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Sirene}.
 */
@Service
@Transactional
public class SireneService {

    private final Logger log = LoggerFactory.getLogger(SireneService.class);

    private final SireneRepository sireneRepository;

    private final SireneMapper sireneMapper;

    public SireneService(SireneRepository sireneRepository, SireneMapper sireneMapper) {
        this.sireneRepository = sireneRepository;
        this.sireneMapper = sireneMapper;
    }

    /**
     * Save a sirene.
     *
     * @param sireneDTO the entity to save.
     * @return the persisted entity.
     */
    public SireneDTO save(SireneDTO sireneDTO) {
        log.debug("Request to save Sirene : {}", sireneDTO);
        Sirene sirene = sireneMapper.toEntity(sireneDTO);
        sirene = sireneRepository.save(sirene);
        return sireneMapper.toDto(sirene);
    }

    /**
     * Get all the sirenes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SireneDTO> findAll() {
        log.debug("Request to get all Sirenes");
        return sireneRepository.findAll().stream()
            .map(sireneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sirene by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SireneDTO> findOne(Long id) {
        log.debug("Request to get Sirene : {}", id);
        return sireneRepository.findById(id)
            .map(sireneMapper::toDto);
    }

    /**
     * Delete the sirene by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sirene : {}", id);
        sireneRepository.deleteById(id);
    }
}
