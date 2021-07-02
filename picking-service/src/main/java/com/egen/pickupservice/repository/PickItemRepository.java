package com.egen.pickupservice.repository;

import com.egen.pickupservice.model.entity.PickItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickItemRepository extends JpaRepository<PickItem, String> {

}