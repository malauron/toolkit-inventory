package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Projection.CartMenuView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = CartMenuView.class)
public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {

}
