package com.dbc.vakinhaemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VakinhaEmailApp {

	public static void main(String[] args) {
		SpringApplication.run(VakinhaEmailApp.class, args);
	}
}
