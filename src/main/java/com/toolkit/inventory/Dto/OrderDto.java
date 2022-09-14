package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.OrderMenu;
import com.toolkit.inventory.Domain.Warehouse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Cacheable;
import java.util.Set;

@Getter
@Setter
public class OrderDto {
  private Long orderId;
  private Warehouse warehouse;
  private String orderStatus;
  private Customer customer;
  private Set<OrderMenu> orderMenus;
  private Set<CartMenu> cartMenus;
}
