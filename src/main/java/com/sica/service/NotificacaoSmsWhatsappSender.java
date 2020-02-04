package com.sica.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.sica.service.dto.NotificacaoSmsWhatsappDTO;

public class NotificacaoSmsWhatsappSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private Queue notificacaoSmsWhatsappQueue;

	@Scheduled(fixedDelay = 1000 * 60 * 60, initialDelay = 500)
	public void send() {
		String message = "Hello World!";
		this.rabbitTemplate.convertAndSend(notificacaoSmsWhatsappQueue.getName(),
				new NotificacaoSmsWhatsappDTO("5531994815726", "CONTEÚDO DA MENSAGEM"));
		System.out.println(" [x] Sent '" + message + "'");
	}

}