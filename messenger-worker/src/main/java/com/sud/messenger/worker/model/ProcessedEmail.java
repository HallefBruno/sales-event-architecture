package com.sud.messenger.worker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "processed_emails")
public class ProcessedEmail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId; 

	private LocalDateTime processedAt = LocalDateTime.now();

	public ProcessedEmail() {
	}

	public ProcessedEmail(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getProcessedAt() {
		return processedAt;
	}

	public void setProcessedAt(LocalDateTime processedAt) {
		this.processedAt = processedAt;
	}
	
}
