package com.egen.pickupservice.client;

import com.egen.pickupservice.model.dto.client.EmployeePerformanceDTO;

public interface PerformanceTrackingClient {

    void employeePerformanceDetails(EmployeePerformanceDTO employeePerformanceDTO);
}
