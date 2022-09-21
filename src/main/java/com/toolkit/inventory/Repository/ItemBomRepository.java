package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemBom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface ItemBomRepository extends JpaRepository<ItemBom, Long> {

  @Query(value = "SELECT i FROM ItemBom i WHERE i.mainItem=:item ORDER BY i.subItem.itemName")
  Set<ItemBom> findByMainItemOrderBySubItemName(Item item);

}
