package com.mrdevs.sil_service.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.mrdevs.sil_service.dto.ReceiveLocomotive;
import com.mrdevs.sil_service.dto.SendSummary;
import com.mrdevs.sil_service.service.locomotive.LocomotiveService;
import com.mrdevs.sil_service.service.locomotive.SummaryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SummaryService summaryService;
    private final LocomotiveService locomotiveService;

    // for testing only
    @MessageMapping("/sendMessage")
    public void sendMessage(String message) throws Exception {
        messagingTemplate.convertAndSend("/topic/messages",
                message);
    }

    // function for sending data to subscriber /topic/message
    public void sendLatestLocomotiveSummaryData(String message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }

    // function for sending data to subscriber /topic/message/locomotive
    public void sendLatestLocomotiveData(String message) {
        messagingTemplate.convertAndSend("/topic/messages/locomotive", message);
    }

    // handle new subscriber /topic/message
    @MessageMapping("/subscribe")
    @SendTo("/topic/messages")
    public void handleSubscription() {

        // get 24h summaries as initial data
        List<SendSummary> summaries = summaryService.get24hSummaries();

        // send it to subscriber with topic /topic/message
        for (SendSummary summary : summaries) {
            messagingTemplate.convertAndSend("/topic/messages", summary);
        }
    }

    // handle new subscriber /topic/message/locomotive
    @MessageMapping("/subscribe/locomotive")
    @SendTo("/topic/messages/locomotive")
    public void handleSubscriptionLocomotive() {

        // get 24h locomotives as initial data
        List<ReceiveLocomotive> locomotives = locomotiveService.get24hLocomotives();

        // send it to subscriber with topic /topic/message/locomotive
        for (ReceiveLocomotive locomotive : locomotives) {
            messagingTemplate.convertAndSend("/topic/messages/locomotive", locomotive);
        }
    }

}
