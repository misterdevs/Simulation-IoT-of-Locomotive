package com.mrdevs.sil_service.dto;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.Data;

@Data
public class ReceiveLocomotive {
    private UUID id;
    private String code;
    private String name;
    private String dimension;
    private Integer statusId;
    private String createdAt;
    private Timestamp updatedAt;
    private Boolean isDeleted = false;
}
