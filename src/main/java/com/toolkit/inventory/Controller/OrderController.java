package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Dto.OrderDto;
import com.toolkit.inventory.Service.OrderService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class OrderController {

  private OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/orders")
  public void save(@RequestBody OrderDto orderDto) {
    this.orderService.save(orderDto);
  }
}
