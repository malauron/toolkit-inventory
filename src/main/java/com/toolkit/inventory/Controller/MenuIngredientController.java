package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.MenuIngredient;
import com.toolkit.inventory.Service.MenuIngredientService;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class MenuIngredientController {

  private MenuIngredientService menuIngredientService;

  public MenuIngredientController(MenuIngredientService menuIngredientService) {
    this.menuIngredientService = menuIngredientService;
  }

  @PostMapping("/menuIngredients")
  public void save(@RequestBody MenuIngredient menuIngredient) {
    menuIngredientService.save(menuIngredient);
  }

  @DeleteMapping("/menuIngredients")
  public void delete(@RequestBody MenuIngredient menuIngredient) {
    menuIngredientService.delete(menuIngredient);
  }
}
