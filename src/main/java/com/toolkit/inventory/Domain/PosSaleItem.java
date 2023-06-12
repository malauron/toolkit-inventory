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
@Table(name = "pos_sale_items")
public class PosSaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pos_sale_item_id")
    private Long posSaleItemId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pos_sale_id")
    private PosSale posSale;

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

    @Column(name = "item_price")
    private BigDecimal itemPrice;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

}
