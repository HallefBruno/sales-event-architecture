package com.sud.messenger.worker.model.dto;

public record OrderCreatedEvent(
    Long id,
    String customerEmail
) {}