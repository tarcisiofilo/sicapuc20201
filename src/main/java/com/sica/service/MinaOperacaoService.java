package com.sica.service;

import com.sica.domain.MinaOperacao;
import com.sica.repository.MinaOperacaoRepository;
import com.sica.service.dto.MinaOperacaoDTO;
import com.sica.service.mapper.MinaOperacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MinaOperacao}.
 */
@Service
@Transactional
public class MinaOperacaoService {

    private final Logger log = LoggerFactory.getLogger(MinaOperacaoService.class);

    private final MinaOperacaoRepository minaOperacaoRepository;

    private final MinaOperacaoMapper minaOperacaoMapper;

    public MinaOperacaoService(MinaOperacaoRepository minaOperacaoRepository, MinaOperacaoMapper minaOperacaoMapper) {
        this.minaOperacaoRepository = minaOperacaoRepository;
        this.minaOperacaoMapper = minaOperacaoMapper;
    }

    /**
     * Save a minaOperacao.
     *
     * @param minaOperacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public MinaOperacaoDTO save(MinaOperacaoDTO minaOperacaoDTO) {
        log.debug("Request to save MinaOperacao : {}", minaOperacaoDTO);
        MinaOperacao minaOperacao = minaOperacaoMapper.toEntity(minaOperacaoDTO);
        minaOperacao = minaOperacaoRepository.save(minaOperacao);
        return minaOperacaoMapper.toDto(minaOperacao);
    }

    /**
     * Get all the minaOperacaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MinaOperacaoDTO> findAll() {
        log.debug("Request to get all MinaOperacaos");
        return minaOperacaoRepository.findAll().stream()
            .map(minaOperacaoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one minaOperacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MinaOperacaoDTO> findOne(Long id) {
        log.debug("Request to get MinaOperacao : {}", id);
        return minaOperacaoRepository.findById(id)
            .map(minaOperacaoMapper::toDto);
    }

    /**
     * Delete the minaOperacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MinaOperacao : {}", id);
        minaOperacaoRepository.deleteById(id);
    }
}
