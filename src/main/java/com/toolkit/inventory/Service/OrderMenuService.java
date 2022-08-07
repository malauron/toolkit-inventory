package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Order;
import com.toolkit.inventory.Dto.OrderMenuDto;

import java.util.List;

public interface OrderMenuService {
    List<OrderMenuDto> getOrderMenus(Order order);
}
