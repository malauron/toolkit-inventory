package com.toolkit.inventory.Domain;

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
@Table(name = "pos_item_prices")
public class PosItemPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pos_item_price_id")
    private Long posItemPriceId;

    @JsonManagedReference
    @OneToMany(mappedBy = "posItemPrice", cascade = CascadeType.ALL)
    private Set<PosItemPriceLevel> posItemPriceLevels = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "default_price")
    private BigDecimal defaultPrice;

    public void addPosItemPriceLevel(PosItemPriceLevel posItemPriceLevel) {
        if (posItemPriceLevel != null) {
            if (this.posItemPriceLevels == null) {
                this.posItemPriceLevels = new HashSet<>();
            }

            this.posItemPriceLevels.add(posItemPriceLevel);
            posItemPriceLevel.setPosItemPrice(this);
        }
    }

}
