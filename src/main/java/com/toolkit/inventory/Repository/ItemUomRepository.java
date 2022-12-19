package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Domain.ItemUomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@RepositoryRestResource
public interface ItemUomRepository extends JpaRepository<ItemUom, ItemUomId> {

//  @Modifying
//  @Query("UPDATE ItemUom x SET x.quantity = :quantity WHERE x.itemUomId=:itemUomId")
//  void setQuantity(@Param("itemUomId") ItemUomId itemUomId, @Param("quantity") BigDecimal quantity);

//  @Query(value = "SELECT i FROM ItemUom i WHERE i.itemUomId=:itemUomId")
//  Set<ItemUom> findByItemUomId(ItemUomId itemUomId);

  @Query(value = "SELECT i FROM ItemUom i WHERE i.item=:item")
  Set<ItemUom> findByItem(Item item);

}
