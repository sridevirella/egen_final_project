package com.egen.pickupservice.service;

import com.egen.pickupservice.model.entity.EmployeePerformance;

public interface OrderPickListener {

    void onOrderPicked(EmployeePerformance employeePerformance);
}
