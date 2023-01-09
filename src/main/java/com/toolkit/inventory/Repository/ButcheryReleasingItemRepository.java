package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryReleasing;
import com.toolkit.inventory.Domain.ButcheryReleasingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@RepositoryRestResource
public interface ButcheryReleasingItemRepository extends JpaRepository<ButcheryReleasingItem, Long> {

    @Query(value = "SELECT b FROM ButcheryReleasingItem  b WHERE b.butcheryReleasing = :butcheryReleasing " +
            "ORDER BY b.item.itemName, b.item.itemCode ")
    Set<ButcheryReleasingItem> findByButcheryReleasingOrderByItemName(ButcheryReleasing butcheryReleasing);

    @Query(value = "SELECT b FROM ButcheryReleasingItem  b WHERE b.butcheryReleasing.butcheryReleasingId = :butcheryReleasingId " +
            "ORDER BY b.item.itemName, b.barcode ")
    Set<ButcheryReleasingItem> findByButcheryReleasingIdOrderByItemName(Long butcheryReleasingId);

    @Query(value = "SELECT b FROM ButcheryReleasingItem  b WHERE b.butcheryReleasing = :butcheryReleasing ORDER BY b.item.itemId")
    Set<ButcheryReleasingItem> findByButcheryReleasingOrderByItemId(ButcheryReleasing butcheryReleasing);

}
