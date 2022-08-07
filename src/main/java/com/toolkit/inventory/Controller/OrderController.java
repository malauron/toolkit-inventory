package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.Order;
import com.toolkit.inventory.Dto.OrderDto;
import com.toolkit.inventory.Dto.OrderMenuDto;
import com.toolkit.inventory.Service.OrderMenuService;
import com.toolkit.inventory.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderMenuService orderMenuService;

//  public OrderController(OrderService orderService) {
//    this.orderService = orderService;
//  }

  @GetMapping("/orders/orderMenus")
  public List<OrderMenuDto> getOrderMenus(@RequestBody Order order) {

    return this.orderMenuService.getOrderMenus(order);
  }

  @PostMapping("/orders")
  public void save(@RequestBody OrderDto orderDto) {
    this.orderService.save(orderDto);
  }
}
