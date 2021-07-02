package com.egen.pickupservice.model.entity;


import com.egen.pickupservice.model.enums.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "pick_order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PickOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "pick_id", nullable = false)
    private UUID pickId;

    @Column(name = "pick_warehouse_id", nullable = false)
    private String pickWarehouseId;

    @Column(name = "pick_store_id", nullable = false)
    private String pickStoreId;

    @Column(name = "pick_created_at")
    private Date pickCreatedAt;

    @Column(name = "pick_item_qty", nullable = false)
    private Long pickItemQty;

    @Column(name = "pick_tote_id", nullable = false)
    private String pickToteId;

    @Column(name = "pick_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PickingStatus pickStatus;

    @Column(name = "pick_tote_cart_id", nullable = false)
    private String pickToteCartId;

    @Column(name = "pick_method_type", nullable = false)// we are going to set while order pickup creation
    private String pickMethodType;

    @Column(name = "pick_emp_id") // set at the time of order pickup creation
    private String pickEmpId;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "pick_id",nullable=false)
    private List<PickOrderItem> pickOrderItemList;
}
