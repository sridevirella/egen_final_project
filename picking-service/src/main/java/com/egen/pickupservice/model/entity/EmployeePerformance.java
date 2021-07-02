package com.egen.pickupservice.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "employees_performance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePerformance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
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
