package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.MenuIngredient;
import com.toolkit.inventory.Repository.MenuIngredientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MenuIngredientServiceImp implements  MenuIngredientService {

  private MenuIngredientRepository menuIngredientRepository;

  public MenuIngredientServiceImp(MenuIngredientRepository menuIngredientRepository) {
    this.menuIngredientRepository = menuIngredientRepository;
  }

  @Transactional
  @Override
  public void save(MenuIngredient menuIngredient) {
    menuIngredientRepository.save(menuIngredient);
  }


  @Transactional
  @Override
  public void delete(MenuIngredient menuIngredient) {
    menuIngredientRepository.delete(menuIngredient);
  }

}
