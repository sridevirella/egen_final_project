package com.egen.pickupservice.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pick_store_item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PickStoreItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pick_store_item_id")
    private String  pickStoreItemId;

    @Column(name = "pick_store_name")
    private String pickStoreName;

    @Column(name = "item_availability")
    private boolean itemAvailability;

    @Column(name = "item_id")
    private String pickItemID;
}
