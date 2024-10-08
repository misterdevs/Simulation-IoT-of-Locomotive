package com.mrdevs.sil_service.service.locomotive;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrdevs.sil_service.dto.SendSummary;
import com.mrdevs.sil_service.dto.SendSummaryStatusTotal;
import com.mrdevs.sil_service.model.mongodb.StatusDetail;
import com.mrdevs.sil_service.model.mongodb.Summary;
import com.mrdevs.sil_service.model.postgresql.Status;
import com.mrdevs.sil_service.repository.mongodb.StatusDetailRepository;
import com.mrdevs.sil_service.repository.mongodb.SummaryRepository;
import com.mrdevs.sil_service.repository.postgresql.LocomotiveRepository;
import com.mrdevs.sil_service.repository.postgresql.StatusRepository;
import com.mrdevs.sil_service.service.kafka.KafkaProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SummaryService {
    private static final Logger logger = LoggerFactory.getLogger(SummaryService.class);

    // topic name for locomotive summary data
    private final String topic = "locomotive-summary-data-test-topic"; // need to change after all services running well

    private final LocomotiveRepository locomotiveRepository;
    private final StatusRepository statusRepository;
    private final SummaryRepository summaryRepository;
    private final StatusDetailRepository statusDetailRepository;
    private final KafkaProducer kafkaProducer;

    public void CreateSummary(String data) throws JsonProcessingException {

        try {
            SendSummary sendSummary = new SendSummary();
            List<SendSummaryStatusTotal> total = new ArrayList<SendSummaryStatusTotal>();
            List<Status> statuses = statusRepository.findAll();

            // get total of each status
            for (int i = 0; i < statuses.size(); i++) {
                // get status id
                Long id = Long.valueOf(statuses.get(i).getId());
                // get total by status id
                Long locomotiveTotal = locomotiveRepository.countByStatusId(id);

                // adding total to total list
                total.add(new SendSummaryStatusTotal(statuses.get(i).getId(), locomotiveTotal.intValue()));
            }

            Long totalLocomotive = locomotiveRepository.count();

            // set total locomotive to summary
            sendSummary.setTotalLocomotive(totalLocomotive.intValue());
            // set total to summary
            sendSummary.setStatus(total);

            ObjectMapper objectMapper = new ObjectMapper();
            // convert object class to json string
            String message = objectMapper.writeValueAsString(sendSummary);

            // sending dummy data to kafka which later will be stored to mongodb and to
            // consume for another service
            kafkaProducer.sendMessage(topic, message);

            // logging event
            logger.info("Summary data has been created : " + message);

        } catch (Exception e) {
            // logging event
            logger.info("Summary data failed to create : " + e);
        }

        // ReceiveLocomotive receiveLocomotive = mapper.readValue(data,
        // ReceiveLocomotive.class);
        // System.out.println(receiveLocomotive.toString());

    }

    public List<SendSummary> get24hSummaries(String data) {

        // retrieve 24h summaries
        List<Summary> summaries = summaryRepository
                .findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime.now().minusHours(24));

        // mapping to SendSummary
        List<SendSummary> sendSummary = summaries.stream().map(item -> {

            // retrieve status detail by summary id
            List<StatusDetail> statuses = statusDetailRepository
                    .findBySummaryTestId(new ObjectId(item.getId()));

            // mapping to SendSummaryStatusTotal
            List<SendSummaryStatusTotal> statusTotal = statuses.stream()
                    .map(st -> new SendSummaryStatusTotal(st.getStatusId(), st.getTotal()))
                    .collect(Collectors.toList());

            return SendSummary.builder().totalLocomotive(item.getTotalLocomotive()).status(statusTotal)
                    .createdAt(item.getCreatedAt().toString()).updatedAt(item.getUpdatedAt().toString()).build();

        }).collect(Collectors.toList());

        return sendSummary;

    };

}
