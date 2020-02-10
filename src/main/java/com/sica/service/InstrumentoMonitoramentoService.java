package com.sica.service;

import com.sica.domain.InstrumentoMonitoramento;
import com.sica.repository.InstrumentoMonitoramentoRepository;
import com.sica.service.dto.InstrumentoMonitoramentoDTO;
import com.sica.service.dto.NotificacaoEmailDTO;
import com.sica.service.mapper.InstrumentoMonitoramentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link InstrumentoMonitoramento}.
 */
@Service
@Transactional
public class InstrumentoMonitoramentoService {

	private final Logger log = LoggerFactory.getLogger(InstrumentoMonitoramentoService.class);

	private final InstrumentoMonitoramentoRepository instrumentoMonitoramentoRepository;

	private final InstrumentoMonitoramentoMapper instrumentoMonitoramentoMapper;

	private final RabbitTemplate rabbitTemplate;

	private final Queue inclusaoInstrumentoMonitoramentoQueue;

	public InstrumentoMonitoramentoService(InstrumentoMonitoramentoRepository instrumentoMonitoramentoRepository,
			InstrumentoMonitoramentoMapper instrumentoMonitoramentoMapper, RabbitTemplate rabbitTemplate,
			Queue inclusaoInstrumentoMonitoramentoQueue) {
		this.instrumentoMonitoramentoRepository = instrumentoMonitoramentoRepository;
		this.instrumentoMonitoramentoMapper = instrumentoMonitoramentoMapper;
		this.rabbitTemplate = rabbitTemplate;
		this.inclusaoInstrumentoMonitoramentoQueue = inclusaoInstrumentoMonitoramentoQueue;

	}

	/**
	 * Save a instrumentoMonitoramento.
	 *
	 * @param instrumentoMonitoramentoDTO the entity to save.
	 * @return the persisted entity.
	 */
	public InstrumentoMonitoramentoDTO save(InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO) {
		log.debug("Request to save InstrumentoMonitoramento : {}", instrumentoMonitoramentoDTO);
		InstrumentoMonitoramento instrumentoMonitoramento = instrumentoMonitoramentoMapper
				.toEntity(instrumentoMonitoramentoDTO);
		instrumentoMonitoramento = instrumentoMonitoramentoRepository.save(instrumentoMonitoramento);
		this.rabbitTemplate.convertAndSend(inclusaoInstrumentoMonitoramentoQueue.getName(),
				instrumentoMonitoramentoDTO);

		return instrumentoMonitoramentoMapper.toDto(instrumentoMonitoramento);
	}

	/**
	 * Get all the instrumentoMonitoramentos.
	 *
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public List<InstrumentoMonitoramentoDTO> findAll() {
		log.debug("Request to get all InstrumentoMonitoramentos");
		return instrumentoMonitoramentoRepository.findAll().stream().map(instrumentoMonitoramentoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get all the instrumentoMonitoramentos where Vistoria is {@code null}.
	 * 
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public List<InstrumentoMonitoramentoDTO> findAllWhereVistoriaIsNull() {
		log.debug("Request to get all instrumentoMonitoramentos where Vistoria is null");
		return StreamSupport.stream(instrumentoMonitoramentoRepository.findAll().spliterator(), false)
				.filter(instrumentoMonitoramento -> instrumentoMonitoramento.getVistoria() == null)
				.map(instrumentoMonitoramentoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one instrumentoMonitoramento by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<InstrumentoMonitoramentoDTO> findOne(Long id) {
		log.debug("Request to get InstrumentoMonitoramento : {}", id);
		return instrumentoMonitoramentoRepository.findById(id).map(instrumentoMonitoramentoMapper::toDto);
	}

	/**
	 * Delete the instrumentoMonitoramento by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete InstrumentoMonitoramento : {}", id);
		instrumentoMonitoramentoRepository.deleteById(id);
	}
}
