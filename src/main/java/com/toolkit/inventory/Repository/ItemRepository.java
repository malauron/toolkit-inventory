package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Projection.ItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = ItemView.class)
public interface ItemRepository extends JpaRepository<Item, Long> {

  Optional<Item> findByItemCode(@RequestParam String itemCode);

  Page<Item> findByItemNameContainingOrderByItemName(
          @RequestParam("itenName") String itemName,
          Pageable pageable);
}
