package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Order;
import com.toolkit.inventory.Projection.OrderView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = OrderView.class)
public interface OrderRepository extends JpaRepository<Order, Long> {
}
