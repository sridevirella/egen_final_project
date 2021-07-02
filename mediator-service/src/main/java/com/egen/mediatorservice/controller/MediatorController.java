package com.egen.mediatorservice.controller;

import com.egen.mediatorservice.model.PerformanceStatisticsDTO;
import com.egen.mediatorservice.service.PerformanceStats;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employees/stats")
public class MediatorController {

    private PerformanceStats performanceStats;

    public MediatorController(PerformanceStats performanceStats) {
        this.performanceStats = performanceStats;
    }
    /**
     * An endpoint to get all employee activities
     */
    @GetMapping
    public ResponseEntity<PerformanceStatisticsDTO> getAllEmployeeActivities() {

        return new ResponseEntity<>(performanceStats.getMessage(), HttpStatus.OK);
    }
}