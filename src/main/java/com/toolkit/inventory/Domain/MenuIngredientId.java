package com.toolkit.inventory.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
public class MenuIngredientId implements Serializable {

    private Long menuId;
    private Long itemId;
//    private Menu menu;
//    private Item item;

}
