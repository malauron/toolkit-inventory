package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "purchase_items")
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_item_id")
    private Long purchaseItemId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

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

    @Column(name = "purchased_qty")
    private BigDecimal purchasedQty;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @JsonManagedReference
    @OneToMany(mappedBy = "purchaseItem", cascade = CascadeType.ALL)
    Set<PurchaseItemBom> purchaseItemBoms = new HashSet<>();

    public void addPurchaseItemBom(PurchaseItemBom purchaseItemBom) {
        if (purchaseItemBom != null) {
            if (purchaseItemBoms == null) {
                purchaseItemBoms = new HashSet<>();
            }

            purchaseItemBoms.add(purchaseItemBom);
            purchaseItemBom.setPurchaseItem(this);
        }
    }

}
