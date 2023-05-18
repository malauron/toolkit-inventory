package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "pos_item_price_levels")
public class PosItemPriceLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pos_item_price_level_id")
    private Long posItemPriceLevelId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pos_item_price_id")
    private PosItemPrice posItemPrice;

    @ManyToOne
    @JoinColumn(name = "customer_group_id")
    private CustomerGroup customerGroup;

    @Column(name = "price")
    private BigDecimal price;

}
