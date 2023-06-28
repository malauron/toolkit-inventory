package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Domain.ItemUomId;
import com.toolkit.inventory.Projection.ItemUomView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@RepositoryRestResource(excerptProjection = ItemUomView.class)
public interface ItemUomRepository extends JpaRepository<ItemUom, ItemUomId> {

  @Query(value = "SELECT i FROM ItemUom i WHERE i.item.itemId = :itemId AND i.uom.uomName LIKE %:uomName% " +
                 "ORDER BY i.uom.uomName")
  Page<ItemUom> findByItemIdUomName(
          @RequestParam("itemId") Long itemId,
          @RequestParam("uomName") String uomName,
          Pageable pageable);

  @Query(value = "SELECT i FROM ItemUom i WHERE i.item=:item")
  Set<ItemUom> findByItem(Item item);



}
