package com.egen.pickupservice.model.dto;

import com.egen.pickupservice.model.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PickStatusDTO {

    private UUID pickId;
    private PickingStatus pickingStatus;
}
