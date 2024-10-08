package com.mrdevs.sil_service.model;

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
public class Summary {

    // private Integer totalOnDuty;
    // private Integer totalOnDepot;
    // private Integer totalUnderMaintenance;
    private Integer totalLocomotive;
    private List<StatusTotal> status;
    @Builder.Default
    private String createdAt = LocalDateTime.now().toString();
    private String updatedAt;

}
