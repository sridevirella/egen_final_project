package com.egen.pickupservice.repository;

import com.egen.pickupservice.model.entity.EmployeePerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeePerformanceRepository extends JpaRepository<EmployeePerformance, String> {

    List<EmployeePerformance> findAllByPickEmpId(String empId);
}