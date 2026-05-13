package com.sud.sales.api.model.dto;

import java.math.BigDecimal;

public record OrderRequest(String email, BigDecimal total) {
	
}
