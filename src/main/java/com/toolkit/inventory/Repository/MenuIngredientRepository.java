package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.MenuIngredient;
import com.toolkit.inventory.Domain.MenuIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuIngredientRepository extends JpaRepository<MenuIngredient, Long> {
}
