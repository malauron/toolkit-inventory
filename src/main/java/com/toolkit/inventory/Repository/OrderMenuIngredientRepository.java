package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.OrderMenu;
import com.toolkit.inventory.Domain.OrderMenuIngredient;
import com.toolkit.inventory.Dto.OrderMenuIngredientSummaryDto;
import com.toolkit.inventory.Projection.OrderMenuIngredientSummaryView;
import com.toolkit.inventory.Projection.OrderMenuIngredientView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@RepositoryRestResource(excerptProjection = OrderMenuIngredientView.class)
public interface OrderMenuIngredientRepository extends JpaRepository<OrderMenuIngredient, Long> {
    @Query(value = "SELECT o FROM OrderMenuIngredient o WHERE o.orderMenu = :orderMenu ORDER BY o.item.itemName")
    List<OrderMenuIngredient> findOrderMenuIngredientsByOrderMenu(OrderMenu orderMenu);

    @Query(value = "SELECT o  FROM OrderMenuIngredient o " +
            "WHERE o.orderMenu.order.orderId IN :orderIds ORDER BY o.orderMenuIngredientId ASC ")
    List<OrderMenuIngredient> findOrderMenuIngredientsByOrderIds(@RequestParam("orderIds") Set<Long> orderIds);

    @Query(value = "SELECT o.item as item, o.baseUom AS baseUom, SUM(o.baseQty * o.requiredQty * o.orderedQty) AS totalQty " +
            "FROM OrderMenuIngredient o " +
            "WHERE o.orderMenu.order.orderId IN :orderIds " +
            "GROUP BY o.item, o.baseUom  ORDER BY o.item.itemName")
    Set<OrderMenuIngredientSummaryView> findSummaryOrderMenuIngredientsView(@RequestParam("orderIds") Set<Long> orderIds);

    @Query(value = "SELECT new com.toolkit.inventory.Dto.OrderMenuIngredientSummaryDto(o.item, o.baseUom, " +
            "SUM(o.baseQty), SUM(o.requiredQty) , SUM(o.orderedQty))  " +
            "FROM OrderMenuIngredient o WHERE o.orderMenu.order.orderId IN :orderIds " +
            "GROUP BY o.item, o.baseUom ORDER BY o.item.itemName")
    Set<OrderMenuIngredientSummaryDto> findSummaryOrderMenuIngredientsDto(@RequestParam("orderIds") Set<Long> orderIds);

}
