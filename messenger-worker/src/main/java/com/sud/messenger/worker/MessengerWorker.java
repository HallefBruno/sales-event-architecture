
package com.sud.messenger.worker;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class MessengerWorker {

	public static void main(String[] args) {
		SpringApplication.run(MessengerWorker.class, args);
	}

}