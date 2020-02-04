package com.sica.config;

import static java.lang.System.getenv;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.sica.service.NotificacaoSmsWhatsappSender;

@Configuration
public class QueueConfig {

	@Profile("prod")
	@Bean
	public ConnectionFactory connectionFactory() {
		final URI rabbitMqUrl;
		try {
			rabbitMqUrl = new URI(getEnvOrThrow("CLOUDAMQP_URL"));
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		final CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setUsername(rabbitMqUrl.getUserInfo().split(":")[0]);
		factory.setPassword(rabbitMqUrl.getUserInfo().split(":")[1]);
		factory.setHost(rabbitMqUrl.getHost());
		factory.setPort(rabbitMqUrl.getPort());
		factory.setVirtualHost(rabbitMqUrl.getPath().substring(1));
		return factory;
	}

	@Bean
	public Queue notificacaoSmsWhatsappQueue() {
		return new Queue("notificacaoSmsWhatsappQueue");
	}

	@Bean
	public NotificacaoSmsWhatsappSender sender() {
		return new NotificacaoSmsWhatsappSender();
	}

	@Profile("prod")
	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Profile("prod")
	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		return template;
	}

	private static String getEnvOrThrow(String name) {
		final String env = getenv(name);
		if (env == null) {
			throw new IllegalStateException("Environment variable [" + name + "] is not set.");
		}
		return env;
	}
}