package com.mrdevs.sil_service.model.mongodb;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "summaryTests")
public class Summary {

    @Id
    private String id;
    private Integer totalLocomotive;
    private List<StatusDetail> status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
