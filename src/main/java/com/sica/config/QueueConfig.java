package com.sica.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sica.service.NotificacaoSmsWhatsappSender;

@Configuration
public class QueueConfig {

//	@Bean
//	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
//		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//		return rabbitTemplate;
//	}

	@Bean
	public Queue notificacaoSmsWhatsappQueue() {
		return new Queue("notificacaoSmsWhatsappQueue");
	}

	@Bean
	public NotificacaoSmsWhatsappSender sender() {
		return new NotificacaoSmsWhatsappSender();
	}
}