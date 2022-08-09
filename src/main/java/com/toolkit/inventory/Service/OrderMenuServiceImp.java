package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.OrderMenuDto;
import com.toolkit.inventory.Dto.OrderMenuIngredientDto;
import com.toolkit.inventory.Repository.MenuIngredientRepository;
import com.toolkit.inventory.Repository.OrderMenuIngredientRepository;
import com.toolkit.inventory.Repository.OrderMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class OrderMenuServiceImp implements OrderMenuService {

    @Autowired
    private OrderMenuRepository orderMenuRepository;

    @Autowired
    private OrderMenuIngredientRepository orderMenuIngredientRepository;

    @Autowired
    private MenuIngredientRepository menuIngredientRepository;

    @Override
    public List<OrderMenuDto> getOrderMenus(Order order) {

        Set<OrderMenu> orderMenus = this.orderMenuRepository.findAllByOrderOrderByOrderMenuId(order);
        List<OrderMenuDto> orderMenuDtos = getOrderMenus(orderMenus);
        return orderMenuDtos;
    }

    @Override
    public void deleteById(Long orderMenuId) {
        this.orderMenuRepository.deleteById(orderMenuId);
    }

    private List<OrderMenuDto> getOrderMenus(Set<OrderMenu> orderMenus) {
        List<OrderMenuDto> orderMenuDtos = new ArrayList<>();
        orderMenus.forEach(orderMenu -> {
            Menu menu = new Menu();

            OrderMenuDto orderMenuDto = new OrderMenuDto();
            orderMenuDto.setOrderMenuId(orderMenu.getOrderMenuId());
            orderMenuDto.setOrderQty(orderMenu.getOrderQty());
            orderMenuDto.setPrice(orderMenu.getPrice());
            orderMenuDto.setLineTotal(orderMenu.getLineTotal());

            menu.setMenuId(orderMenu.getMenu().getMenuId());
            menu.setMenuName(orderMenu.getMenu().getMenuName());
            menu.setRemarks(orderMenu.getMenu().getRemarks());
            orderMenuDto.setMenu(menu);

            Set<MenuIngredient> menuIngredients = this.menuIngredientRepository.findMenuIngredientsByMenu(orderMenu.getMenu());
            Set<OrderMenuIngredient> orderMenuIngredients = new HashSet<>(
                    this.orderMenuIngredientRepository.findOrderMenuIngredientsByOrderMenu(orderMenu)
            );
            List<OrderMenuIngredientDto> menuIngredientDtos = this.getOrderMenuIngredients(menuIngredients, orderMenuIngredients);
            orderMenuDto.setOrderMenuIngredients(menuIngredientDtos);

            orderMenuDtos.add(orderMenuDto);
        });
//        System.out.println(orderMenuDtos);
        return orderMenuDtos;
    }

    private List<OrderMenuIngredientDto> getOrderMenuIngredients(
            Set<MenuIngredient> menuIngredients, Set<OrderMenuIngredient> orderMenuIngredients) {

        List<OrderMenuIngredientDto> orderMenuIngredientDtos = new ArrayList<>();
        menuIngredients.forEach(menuIngredient -> {
            Item item = new Item();

            OrderMenuIngredientDto orderMenuIngredientDto = new OrderMenuIngredientDto();
            orderMenuIngredientDto.setMenuIngredientId(menuIngredient.getMenuIngredientId());
            orderMenuIngredientDto.setRequiredUom(menuIngredient.getRequiredUom());
            orderMenuIngredientDto.setRequiredQty(menuIngredient.getRequiredQty());

            item.setItemId(menuIngredient.getItem().getItemId());
            item.setItemName(menuIngredient.getItem().getItemName());
            orderMenuIngredientDto.setItem(item);

            orderMenuIngredientDto.setOrderMenuIngredientId(
                    this.getOrderMenuIngredientId(menuIngredient.getMenuIngredientId(),orderMenuIngredients)
            );

            orderMenuIngredientDtos.add(orderMenuIngredientDto);
        });

        return orderMenuIngredientDtos;
    }

    private Long getOrderMenuIngredientId(Long menuIngredientId, Set<OrderMenuIngredient> orderMenuIngredients) {
        Stream<OrderMenuIngredient> ing = orderMenuIngredients.stream();
        Optional<OrderMenuIngredient> tmpIng = ing.filter(ingredients ->
            ingredients.getMenuIngredientId() == menuIngredientId
        ).findFirst();

         if (tmpIng.isPresent()) {
             return tmpIng.get().getOrderMenuIngredientId();
         }
        return 0L;
    }


}
