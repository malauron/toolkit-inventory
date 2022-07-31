package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Projection.CartMenuView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = CartMenuView.class)
public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {

    @Query(value = "SELECT * FROM cart_menus order by cart_menu_id", nativeQuery = true)
    List<CartMenu> findAllCartMenus();
}
