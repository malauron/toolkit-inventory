package com.toolkit.inventory.Service;


import com.toolkit.inventory.Domain.CartMenuIngredient;
import com.toolkit.inventory.Repository.CartMenuIngredientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CartMenuIngredientServiceImp implements CartMenuIngredientService {

  private CartMenuIngredientRepository cartMenuIngredientRepository;

  public CartMenuIngredientServiceImp(CartMenuIngredientRepository cartMenuIngredientRepository) {
    this.cartMenuIngredientRepository = cartMenuIngredientRepository;
  }

  @Override
  @Transactional
  public void delete(CartMenuIngredient cartMenuIngredient) {
    this.cartMenuIngredientRepository.delete(cartMenuIngredient);
  }

  @Override
  @Transactional
  public void deleteById(Long cartMenuIngredientId){
    this.cartMenuIngredientRepository.deleteById(cartMenuIngredientId);
  }

}
