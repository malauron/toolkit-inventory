package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryReceiving;
import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import com.toolkit.inventory.Projection.ButcheryReceivingItemView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource
public interface ButcheryReceivingItemRepository extends JpaRepository<ButcheryReceivingItem, Long> {

    @Query(value = "SELECT b FROM ButcheryReceivingItem  b WHERE b.butcheryReceiving = :butcheryReceiving ORDER BY b.item.itemName")
    Set<ButcheryReceivingItem> findByButcheryReceivingOrderByItemName(ButcheryReceiving butcheryReceiving);

    @Query(value = "SELECT b FROM ButcheryReceivingItem  b WHERE b.butcheryReceiving = :butcheryReceiving ORDER BY b.item.itemId")
    Set<ButcheryReceivingItem> findByButcheryReceivingOrderByItemId(ButcheryReceiving butcheryReceiving);

    @Query(value = "SELECT b FROM ButcheryReceivingItem  b WHERE b.butcheryReceivingItemId = :butcheryReceivingItemId " +
            "AND b.isAvailable=TRUE")
    Optional<ButcheryReceivingItemView> findByIdAndIsAvailable(Long butcheryReceivingItemId);

//    @Modifying
//    @Query(value = "UPDATE ButcheryReceivingItem b SET b.usedQty = b.usedQty + :usedQty, " +
//            "b.isAvailable = CASE WHEN ((b.usedQty + :usedQty) > b.receivedQty) THEN FALSE ELSE b.isAvailable END " +
//            "WHERE b.butcheryReceivingItemId = :id")
//    void setUsedQty(BigDecimal usedQty, Long id);


    @Modifying
    @Query(value = "UPDATE ButcheryReceivingItem b SET b.usedQty = b.usedQty + :usedQty, " +
            "b.isAvailable = CASE WHEN (b.usedQty >= b.receivedQty) THEN FALSE ELSE b.isAvailable END " +
            "WHERE b.butcheryReceivingItemId = :id")
    void setUsedQty(BigDecimal usedQty, Long id);
}
