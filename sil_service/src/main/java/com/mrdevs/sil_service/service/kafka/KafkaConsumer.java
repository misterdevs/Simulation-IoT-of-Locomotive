package com.mrdevs.sil_service.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mrdevs.sil_service.controller.WebSocketController;
import com.mrdevs.sil_service.service.locomotive.SummaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final SummaryService summaryService;
    private final WebSocketController webSocketController;

    @KafkaListener(topics = "locomotive-data-test-topic", groupId = "service")
    public void listen(String message) throws JsonProcessingException {
        // System.out.println("Received Message: " + message);
        summaryService.CreateSummary(message);
        webSocketController.sendLatestLocomotiveData(message);
    }

    @KafkaListener(topics = "locomotive-summary-data-test-topic", groupId = "service")
    public void listenSummary(String message) throws JsonProcessingException {
        // System.out.println("Received Message: " + message);
        // summaryService.get24hSummaries(message);

        // sending latest summary data to subscriber /topic/message
        webSocketController.sendLatestLocomotiveSummaryData(message);

    }

}
