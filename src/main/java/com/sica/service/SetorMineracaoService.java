package com.sica.service;

import com.sica.domain.SetorMineracao;
import com.sica.repository.SetorMineracaoRepository;
import com.sica.service.dto.SetorMineracaoDTO;
import com.sica.service.mapper.SetorMineracaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SetorMineracao}.
 */
@Service
@Transactional
public class SetorMineracaoService {

    private final Logger log = LoggerFactory.getLogger(SetorMineracaoService.class);

    private final SetorMineracaoRepository setorMineracaoRepository;

    private final SetorMineracaoMapper setorMineracaoMapper;

    public SetorMineracaoService(SetorMineracaoRepository setorMineracaoRepository, SetorMineracaoMapper setorMineracaoMapper) {
        this.setorMineracaoRepository = setorMineracaoRepository;
        this.setorMineracaoMapper = setorMineracaoMapper;
    }

    /**
     * Save a setorMineracao.
     *
     * @param setorMineracaoDTO the entity to save.
     * @return the persisted entity.
     */
    public SetorMineracaoDTO save(SetorMineracaoDTO setorMineracaoDTO) {
        log.debug("Request to save SetorMineracao : {}", setorMineracaoDTO);
        SetorMineracao setorMineracao = setorMineracaoMapper.toEntity(setorMineracaoDTO);
        setorMineracao = setorMineracaoRepository.save(setorMineracao);
        return setorMineracaoMapper.toDto(setorMineracao);
    }

    /**
     * Get all the setorMineracaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SetorMineracaoDTO> findAll() {
        log.debug("Request to get all SetorMineracaos");
        return setorMineracaoRepository.findAll().stream()
            .map(setorMineracaoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one setorMineracao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SetorMineracaoDTO> findOne(Long id) {
        log.debug("Request to get SetorMineracao : {}", id);
        return setorMineracaoRepository.findById(id)
            .map(setorMineracaoMapper::toDto);
    }

    /**
     * Delete the setorMineracao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SetorMineracao : {}", id);
        setorMineracaoRepository.deleteById(id);
    }
}
