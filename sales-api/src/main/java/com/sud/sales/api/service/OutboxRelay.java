package com.sud.sales.api.service;

import com.sud.sales.api.config.RabbitMQConfig;
import com.sud.sales.api.model.Outbox;
import com.sud.sales.api.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutboxRelay {
	
	private static final Logger log = LoggerFactory.getLogger(OutboxRelay.class);

    private final OutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;

	public OutboxRelay(OutboxRepository outboxRepository, RabbitTemplate rabbitTemplate) {
		this.outboxRepository = outboxRepository;
		this.rabbitTemplate = rabbitTemplate;
	}

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processOutbox() {
        List<Outbox> messages = outboxRepository.findByProcessedFalse();
        for (Outbox message : messages) {
            try {
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message.getPayload());
                message.setProcessed(true);
                outboxRepository.save(message);
                log.info("Evento enviado com sucesso: {}", message.getAggregateId());
            } catch (Exception e) {
                log.error("Falha ao enviar evento da Outbox: {}", message.getId(), e);
            }
        }
    }
}