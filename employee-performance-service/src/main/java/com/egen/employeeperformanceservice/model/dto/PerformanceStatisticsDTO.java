package com.egen.employeeperformanceservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceStatisticsDTO implements Serializable {

    private String pickEmpPerfId;
    private Date pickEmpPerfStartDtTm;
    private Date pickEmpPerfEndDtTm;
    private String pickMethodType;
    private String pickEmpId;
}
