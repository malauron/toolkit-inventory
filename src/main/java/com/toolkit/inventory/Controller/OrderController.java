package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.Order;
import com.toolkit.inventory.Dto.OrderDto;
import com.toolkit.inventory.Dto.OrderMenuDto;
import com.toolkit.inventory.Dto.OrderMenuIngredientSummaryDto;
import com.toolkit.inventory.Projection.OrderMenuIngredientSummaryView;
import com.toolkit.inventory.Service.OrderMenuIngredientService;
import com.toolkit.inventory.Service.OrderMenuService;
import com.toolkit.inventory.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderMenuService orderMenuService;

  @Autowired
  private OrderMenuIngredientService orderMenuIngredientService;

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

  @PatchMapping("/orders")
  public void patch(@RequestBody Order order) {
    this.orderService.patch(order);
  }

  @DeleteMapping("/orders/orderMenus")
  public void deleteByOrderMenuId(@RequestParam Long orderMenuId) {
    this.orderMenuService.deleteById(orderMenuId);
  }

  @DeleteMapping("/orders/orderMenus/orderMenuIngredients")
  public void deleteByOrderMenuIngredientId(@RequestParam Long orderMenuIngredientId) {
    this.orderMenuIngredientService.deleteById(orderMenuIngredientId);
  }

  @GetMapping("/viewSummary")
  public Set<OrderMenuIngredientSummaryView> viewSummary(@RequestParam("orderIds") Set<Long> orderIds) {
    return this.orderMenuIngredientService.viewSummary(orderIds);
  }

  @GetMapping("/dtoSummary")
  public Set<OrderMenuIngredientSummaryDto> dtoSummary(@RequestParam("orderIds") Set<Long> orderIds) {
    return this.orderMenuIngredientService.dtoSummary(orderIds);
  }

}
