package com.egen.pickupservice.service;

import com.egen.pickupservice.model.dto.PickOrderDTO;
import com.egen.pickupservice.model.dto.PickStatusDTO;

import java.util.UUID;

public interface PickingService {

    void updatePickingStatus(PickStatusDTO pickStatusDTO);
    PickOrderDTO createOrderPicking(PickOrderDTO pickOrder) throws Exception;
    PickStatusDTO getPickingStatus(UUID pickId);
    void cancelOrderPick(UUID pickId);
}
