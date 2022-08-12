package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Order;
import com.toolkit.inventory.Projection.OrderView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(excerptProjection = OrderView.class)
public interface OrderRepository extends JpaRepository<Order, Long> {

//  Page<Order> findByCustomer_CustomerNameContaining(@RequestParam("customerName") String customerName, Pageable pageable);

  @Modifying
  @Query("UPDATE Order o set o.orderStatus = :orderStatus WHERE o.orderId = :orderId")
  void setOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") String orderStatus);

  Page<Order> findByOrderIdOrCustomer_CustomerNameContainingOrCustomer_AddressContainingOrCustomer_ContactNoContaining(
          @RequestParam("orderId") Long orderId,
          @RequestParam("customerName") String customerName,
          @RequestParam("address") String address,
          @RequestParam("contactNo") String contactNo,
          Pageable pageable);

}
