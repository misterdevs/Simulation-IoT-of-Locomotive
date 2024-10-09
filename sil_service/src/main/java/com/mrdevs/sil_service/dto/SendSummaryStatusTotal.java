package com.mrdevs.sil_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendSummaryStatusTotal {
    private Integer statusId;
    private String statusTitle;
    private Integer total;
}
