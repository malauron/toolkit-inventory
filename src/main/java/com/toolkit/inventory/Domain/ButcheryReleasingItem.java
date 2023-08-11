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
@Table(name = "butchery_releasing_items")
public class ButcheryReleasingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_releasing_item_id")
    private Long butcheryReleasingItemId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "butchery_releasing_id")
    private ButcheryReleasing butcheryReleasing;

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

    @Column(name = "cost")
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "required_uom_id")
    private Uom requiredUom;

    @Column(name = "released_qty")
    private BigDecimal releasedQty;

    @Column(name = "released_weight_kg")
    private BigDecimal releasedWeightKg;

    @Column(name = "item_price")
    private BigDecimal itemPrice;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @OneToOne
    @JoinColumn(name = "butchery_production_item_id")
    private ButcheryProductionItem butcheryProductionItem;

}
