package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ItemAddOnDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface ItemAddOnDetailRepository extends JpaRepository<ItemAddOnDetail, Long> {

    @Query(value = "SELECT i FROM ItemAddOnDetail i WHERE i.item.itemId = :itemId ")
    Set<ItemAddOnDetail> findByItemId(Long itemId);

}
