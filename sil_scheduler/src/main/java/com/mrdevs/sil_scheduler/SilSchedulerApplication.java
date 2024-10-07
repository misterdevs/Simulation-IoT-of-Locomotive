package com.mrdevs.sil_scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SilSchedulerApplication {
	private static final Logger logger = LoggerFactory.getLogger(SilSchedulerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SilSchedulerApplication.class, args);
		logger.info("Application started");

	}

}
