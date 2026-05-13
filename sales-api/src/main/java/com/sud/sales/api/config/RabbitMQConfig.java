package com.sud.sales.api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "sales.v1.events";
    public static final String QUEUE_EMAIL = "sales.v1.send-email";
    public static final String ROUTING_KEY = "order-created";
	
	@Bean
	public TopicExchange deadLetterExchange() {
		return new TopicExchange(EXCHANGE_NAME + ".dlx");
	}
	
	@Bean
	public Queue deadLetterQueue() {
		return new Queue(QUEUE_EMAIL + ".dlq");
	}
	
	@Bean
	public Binding deadLetterBinding() {
		return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(ROUTING_KEY);
	}
	
    @Bean
    public TopicExchange salesExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
	public Queue emailQueue() {
		return QueueBuilder.durable(QUEUE_EMAIL)
			.withArgument("x-dead-letter-exchange", EXCHANGE_NAME + ".dlx")
			.withArgument("x-dead-letter-routing-key", ROUTING_KEY)
			.build();
	}

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(emailQueue()).to(salesExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
