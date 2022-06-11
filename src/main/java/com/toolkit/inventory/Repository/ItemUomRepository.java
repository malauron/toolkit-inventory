package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Domain.ItemUomId;
import com.toolkit.inventory.Projection.ItemUomView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigDecimal;
import java.util.List;

//@CrossOrigin
@RepositoryRestResource(excerptProjection = ItemUomView.class)
public interface ItemUomRepository extends JpaRepository<ItemUom, ItemUomId> {

  @Modifying
  @Query("UPDATE ItemUom x SET x.quantity = :quantity WHERE x.itemUomId=:itemUomId")
  void setQuantity(@Param("itemUomId") ItemUomId itemUomId, @Param("quantity") BigDecimal quantity);

  @Query(value = "SELECT * FROM item_uoms where item_id =:itemId", nativeQuery = true)
  List<ItemUom>  findByItemId(@Param("itemId") BigDecimal itemid);

}
