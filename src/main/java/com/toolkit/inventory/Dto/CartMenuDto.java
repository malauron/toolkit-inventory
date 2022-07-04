package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Domain.CartMenuIngredient;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CartMenuDto {
    private CartMenu cartMenu;
    private Set<CartMenuIngredient> cartMenuIngredients;
}
