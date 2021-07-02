package com.egen.pickupservice.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "pick_employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PickEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pick_emp_id", nullable = false)
    private String pickEmpId;

    @Column(name = "pick_emp_first_name", nullable = false)
    private String pickEmpFirstName;

    @Column(name = "pick_emp_last_name", nullable = false)
    private String pickEmpLastName;

    @Column(name = "pick_emp_occupied", nullable = false)
    private boolean pickEmpOccupied;

    @Column(name = "store_id", nullable = false)
    private String storeId;

}
