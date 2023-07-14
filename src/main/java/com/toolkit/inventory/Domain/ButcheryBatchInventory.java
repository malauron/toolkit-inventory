package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "butchery_batch_inventory")
public class ButcheryBatchInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_batch_inventory_id")
    private Long butcheryBatchInventoryId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "butchery_batch_id")
    private ButcheryBatch butcheryBatch;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "received_qty")
    private BigDecimal receivedQty;

    @Column(name = "received_weight_kg")
    private BigDecimal receivedWeightKg;

    @Column(name = "remaining_qty")
    private BigDecimal remainingQty;

    @Column(name = "remaining_weight_kg")
    private BigDecimal remainingWeightKg;

    @Version
    @Column(name = "version")
    private Long version;
}
