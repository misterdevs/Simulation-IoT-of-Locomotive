package com.mrdevs.sil_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SilServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(SilServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SilServiceApplication.class, args);
		logger.info("Application started");
	}

}
