package com.egen.employeeperformanceservice.respository;

import com.egen.employeeperformanceservice.model.entity.PerformanceStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceStatsRepository extends JpaRepository<PerformanceStatistics, String> {
}
