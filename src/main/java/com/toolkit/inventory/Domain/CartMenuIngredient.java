package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cartMenuIngredientId")
@Table(name = "cart_menu_ingredients")
public class CartMenuIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_menu_ingredient_id")
    private Long cartMenuIngredientId;

    @ManyToOne
    @JoinColumn(name = "cart_menu_id")
    private CartMenu cartMenu;

    @ManyToOne
    @JoinColumn(name = "base_uom_id")
    private Uom baseUom;

    @Column(name = "base_qty")
    private BigDecimal baseQty;

    @ManyToOne
    @JoinColumn(name = "required_uom_id")
    private Uom requiredUom;

    @Column(name = "required_qty")
    private BigDecimal requiredQty;

    @Column(name = "ordered_qty")
    private BigDecimal orderedQty;
}
