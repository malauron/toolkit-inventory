package com.toolkit.inventory.Service;

import com.toolkit.inventory.Repository.OrderMenuIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderMenuIngredientServiceImp implements OrderMenuIngredientService {

  @Autowired
  private OrderMenuIngredientRepository orderMenuIngredientRepository;


  @Override
  @Transactional
  public void deleteById(Long orderMenuIngredientId) {
    this.orderMenuIngredientRepository.deleteById(orderMenuIngredientId);
  }
}
