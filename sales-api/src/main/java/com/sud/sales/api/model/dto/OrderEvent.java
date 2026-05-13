package com.sud.sales.api.model.dto;

import java.math.BigDecimal;

public record OrderEvent(
    Long orderId,
    String customerEmail,
    String customerName,
    BigDecimal amount
) {}