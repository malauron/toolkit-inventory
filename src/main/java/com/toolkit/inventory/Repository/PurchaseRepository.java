package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Purchase;
import com.toolkit.inventory.Domain.Vendor;
import com.toolkit.inventory.Projection.PurchaseView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(excerptProjection = PurchaseView.class)
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

  @Lock(LockModeType.OPTIMISTIC)
  Optional<Purchase> findByPurchaseId(Long purchaseId);

  @Modifying
  @Query("UPDATE Purchase SET vendor = :vendor WHERE purchaseId =:purchaseId")
  void setVendor(@Param("vendor") Vendor vendor, @Param("purchaseId") Long purchaseId);

  @Modifying
  @Query("UPDATE Purchase p SET p.totalAmt = (SELECT SUM(i.purchasedQty * i.cost) FROM PurchaseItem i WHERE i.purchase = p) " +
          "WHERE p.purchaseId = :purchaseId")
  void setTotalAmt(Long purchaseId);

  @Query(value = "SELECT SUM(i.purchasedQty * i.cost) as totalAmt FROM PurchaseItem i WHERE i.purchase = :purchase")
  BigDecimal getTotalAmt(Purchase purchase);

  @Query(value = "SELECT p FROM Purchase p " +
          "WHERE (p.purchaseId LIKE :purchaseId OR p.vendor.vendorName LIKE %:vendorName% OR " +
          "p.vendor.address LIKE %:address% OR p.vendor.contactNo LIKE %:contactNo%) " +
          "AND p.purchaseStatus IN :purchaseStatus ORDER BY p.purchaseId")
  Page<Purchase> findUnpostedPurchases(
          @RequestParam("purchaseId") Long purchaseId,
          @RequestParam("vendorName") String vendorName,
          @RequestParam("address") String address,
          @RequestParam("contactNo") String contactNo,
          @RequestParam("purchaseStatus") Set<String> purchaseStatus, Pageable pageable);
}
