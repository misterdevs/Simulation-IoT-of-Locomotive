package com.mrdevs.sil_service.service.locomotive;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mrdevs.sil_service.dto.ReceiveLocomotive;
import com.mrdevs.sil_service.model.postgresql.Locomotive;
import com.mrdevs.sil_service.repository.postgresql.LocomotiveRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocomotiveService {

    private final LocomotiveRepository locomotiveRepository;

    public List<ReceiveLocomotive> get24hLocomotives() {

        // retrieve 24h summaries
        List<Locomotive> locomotives = locomotiveRepository
                .findByCreatedAtAfterOrderByCreatedAtAsc(LocalDateTime.now().minusHours(24));

        // mapping to ReceiveLocomotive
        List<ReceiveLocomotive> sendLocomotive = locomotives.stream()
                .map(item -> ReceiveLocomotive.builder().id(item.getId()).code(item.getCode()).name(item.getName())
                        .statusId(item.getStatusId()).dimension(item.getDimension())
                        .createdAt(item.getCreatedAt().toString()).build())
                .collect(Collectors.toList());

        return sendLocomotive;

    };

}
