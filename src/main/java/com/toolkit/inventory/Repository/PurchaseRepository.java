package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Purchase;
import com.toolkit.inventory.Domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

  @Modifying
  @Query("UPDATE Purchase SET vendor = :vendor WHERE purchaseId =:purchaseId")
  void setVendor(@Param("vendor") Vendor vendor, @Param("purchaseId") Long purchaseId);

}
