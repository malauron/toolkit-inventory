package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "item_add_on_contents")
public class ItemAddOnContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_add_on_content_id")
    private Long itemAddOnContentId;

    @ManyToOne
    @JoinColumn(name = "item_add_on_detail_id")
    private ItemAddOnDetail itemAddOnDetail;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "uom_id")
    private Uom uom;

    @Column(name = "qty")
    private BigDecimal qty;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "alt_desc")
    private String altDesc;

}
