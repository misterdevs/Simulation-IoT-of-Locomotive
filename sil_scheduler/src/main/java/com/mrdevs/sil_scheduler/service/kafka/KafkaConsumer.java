package com.mrdevs.sil_scheduler.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "application-logs-topic", groupId = "dummyData_scheduler")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }

}
