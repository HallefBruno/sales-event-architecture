package com.sud.sales.api.controller;

import com.sud.sales.api.model.dto.OrderRequest;
import com.sud.sales.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderService salesService;

    @PostMapping
    public ResponseEntity<String> postOrder(@RequestBody OrderRequest order) {
        salesService.placeOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido recebido!");
    }
}