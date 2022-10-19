package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "butchery_receiving_items")
public class ButcheryReceivingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_receiving_item_id")
    private Long butcheryReceivingItemId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "butchery_receiving_id")
    private ButcheryReceiving butcheryReceiving;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "item_class")
    @Enumerated(EnumType.STRING)
    private ItemClass itemClass;

    @ManyToOne
    @JoinColumn(name = "base_uom_id")
    private Uom baseUom;

    @Column(name = "base_qty")
    private BigDecimal baseQty;

    @ManyToOne
    @JoinColumn(name = "required_uom_id")
    private Uom requiredUom;

    @Column(name = "received_qty")
    private BigDecimal receivedQty;

    @Column(name = "item_cost")
    private BigDecimal itemCost;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

}
