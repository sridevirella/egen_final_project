package com.egen.pickupservice.model.dto;

import com.egen.pickupservice.model.entity.PickOrderItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PickItemDTO implements Serializable {

    private String pickItemId;
    private String pickItemHeight;
    private String pickItemWidth;
    private String pickItemLength;
    private String pickItemWeight;
    private Boolean pickItemAvailability;
    private String pickOrderItemId;
    private String pickZone;
}
