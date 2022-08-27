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

import java.util.Set;

@RepositoryRestResource(excerptProjection = OrderView.class)
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Modifying
  @Query("UPDATE Order o set o.orderStatus = :orderStatus WHERE o.orderId = :orderId")
  void setOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") String orderStatus);

  Page<Order> findByOrderIdOrCustomer_CustomerNameContainingOrCustomer_AddressContainingOrCustomer_ContactNoContainingAndOrderStatusIn(
          @RequestParam("orderId") Long orderId,
          @RequestParam("customerName") String customerName,
          @RequestParam("address") String address,
          @RequestParam("contactNo") String contactNo,
          @RequestParam("orderStatus") Set<String> orderStatus,
          Pageable pageable);

  @Query(value = "SELECT o FROM Order o WHERE (o.orderId LIKE :orderId OR o.customer.customerName LIKE %:customerName% OR " +
          "o.customer.address LIKE %:address% OR o.customer.contactNo LIKE %:contactNo%)  AND o.orderStatus IN :orderStatus ORDER BY o.orderId")
  Page<Order> findUndeliveredOrders(
          @RequestParam("orderId") Long orderId,
          @RequestParam("customerName") String customerName,
          @RequestParam("address") String address,
          @RequestParam("contactNo") String contactNo,
          @RequestParam("orderStatus") Set<String> orderStatus, Pageable pageable);

}
