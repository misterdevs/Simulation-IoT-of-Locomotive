package com.mrdevs.sil_scheduler.scheduler;

import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrdevs.sil_scheduler.model.Locomotive;
import com.mrdevs.sil_scheduler.service.kafka.KafkaProducer;

@Component
public class CreateDummyData {
    private static final Logger logger = LoggerFactory.getLogger(CreateDummyData.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    private String topics = "locomotive-data-test-topic";
    private Integer[] status = { 1, 2, 3 };
    private String[] type = { "Executive", "Bussiness", "Economy" };

    @Scheduled(fixedDelay = 5000)
    public void scheduler() throws JsonProcessingException {
        Random randomize = new Random();

        String code = "LOCO-" + UUID.randomUUID().toString().substring(0, 3).toUpperCase();
        String name = code + " " + type[randomize.nextInt(type.length)];

        Locomotive locomotive = Locomotive.builder().code(code).name(name)
                .dimension("2000 x 15000").statusId(status[randomize.nextInt(status.length)]).build();

        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(locomotive);

        kafkaProducer.sendMessage(topics, message);

        // System.out.println("Sending message : " + message);
        logger.info("Dummy data has been created : " + message);
    }

}
