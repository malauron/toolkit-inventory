package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Order;
import com.toolkit.inventory.Projection.OrderView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(excerptProjection = OrderView.class)
public interface OrderRepository extends JpaRepository<Order, Long> {

//  Page<Order> findByCustomer_CustomerNameContaining(@RequestParam("customerName") String customerName, Pageable pageable);

  Page<Order> findByOrderIdOrCustomer_CustomerNameContainingOrCustomer_AddressContainingOrCustomer_ContactNoContaining(
          @RequestParam("orderId") Long orderId,
          @RequestParam("customerName") String customerName,
          @RequestParam("address") String address,
          @RequestParam("contactNo") String contactNo,
          Pageable pageable);

}
