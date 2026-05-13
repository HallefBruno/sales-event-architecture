package com.sud.messenger.worker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sud.messenger.worker.model.ProcessedEmail;
import com.sud.messenger.worker.model.dto.OrderCreatedEvent;
import com.sud.messenger.worker.repository.ProcessedEmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class OrderNotificationListener {
	
	private static final Logger log = LoggerFactory.getLogger(OrderNotificationListener.class);
	
    private final ProcessedEmailRepository processedEmailRepository;
    private final JavaMailSender mailSender; 
    private final ObjectMapper objectMapper;

	public OrderNotificationListener(
			ProcessedEmailRepository processedEmailRepository, 
			JavaMailSender mailSender, 
			ObjectMapper objectMapper) {
		this.processedEmailRepository = processedEmailRepository;
		this.mailSender = mailSender;
		this.objectMapper = objectMapper;
	}
	
    @RabbitListener(queues = "sales.v1.send-email")
    public void onOrderCreated(String messagePayload) throws JsonProcessingException {
		
		OrderCreatedEvent event = objectMapper.readValue(messagePayload, OrderCreatedEvent.class);
		
        try {

			log.info("Recebendo pedido para notificação: {}", event.id());

			if (processedEmailRepository.existsById(event.id())) {
				log.warn("E-mail para o pedido {} já foi processado. Ignorando...", event.id());
				return;
			}

            sendRealEmail(event.customerEmail(), event.id());

            processedEmailRepository.save(new ProcessedEmail(event.id()));
            log.info("E-mail enviado com sucesso para o pedido: {}", event.id());

        } catch (Exception e) {
            log.error("Falha ao processar e-mail do pedido: {}. Iniciando tentativa de retry...", event.id());
            throw e; // Lançar a exceção ativa o Retry configurado no YML
        }
    }

    private void sendRealEmail(String email, Long orderId) {
        log.info("Enviando e-mail para {} referente ao pedido {}", email, orderId);
    }
}
