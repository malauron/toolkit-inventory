package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.Order;
import com.toolkit.inventory.Dto.OrderDto;
import com.toolkit.inventory.Dto.OrderMenuDto;
import com.toolkit.inventory.Service.OrderMenuService;
import com.toolkit.inventory.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderMenuService orderMenuService;

  @GetMapping("/orders/orderMenus")
  public List<OrderMenuDto> getOrderMenusById(@RequestParam Long orderId) {
    Optional<Order> order = this.orderService.findById(orderId);
    if (order.isPresent()) {
      return this.orderMenuService.getOrderMenus(order.get());
    }
    return null;
  }

//  @PostMapping("/orders/orderMenus")
//  public List<OrderMenuDto> getOrderMenus(@RequestBody Order order) {
//    return this.orderMenuService.getOrderMenus(order);
//  }

  @PostMapping("/orders")
  public void save(@RequestBody OrderDto orderDto) {
    this.orderService.save(orderDto);
  }
}
