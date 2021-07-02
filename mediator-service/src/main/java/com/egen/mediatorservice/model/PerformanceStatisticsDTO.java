package com.egen.mediatorservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceStatisticsDTO implements Serializable {

    private String employeeId;
    private Integer numOfSinglePick;
    private String avgPickTimeSinglePick;
    private Integer numOfBatchPick;
    private String avgPickTimeBatchPick;
}
