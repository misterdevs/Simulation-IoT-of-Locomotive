package com.mrdevs.sil_scheduler.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Locomotive {

    @Builder.Default
    private UUID id = UUID.randomUUID();
    @Builder.Default
    private String code = "LOCO-" + UUID.randomUUID().toString().substring(0, 3).toUpperCase();
    private String name;
    private String dimension;
    private Integer statusId;
    @Builder.Default
    private String createdAt = LocalDateTime.now().toString();
    private String updatedAt;
    @Builder.Default
    private Boolean isDeleted = false;

}
