package com.egen.pickupservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeePerfStatsDTO implements Serializable {

    private String employeeId;
    private Integer numOfSinglePick;
    private String avgPickTimeSinglePick;
    private Integer numOfBatchPick;
    private String avgPickTimeBatchPick;
}
