package com.egen.pickupservice.service.impl;

import com.egen.pickupservice.client.*;
import com.egen.pickupservice.exception.PickOrderServiceException;
import com.egen.pickupservice.mapper.OrderPickingMapper;
import com.egen.pickupservice.model.dto.EmployeePerfStatsDTO;
import com.egen.pickupservice.model.entity.EmployeePerformance;
import com.egen.pickupservice.model.enums.*;
import com.egen.pickupservice.repository.*;
import com.egen.pickupservice.service.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeePerformanceServiceImpl implements EmployeePerformanceService, OrderPickListener {

    private EmployeePerformanceRepository employeePerformanceRepository;
    private PerformanceTrackingClient performanceTrackingClient;

    public EmployeePerformanceServiceImpl(EmployeePerformanceRepository employeePerformanceRepository,
                                          PerformanceTrackingClient performanceTrackingClient) {

        this.employeePerformanceRepository = employeePerformanceRepository;
        this.performanceTrackingClient = performanceTrackingClient;
    }

    @Override
    public void onOrderPicked(EmployeePerformance employeePerformance) {

        employeePerformanceRepository.save(employeePerformance);
        performanceTrackingClient.employeePerformanceDetails(OrderPickingMapper.MAPPER.toEmployeePerformance(employeePerformance));
    }

    @Override
    public EmployeePerfStatsDTO getEmpPerformance(String empId) {

        long avgTimeForSinglePick;
        long avgTimeForBatchPick;

        try {

            EmployeePerfStatsDTO employeePerfStatsDTO = new EmployeePerfStatsDTO();
            employeePerfStatsDTO.setEmployeeId(empId);

            List<EmployeePerformance> allEmployees = employeePerformanceRepository.findAll();
            List<EmployeePerformance> employeePerformanceList = allEmployees.stream()
                                                                             .filter(employee -> employee.getPickEmpId().equalsIgnoreCase(empId))
                                                                             .collect(Collectors.toList());

            //number of Batch order picks by employee
            List<EmployeePerformance> employeeBatchPerformanceList = employeePerformanceList.stream()
                                                                                            .filter(employeePerformance -> employeePerformance.getPickMethodType().equalsIgnoreCase(PickingMethod.BATCH.name()))
                                                                                            .collect(Collectors.toList());
            employeePerfStatsDTO.setNumOfBatchPick(employeeBatchPerformanceList.size());

            //number of single order picks by employee
            List<EmployeePerformance> employeeSinglePerformanceList = employeePerformanceList.stream()
                                                                                             .filter(employeePerformance -> employeePerformance.getPickMethodType().equalsIgnoreCase(PickingMethod.SINGLE.name()))
                                                                                              .collect(Collectors.toList());

            employeePerfStatsDTO.setNumOfSinglePick(employeeSinglePerformanceList.size());


            //time taken by employee to pick single order
            List<Long> timeTakenBySinglePickList = employeeSinglePerformanceList.stream()
                                                                                .map(employeePerformance -> ((employeePerformance.getPickEmpPerfEndDtTm().getTime() - employeePerformance.getPickEmpPerfStartDtTm().getTime()) / 60000))
                                                                                .collect(Collectors.toList());
            //time taken by employee to pick batch order
            List<Long> timeTakenByBatchPickList = employeeBatchPerformanceList.stream()
                                                                              .map(employeePerformance -> ((employeePerformance.getPickEmpPerfEndDtTm().getTime() - employeePerformance.getPickEmpPerfStartDtTm().getTime()) / 60000))
                                                                              .collect(Collectors.toList());

            //employee average picking time per order
            if(timeTakenBySinglePickList.size() != 0) {
                avgTimeForSinglePick = timeTakenBySinglePickList.stream()
                                                                     .reduce(0L, Long::sum) / timeTakenBySinglePickList.size();
                employeePerfStatsDTO.setAvgPickTimeSinglePick(avgTimeForSinglePick + " minutes");
            }

            if (timeTakenByBatchPickList.size() != 0) {

                avgTimeForBatchPick = timeTakenByBatchPickList.stream().reduce(0L, Long::sum) / timeTakenByBatchPickList.size();
                employeePerfStatsDTO.setAvgPickTimeBatchPick(avgTimeForBatchPick + " minutes");
            }

            return employeePerfStatsDTO;

        }catch (Exception e){
            log.error("Error occurred in getEmpPerformance: "+e.getMessage());
            throw new PickOrderServiceException(e.getMessage());
        }
    }
}
