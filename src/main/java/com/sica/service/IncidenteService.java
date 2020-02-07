package com.sica.service;

import com.sica.domain.Incidente;
import com.sica.repository.IncidenteRepository;
import com.sica.service.dto.IncidenteDTO;
import com.sica.service.mapper.IncidenteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Incidente}.
 */
@Service
@Transactional
public class IncidenteService {

	private final Logger log = LoggerFactory.getLogger(IncidenteService.class);

	private final IncidenteRepository incidenteRepository;

	private final IncidenteMapper incidenteMapper;

	private final NotificacaoService notificacaoService;

	public IncidenteService(IncidenteRepository incidenteRepository, IncidenteMapper incidenteMapper,
			NotificacaoService notificacaoService) {
		this.incidenteRepository = incidenteRepository;
		this.incidenteMapper = incidenteMapper;
		this.notificacaoService = notificacaoService;
	}

	/**
	 * Save a incidente.
	 *
	 * @param incidenteDTO the entity to save.
	 * @return the persisted entity.
	 */
	public IncidenteDTO save(IncidenteDTO incidenteDTO) {
		log.debug("Request to save Incidente : {}", incidenteDTO);
		Incidente incidente = incidenteMapper.toEntity(incidenteDTO);
		incidente = incidenteRepository.save(incidente);
		notificacaoService.notificarFamiliasAreasSusceptiveis(incidente);
		notificacaoService.notificarFuncionarios(incidente);
		return incidenteMapper.toDto(incidente);
	}

	/**
	 * Get all the incidentes.
	 *
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public List<IncidenteDTO> findAll() {
		log.debug("Request to get all Incidentes");
		return incidenteRepository.findAllWithEagerRelationships().stream().map(incidenteMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get all the incidentes with eager load of many-to-many relationships.
	 *
	 * @return the list of entities.
	 */
	public Page<IncidenteDTO> findAllWithEagerRelationships(Pageable pageable) {
		return incidenteRepository.findAllWithEagerRelationships(pageable).map(incidenteMapper::toDto);
	}

	/**
	 * Get one incidente by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<IncidenteDTO> findOne(Long id) {
		log.debug("Request to get Incidente : {}", id);
		return incidenteRepository.findOneWithEagerRelationships(id).map(incidenteMapper::toDto);
	}

	/**
	 * Delete the incidente by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Incidente : {}", id);
		incidenteRepository.deleteById(id);
	}
}
