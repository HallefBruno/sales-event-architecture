package com.sud.sales.api.service;

import com.sud.sales.api.model.dto.OrderCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sud.sales.api.model.Order;
import com.sud.sales.api.model.Outbox;
import com.sud.sales.api.model.dto.OrderRequest;
import com.sud.sales.api.repository.OrderRepository;
import com.sud.sales.api.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

	public OrderService(OrderRepository orderRepository, OutboxRepository outboxRepository, ObjectMapper objectMapper) {
		this.orderRepository = orderRepository;
		this.outboxRepository = outboxRepository;
		this.objectMapper = objectMapper;
	}
	
    @Transactional
    public void placeOrder(OrderRequest request) {
		
        Order order = new Order();
        order.setCustomerEmail(request.email());
        order.setTotal(request.total());
        order.setStatus("CREATED");
        
        orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), order.getCustomerEmail());

        try {
            String jsonPayload = objectMapper.writeValueAsString(event);
            
            Outbox outbox = new Outbox();
            outbox.setAggregateId(order.getId().toString());
            outbox.setType("OrderCreated");
            outbox.setPayload(jsonPayload);
            
            outboxRepository.save(outbox);
            
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter evento para JSON", e);
        }
    }
	
}
