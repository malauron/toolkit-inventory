package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.PosSale;
import com.toolkit.inventory.Domain.PosSaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface PosSaleItemRepository extends JpaRepository<PosSaleItem, Long> {

    @Query(value = "SELECT b FROM PosSaleItem  b WHERE b.posSale = :posSale " +
            "ORDER BY b.item.itemName, b.item.itemCode ")
    Set<PosSaleItem> findByPosSaleOrderByItemName(PosSale posSale);

    @Query(value = "SELECT b FROM PosSaleItem  b WHERE b.posSale.posSaleId = :posSaleId " +
            "ORDER BY b.item.itemName, b.barcode ")
    Set<PosSaleItem> findByPosSaleIdOrderByItemName(Long posSaleId);

    @Query(value = "SELECT b FROM PosSaleItem  b WHERE b.posSale = :posSale ORDER BY b.item.itemId")
    Set<PosSaleItem> findByPosSaleOrderByItemId(PosSale posSale);

}
