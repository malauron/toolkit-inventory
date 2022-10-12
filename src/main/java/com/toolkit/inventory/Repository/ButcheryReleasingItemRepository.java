package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryReleasing;
import com.toolkit.inventory.Domain.ButcheryReleasingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface ButcheryReleasingItemRepository extends JpaRepository<ButcheryReleasingItem, Long> {

    @Query(value = "SELECT b FROM ButcheryReleasingItem  b WHERE b.butcheryReleasing = :butcheryReleasing ORDER BY b.item.itemName")
    Set<ButcheryReleasingItem> findByButcheryReleasingOrderByItemName(ButcheryReleasing butcheryReleasing);

    @Query(value = "SELECT b FROM ButcheryReleasingItem  b WHERE b.butcheryReleasing = :butcheryReleasing ORDER BY b.item.itemId")
    Set<ButcheryReleasingItem> findByButcheryReleasingOrderByItemId(ButcheryReleasing butcheryReleasing);

}
