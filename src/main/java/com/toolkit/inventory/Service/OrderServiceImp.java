package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.OrderDto;
import com.toolkit.inventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImp  implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderMenuIngredientRepository orderMenuIngredientRepository;

  @Autowired
  private CartMenuRepository cartMenuRepository;

  @Autowired
  private WarehouseRepository warehouseRepository;

  @Autowired
  private ItemCostRepository itemCostRepository;

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
    order.setWarehouse(orderDto.getWarehouse());
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
        orderMenuIngredient.setCost(new BigDecimal(0L));
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

  @Override
  @Transactional
  public void patch(Order order) {

    orderRepository.setOrderStatus(order.getOrderId(), order.getOrderStatus());

  }

  @Override
  @Transactional
  public OrderDto setOrderStatus(OrderDto dto) {

    OrderDto orderDto = new OrderDto();

    orderDto.setOrderId(dto.getOrderId());

    Optional<Order> optOrder = this.orderRepository.findByOrderId(dto.getOrderId());

    if (optOrder.isPresent()) {

      Order order = optOrder.get();

      Optional<Warehouse> optWhse = this.warehouseRepository.findById(order.getWarehouse().getWarehouseId());

      String oldStatus = order.getOrderStatus();
      String newStatus = dto.getOrderStatus();

      orderDto.setOrderStatus(oldStatus);

      if (oldStatus.equals("Preparing")) {

        if (newStatus.equals("Packed")) {

          order.setOrderStatus(newStatus);

          Set<OrderMenuIngredient> orderMenuIngredients = this.orderMenuIngredientRepository.findByOrderOrderByItemId(order);

          orderMenuIngredients.forEach(orderMenuIngredient -> {

            Optional<ItemCost> optItemCost = this.itemCostRepository.findByItemAndWarehouse(orderMenuIngredient.getItem(), optWhse.get());

            if (optItemCost.isPresent()) {

              ItemCost itemCost = optItemCost.get();

              orderMenuIngredient.setCost(itemCost.getCost());

              BigDecimal baseQty = orderMenuIngredient.getBaseQty();
              BigDecimal requiredQty = orderMenuIngredient.getRequiredQty();
              BigDecimal orderedQty = orderMenuIngredient.getOrderedQty();

              BigDecimal qty = baseQty.multiply(requiredQty)
                                      .multiply(orderedQty)
                                      .multiply(new BigDecimal(-1));

              this.orderMenuIngredientRepository.save(orderMenuIngredient);
              this.itemCostRepository.setQty(qty, itemCost.getItemCostId());

            }

          });

        } else if (newStatus.equals("Cancelled")) {

          order.setOrderStatus(newStatus);

        }

      } else if (oldStatus.equals("Packed")) {

        if (newStatus.equals("In Transit") || newStatus.equals("Delivered") || newStatus.equals("Cancelled")) {

          order.setOrderStatus(newStatus);

        }

      } else if (oldStatus.equals("In Transit")) {

        if (newStatus.equals("Delivered") || newStatus.equals("Cancelled")) {

          order.setOrderStatus(newStatus);

        }

      }

      this.orderRepository.save(order);

    }

    return orderDto;

  }

}
