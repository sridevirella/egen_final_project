package com.egen.employeeperformanceservice.mapper;

import com.egen.employeeperformanceservice.model.dto.PerformanceStatisticsDTO;
import com.egen.employeeperformanceservice.model.entity.PerformanceStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper class provides DTO to entity and vice versa conversion.
 */
@Mapper
public interface PerformanceStatsMapper {

    PerformanceStatsMapper MAPPER = Mappers.getMapper(PerformanceStatsMapper.class );

    PerformanceStatisticsDTO toPerformanceStatisticsDTO(PerformanceStatistics performanceStatistics);
    PerformanceStatistics toPerformanceStatistics(PerformanceStatisticsDTO performanceStatisticsDTO);
}
