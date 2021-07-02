package com.egen.pickupservice.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "pick_item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PickItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

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

    @Column(name = "pick_zone", nullable = false)
    private String pickZone;

}
