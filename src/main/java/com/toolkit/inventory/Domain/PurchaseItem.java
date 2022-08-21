package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "purchase_items")
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_item-id")
    private Long purchaseItemId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "base_uom_id")
    private Uom baseUom;

    @Column(name = "base_qty")
    private BigDecimal baseQty;

    @ManyToOne
    @JoinColumn(name = "requried_uom_id")
    private Uom requiredUom;

    @Column(name = "required_qty")
    private BigDecimal requiredQty;

    @Column(name = "purchased_qty")
    private BigDecimal purchasedQty;

}
