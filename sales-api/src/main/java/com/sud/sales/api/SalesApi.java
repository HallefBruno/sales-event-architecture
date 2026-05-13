package com.sud.sales.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SalesApi {

	public static void main(String[] args) {
		SpringApplication.run(SalesApi.class, args);
	}

}