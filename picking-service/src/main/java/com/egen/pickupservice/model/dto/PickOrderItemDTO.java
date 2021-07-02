package com.egen.pickupservice.model.dto;

import com.egen.pickupservice.model.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PickOrderItemDTO implements Serializable {

    private String pickOrderItemId;
    private String pickOrderItemName;
    private Long pickOrderItemQty;
    private String pickOrderItemHeight;
    private String pickOrderItemWidth;
    private String pickOrderItemLength;
    private UUID pickId;
    private boolean pickIsSubstAllowed;
    private List<PickItem> pickItems;
}
