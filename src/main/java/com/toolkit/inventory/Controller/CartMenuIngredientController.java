package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.CartMenuIngredient;
import com.toolkit.inventory.Service.CartMenuIngredientService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class CartMenuIngredientController {

  private CartMenuIngredientService cartMenuIngredientService;

  public CartMenuIngredientController(CartMenuIngredientService cartMenuIngredientService) {
    this.cartMenuIngredientService = cartMenuIngredientService;
  }

  @DeleteMapping("/cartMenuIngredients")
  public void delete(@RequestBody CartMenuIngredient cartMenuIngredient) {
    this.cartMenuIngredientService.deleteById(cartMenuIngredient.getCartMenuIngredientId());
  }
}
