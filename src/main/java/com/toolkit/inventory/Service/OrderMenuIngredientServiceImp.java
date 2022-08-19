package com.toolkit.inventory.Service;

import com.toolkit.inventory.Dto.OrderMenuIngredientSummaryDto;
import com.toolkit.inventory.Projection.OrderMenuIngredientSummaryView;
import com.toolkit.inventory.Repository.OrderMenuIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class OrderMenuIngredientServiceImp implements OrderMenuIngredientService {

  @Autowired
  private OrderMenuIngredientRepository orderMenuIngredientRepository;


  @Override
  @Transactional
  public void deleteById(Long orderMenuIngredientId) {
    this.orderMenuIngredientRepository.deleteById(orderMenuIngredientId);
  }

  @Override
  public Set<OrderMenuIngredientSummaryView> viewSummary(Set<Long> orderIds) {
    return this.orderMenuIngredientRepository.findSummaryOrderMenuIngredientsView(orderIds);
  }

  @Override
  public Set<OrderMenuIngredientSummaryDto> dtoSummary(Set<Long> orderIds) {
    return this.orderMenuIngredientRepository.findSummaryOrderMenuIngredientsDto(orderIds);
  }
}
