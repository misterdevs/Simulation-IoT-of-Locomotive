package com.mrdevs.sil_service.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendSummary {
    private Integer totalLocomotive;
    private List<SendSummaryStatusTotal> status;
    @Builder.Default
    private String createdAt = LocalDateTime.now().toString();
    private String updatedAt;
}
