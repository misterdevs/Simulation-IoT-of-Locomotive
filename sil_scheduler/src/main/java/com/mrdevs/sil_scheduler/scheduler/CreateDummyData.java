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

    // topic name for locomotive data
    private String topics = "locomotive-data-test-topic";
    // static data for randomize data
    private Integer[] status = { 1, 2, 3 }; // 1: On Duty, 2: On Depot, 3: Under Maintenance
    private String[] type = { "Executive", "Bussiness", "Economy" };

    @Scheduled(fixedDelay = 10000)
    public void scheduler() throws JsonProcessingException {
        Random randomize = new Random();

        // create random code
        String code = "LOCO-" + UUID.randomUUID().toString().substring(0, 3).toUpperCase();
        // create random name based on code + type list
        String name = code + " " + type[randomize.nextInt(type.length)];

        // generate dummy data
        Locomotive locomotive = Locomotive.builder().code(code).name(name)
                .dimension("2000 x 15000").statusId(status[randomize.nextInt(status.length)]).build();

        ObjectMapper objectMapper = new ObjectMapper();
        // convert object class to json string
        String message = objectMapper.writeValueAsString(locomotive);

        // sending dummy data to kafka which later will be stored to postgresql and to
        // consume for another service
        kafkaProducer.sendMessage(topics, message);

        // System.out.println("Sending message : " + message);
        // logging event
        logger.info("Dummy data has been created : " + message);
    }

}
