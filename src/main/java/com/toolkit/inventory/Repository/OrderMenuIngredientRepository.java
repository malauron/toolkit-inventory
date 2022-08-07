package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.OrderMenu;
import com.toolkit.inventory.Domain.OrderMenuIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OrderMenuIngredientRepository extends JpaRepository<OrderMenuIngredient, Long> {
    @Query(value = "SELECT o FROM OrderMenuIngredient o WHERE o.orderMenu = :orderMenu ORDER BY o.item.itemName")
    List<OrderMenuIngredient> findOrderMenuIngredientsByOrderMenu(OrderMenu orderMenu);
}
