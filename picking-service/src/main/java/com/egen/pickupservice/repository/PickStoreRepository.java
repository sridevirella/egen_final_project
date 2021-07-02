package com.egen.pickupservice.repository;

import com.egen.pickupservice.model.entity.PickStoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickStoreRepository extends JpaRepository<PickStoreItem, String> {
    PickStoreItem findByPickItemID(String itemId);
}
