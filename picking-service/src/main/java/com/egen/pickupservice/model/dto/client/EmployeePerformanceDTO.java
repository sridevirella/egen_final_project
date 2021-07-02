package com.egen.pickupservice.model.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeePerformanceDTO {

    private String pickEmpPerfId;
    private Date pickEmpPerfStartDtTm;
    private Date pickEmpPerfEndDtTm;
    private String pickMethodType;
    private String pickEmpId;
}
