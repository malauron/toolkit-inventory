package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "item_generics")
public class ItemGeneric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_generic_id")
    private Long itemGenericId;

    @ManyToOne
    @JoinColumn(name = "main_item_id")
    private Item mainItem;

    @ManyToOne
    @JoinColumn(name = "sub_item_id")
    private Item subItem;

    @ManyToOne
    @JoinColumn(name = "required_uom_id")
    private Uom requiredUom;

    @Column(name = "required_qty")
    private BigDecimal requiredQty;

}
