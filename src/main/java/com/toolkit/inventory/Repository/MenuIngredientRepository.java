package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.MenuIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource(excerptProjection = MenuIngredient.class)
public interface MenuIngredientRepository extends JpaRepository<MenuIngredient, Long> {

    @Query(value = "SELECT * FROM menu_ingredients where menu_id=:menuId", nativeQuery = true)
    List<MenuIngredient> findByMenuId(@Param("menuId")BigInteger menuId);
}
