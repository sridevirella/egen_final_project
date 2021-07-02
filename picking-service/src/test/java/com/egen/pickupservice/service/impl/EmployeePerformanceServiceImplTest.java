package com.egen.pickupservice.service.impl;

import com.egen.pickupservice.PickupServiceApplicationTests;
import com.egen.pickupservice.exception.PickOrderServiceException;
import com.egen.pickupservice.model.dto.EmployeePerfStatsDTO;
import com.egen.pickupservice.model.entity.EmployeePerformance;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.egen.pickupservice.repository.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EmployeePerformanceServiceImplTest extends PickupServiceApplicationTests {

    @MockBean
    private EmployeePerformanceRepository employeePerformanceRepository;

    @Autowired
    private EmployeePerformanceServiceImpl employeePerformanceService;

    @Test
    public void testGetEmpPerformance(){

        Calendar cal1 = Calendar.getInstance();
        cal1.set(2021, 6, 25,14,15);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2021, 6, 25,16,15);

        Calendar cal3 = Calendar.getInstance();
        cal3.set(2021, 6, 26,14,15);
        Calendar cal4 = Calendar.getInstance();
        cal4.set(2021, 6, 26,16,15);

        Calendar cal5 = Calendar.getInstance();
        cal5.set(2021, 6, 27,14,15);
        Calendar cal6 = Calendar.getInstance();
        cal6.set(2021, 6, 27,16,15);

        Calendar cal7 = Calendar.getInstance();
        cal7.set(2021, 6, 28,14,15);
        Calendar cal8 = Calendar.getInstance();
        cal8.set(2021, 6, 28,16,15);


        List<EmployeePerformance> employeePerformanceList = new ArrayList<>();
        EmployeePerformance pr1 = new EmployeePerformance("1",cal1.getTime(),cal2.getTime(),"BATCH","Emp1");
        EmployeePerformance pr2 = new EmployeePerformance("1",cal3.getTime(),cal4.getTime(),"BATCH","Emp1");
        EmployeePerformance pr3 = new EmployeePerformance("1",cal5.getTime(),cal6.getTime(),"SINGLE","Emp1");
        EmployeePerformance pr4 = new EmployeePerformance("1",cal7.getTime(),cal8.getTime(),"SINGLE","Emp1");

        employeePerformanceList.add(pr1);
        employeePerformanceList.add(pr2);
        employeePerformanceList.add(pr3);
        employeePerformanceList.add(pr4);

        when(employeePerformanceRepository.findAll()).thenReturn(employeePerformanceList);

        EmployeePerfStatsDTO employeePerfStatsDTO = employeePerformanceService.getEmpPerformance("emp1");

        assertEquals("120 minutes", employeePerfStatsDTO.getAvgPickTimeSinglePick());
        assertEquals("120 minutes", employeePerfStatsDTO.getAvgPickTimeBatchPick());
        assertEquals(2, employeePerfStatsDTO.getNumOfBatchPick());
        assertEquals(2, employeePerfStatsDTO.getNumOfSinglePick());
    }

    @Test
    public void testGetEmpPerformanceWithException() {

        when(employeePerformanceRepository.findAll()).thenThrow(RuntimeException.class);
        assertThrows(PickOrderServiceException.class, () -> {
            employeePerformanceService.getEmpPerformance("emp1");
        });
    }

}