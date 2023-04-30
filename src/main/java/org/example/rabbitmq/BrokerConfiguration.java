package org.example.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfiguration {
	@Bean
	public Queue queue1() {
		return new Queue("news", true);
	}
}
