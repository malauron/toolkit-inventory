package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.MenuIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MenuIngredientRepository extends JpaRepository<MenuIngredient, Long> {
}
