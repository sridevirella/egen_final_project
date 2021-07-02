package com.egen.pickupservice.service;

import com.egen.pickupservice.model.dto.EmployeePerfStatsDTO;

public interface EmployeePerformanceService {

    EmployeePerfStatsDTO getEmpPerformance(String empId);
}
