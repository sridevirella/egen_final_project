package com.egen.pickupservice.repository;

import com.egen.pickupservice.model.entity.PickOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PickOrderRepository extends JpaRepository<PickOrder, UUID> {
}