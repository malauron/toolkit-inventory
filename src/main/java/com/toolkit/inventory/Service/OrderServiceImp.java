package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.Order;
import com.toolkit.inventory.Domain.OrderMenu;
import com.toolkit.inventory.Domain.OrderMenuIngredient;
import com.toolkit.inventory.Dto.OrderDto;
import com.toolkit.inventory.Repository.CartMenuRepository;
import com.toolkit.inventory.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImp  implements OrderService {

  private OrderRepository orderRepository;
  private CartMenuRepository cartMenuRepository;

  public OrderServiceImp(OrderRepository orderRepository, CartMenuRepository cartMenuRepository)
  {
    this.orderRepository = orderRepository;
    this.cartMenuRepository = cartMenuRepository;
  }


  @Override
  public Optional<Order> findById(Long orderId) {
    return this.orderRepository.findById(orderId);
  }

  @Override
  @Transactional
  public void save(OrderDto orderDto) {

    Order order = new Order();

    Customer customer = orderDto.getCustomer();

    Set<OrderMenu> tempOrderMenus = orderDto.getOrderMenus();

    order.setCustomer(customer);
    order.setTotalPrice(new BigDecimal(0));
    order.setOrderStatus("Preparing");

    tempOrderMenus.forEach(menu -> {
      OrderMenu orderMenu = new OrderMenu();

      orderMenu.setMenu(menu.getMenu());
      orderMenu.setOrderQty(menu.getOrderQty());
      orderMenu.setPrice(menu.getPrice());
      orderMenu.setLineTotal(menu.getLineTotal());

      menu.getOrderMenuIngredients().forEach(ingredient -> {
        OrderMenuIngredient orderMenuIngredient = new OrderMenuIngredient();

        orderMenuIngredient.setItem(ingredient.getItem());
        orderMenuIngredient.setBaseUom(ingredient.getBaseUom());
        orderMenuIngredient.setBaseQty(ingredient.getBaseQty());
        orderMenuIngredient.setRequiredUom(ingredient.getRequiredUom());
        orderMenuIngredient.setRequiredQty(ingredient.getRequiredQty());
        orderMenuIngredient.setOrderedQty(ingredient.getOrderedQty());
        orderMenuIngredient.setMenuIngredientId(ingredient.getMenuIngredientId());

        orderMenu.addIngredient(orderMenuIngredient);

      });

      order.addMenu(orderMenu);

    });

    this.orderRepository.save(order);

    orderDto.getCartMenus().forEach(cartMenu -> {
      cartMenuRepository.deleteById(cartMenu.getCartMenuId());
    });
  }


}
