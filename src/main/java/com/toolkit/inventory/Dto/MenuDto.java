package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.Menu;
import com.toolkit.inventory.Domain.MenuIngredient;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MenuDto {
    private Menu menu;
    private Set<MenuIngredient> menuIngredient;
}
