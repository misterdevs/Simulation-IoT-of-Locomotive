package com.mrdevs.sil_service.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveLocomotive {
    private String id;
    private String code;
    private String name;
    private String dimension;
    private Integer statusId;
    private String createdAt;
    private Timestamp updatedAt;
    @Builder.Default
    private Boolean isDeleted = false;
}
