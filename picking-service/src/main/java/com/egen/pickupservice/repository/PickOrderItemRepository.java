package com.egen.pickupservice.repository;

import com.egen.pickupservice.model.entity.PickOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickOrderItemRepository extends JpaRepository<PickOrderItem, String> {

}