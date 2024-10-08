package com.mrdevs.sil_service.model.mongodb;

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
@Document(collection = "statusTests")
public class StatusDetail {

    @Id
    private String id;
    private Integer statusId;
    private Integer total;
    private String summaryTestId;

}
