package com.egen.pickupservice.repository;


import com.egen.pickupservice.model.entity.PickItemSubstitute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PickItemSubstRepository extends JpaRepository<PickItemSubstitute, String> {
    PickItemSubstitute findByPickItemId(String itemId);
}