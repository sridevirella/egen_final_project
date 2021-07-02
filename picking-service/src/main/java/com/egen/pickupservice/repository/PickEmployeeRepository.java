package com.egen.pickupservice.repository;

import com.egen.pickupservice.model.entity.PickEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickEmployeeRepository extends JpaRepository<PickEmployee, String> {

    List<PickEmployee> findByStoreIdOrderByPickEmpIdAsc(String storeId);
    PickEmployee findByPickEmpId(String pickEmpId);
}