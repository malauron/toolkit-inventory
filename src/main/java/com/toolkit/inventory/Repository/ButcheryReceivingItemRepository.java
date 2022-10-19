package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryReceiving;
import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface ButcheryReceivingItemRepository extends JpaRepository<ButcheryReceivingItem, Long> {

    @Query(value = "SELECT b FROM ButcheryReceivingItem  b WHERE b.butcheryReceiving = :butcheryReceiving ORDER BY b.item.itemName")
    Set<ButcheryReceivingItem> findByButcheryReceivingOrderByItemName(ButcheryReceiving butcheryReceiving);

    @Query(value = "SELECT b FROM ButcheryReceivingItem  b WHERE b.butcheryReceiving = :butcheryReceiving ORDER BY b.item.itemId")
    Set<ButcheryReceivingItem> findByButcheryReceivingOrderByItemId(ButcheryReceiving butcheryReceiving);

}
