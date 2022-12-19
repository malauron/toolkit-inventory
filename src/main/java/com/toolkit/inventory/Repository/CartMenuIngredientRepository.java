package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Domain.CartMenuIngredient;
import com.toolkit.inventory.Projection.CartMenuIngredientView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(excerptProjection = CartMenuIngredientView.class)
public interface CartMenuIngredientRepository extends JpaRepository<CartMenuIngredient, Long> {

  @Query(value = "select u from CartMenuIngredient u where u.cartMenu = :cartMenu order by u.item.itemName")
  List<CartMenuIngredient> findCartMenuIngredientsByCartMenu(CartMenu cartMenu);
}

