package com.egen.pickupservice.model.dto;

import com.egen.pickupservice.model.entity.*;
import com.egen.pickupservice.model.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PickOrderDTO implements Serializable {

    private UUID pickId;
    private String pickWarehouseId;
    private String pickStoreId;
    private Date pickCreatedAt;
    private Long pickItemQty;
    private String pickToteId;
    private PickingStatus pickStatus;
    private String pickToteCartId;
    private String pickMethodType;
    private String pickEmpId;
    private List<PickOrderItem> pickOrderItemList;
}
