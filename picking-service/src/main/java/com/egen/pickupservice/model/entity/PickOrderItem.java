package com.egen.pickupservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "pick_order_item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PickOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pick_order_item_id", nullable = false)
    private String pickOrderItemId;

    @Column(name = "pick_order_item_name", nullable = false)
    private String pickOrderItemName;

    @Column(name = "pick_order_item_qty", nullable = false)
    private Long pickOrderItemQty;

    @Column(name = "pick_order_item_hght", nullable = false)
    private String pickOrderItemHeight;

    @Column(name = "pick_order_item_wdth", nullable = false)
    private String pickOrderItemWidth;

    @Column(name = "pick_order_item_length", nullable = false)
    private String pickOrderItemLength;

    @Column(name = "pick_is_subst_allowed",nullable = false)
    private boolean pickIsSubstAllowed;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "pick_order_item_id",nullable=false)
    private List<PickItem> pickItems;
}