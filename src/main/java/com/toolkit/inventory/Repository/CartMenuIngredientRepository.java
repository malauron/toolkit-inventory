package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.CartMenuIngredient;
import com.toolkit.inventory.Projection.CartMenuIngredientView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = CartMenuIngredientView.class)
public interface CartMenuIngredientRepository extends JpaRepository<CartMenuIngredient, Long> {
}
