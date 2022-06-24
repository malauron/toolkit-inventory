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
        property = "menuId")
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "alt_remarks")
    private String altRemarks;

//    @JsonManagedReference
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    Set<MenuIngredient> menuIngredient  = new HashSet<>();

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Date dateUpdated;

    public void addIngredient(MenuIngredient ingredient) {

        if (ingredient != null) {
            if (menuIngredient == null) {
                menuIngredient = new HashSet<>();
            }

            menuIngredient.add(ingredient);
            ingredient.setMenu(this);
        }
    }
}
