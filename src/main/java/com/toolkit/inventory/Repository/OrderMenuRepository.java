package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Order;
import com.toolkit.inventory.Domain.OrderMenu;
import com.toolkit.inventory.Projection.OrderMenuView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@RepositoryRestResource(excerptProjection = OrderMenuView.class)
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

    Set<OrderMenu> findAllByOrderOrderByOrderMenuId(Order order);

}
