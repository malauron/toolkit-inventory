package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Order;
import com.toolkit.inventory.Dto.OrderDto;

import java.util.Optional;

public interface OrderService {
  Optional<Order> findById(Long orderId);
  void save(OrderDto orderDto);
}
