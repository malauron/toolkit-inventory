package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ItemPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long> {

}
