package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.CartMenuIngredient;

public interface CartMenuIngredientService {
  void delete(CartMenuIngredient cartMenuIngredient);
  void deleteById(Long cartMenuIngredientId);
}
