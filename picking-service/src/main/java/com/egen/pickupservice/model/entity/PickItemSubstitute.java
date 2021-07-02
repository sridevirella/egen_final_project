package com.egen.pickupservice.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "pick_items_subst")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PickItemSubstitute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pick_subst_item_id", nullable = false)
    private String pickSubstItemId;

    @Column(name = "pick_item_id", nullable = false)
    private String pickItemId;

    @Column(name = "pick_item_hght", nullable = false)
    private String pickItemHeight;

    @Column(name = "pick_item_wdth", nullable = false)
    private String pickItemWidth;

    @Column(name = "pick_item_lenght", nullable = false)
    private String pickItemLength;

    @Column(name = "pick_item_wght", nullable = false)
    private String pickItemWeight;

    @Column(name = "pick_item_price", nullable = false)
    private double pickItemPrice;
}
