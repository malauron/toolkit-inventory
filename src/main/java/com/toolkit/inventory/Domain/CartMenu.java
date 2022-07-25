package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cartMenuId")
@Table(name = "cart_menus")
public class CartMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_menu_id")
    private Long cartMenuId;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "order_qty")
    private BigDecimal orderQty;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "line_total")
    private BigDecimal lineTotal;

    @OneToMany(mappedBy = "cartMenu", cascade = CascadeType.ALL)
    Set<CartMenuIngredient> cartMenuIngredients = new HashSet<>();

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Date dateUpdated;

    public void addIngredient(CartMenuIngredient cartMenuIngredient) {
        if (cartMenuIngredient != null) {
            if (cartMenuIngredients == null) {
                cartMenuIngredients = new HashSet<>();
            }

            cartMenuIngredients.add(cartMenuIngredient);
            cartMenuIngredient.setCartMenu(this);
        }
    }

}
