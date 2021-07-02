package com.egen.employeeperformanceservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "performance_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceStatistics {

    @Id
    @Column(name = "pick_emp_perf_id", nullable = false)
    private String pickEmpPerfId;

    @Column(name = "pick_emp_perf_start_dt_tm", nullable = false)
    private Date pickEmpPerfStartDtTm;

    @Column(name = "pick_emp_perf_end_dt_tm", nullable = false)
    private Date pickEmpPerfEndDtTm;

    @Column(name = "pick_method_type", nullable = false)
    private String pickMethodType;

    @Column(name = "pick_emp_id", nullable = false)
    private String pickEmpId;
}
