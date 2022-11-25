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
@Table(name = "inventory_history_items")
public class InventoryHistoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_history_item_id")
    private Long inventoryHistoryItemId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "inventory_history_id")
    private InventoryHistory inventoryHistory;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "beginning_qty")
    private BigDecimal beginningQty;

    @Column(name = "purchased_qty")
    private BigDecimal purchasedQty;

    @Column(name = "ending_qty")
    private BigDecimal endingQty;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "price")
    private BigDecimal price;

}
