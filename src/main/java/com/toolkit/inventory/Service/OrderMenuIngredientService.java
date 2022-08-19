package com.toolkit.inventory.Service;

import com.toolkit.inventory.Dto.OrderMenuIngredientSummaryDto;
import com.toolkit.inventory.Projection.OrderMenuIngredientSummaryView;

import java.util.List;
import java.util.Set;

public interface OrderMenuIngredientService {
  void deleteById(Long orderMenuIngredientId);
  Set<OrderMenuIngredientSummaryView> viewSummary(Set<Long> orderIds);
  Set<OrderMenuIngredientSummaryDto> dtoSummary(Set<Long> orderIds);
}
