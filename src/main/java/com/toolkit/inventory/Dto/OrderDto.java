package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.OrderMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderDto {
  private Customer customer;
  private Set<OrderMenu> orderMenus;
  private Set<CartMenu> cartMenus;
}
