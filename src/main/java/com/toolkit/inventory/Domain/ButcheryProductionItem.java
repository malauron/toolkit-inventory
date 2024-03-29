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
@Table(name = "butchery_production_items")
public class ButcheryProductionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_production_item_id")
    private Long butcheryProductionItemId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "butchery_production_id")
    private ButcheryProduction butcheryProduction;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "item_class")
    @Enumerated(EnumType.STRING)
    private ItemClass itemClass;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "base_uom_id")
    private Uom baseUom;

    @Column(name = "base_qty")
    private BigDecimal baseQty;

    @ManyToOne
    @JoinColumn(name = "required_uom_id")
    private Uom requiredUom;

    @Column(name = "produced_qty")
    private BigDecimal producedQty;

    @Column(name = "produced_weight_kg")
    private BigDecimal producedWeightKg;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Version
    @Column(name = "version")
    private Long version;
}
