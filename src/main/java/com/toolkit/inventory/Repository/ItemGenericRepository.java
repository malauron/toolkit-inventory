package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemGeneric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
public interface ItemGenericRepository extends JpaRepository<ItemGeneric, Long> {

    @Query(value = "SELECT i FROM ItemGeneric i WHERE i.mainItem=:item ORDER BY i.subItem.itemName")
    ItemGeneric findByMainItemOrderBySubItemName(Item item);

}
