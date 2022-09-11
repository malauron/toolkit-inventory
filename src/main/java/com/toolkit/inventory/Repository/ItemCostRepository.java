package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemCost;
import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.util.Optional;

@CrossOrigin
@RepositoryRestResource
public interface ItemCostRepository extends JpaRepository<ItemCost, Long> {

    @Modifying
    @Query(value = "UPDATE ItemCost i SET i.qty = i.qty + :qty, " +
                    "i.cost = CASE WHEN (i.cost > 0) THEN ((i.cost + :cost)/2) ELSE (:cost) END " +
                    "WHERE i.item = :item AND i.warehouse = :warehouse")
    void setQtyCost(BigDecimal qty, BigDecimal cost, Item item, Warehouse warehouse);

    @Modifying
    @Query(value = "UPDATE ItemCost i SET i.qty = i.qty + :qty WHERE i.itemCostId = :itemCostId")
    void setQty(BigDecimal qty, Long itemCostId);

    Optional<ItemCost> findByItemAndWarehouse(Item item, Warehouse warehouse);

}
