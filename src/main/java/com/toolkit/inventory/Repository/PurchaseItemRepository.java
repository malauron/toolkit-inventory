package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Purchase;
import com.toolkit.inventory.Domain.PurchaseItem;
import com.toolkit.inventory.Projection.PurchaseItemView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource(excerptProjection = PurchaseItemView.class)
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

    Set<PurchaseItem> findByPurchaseOrderByPurchaseItemId(Purchase purchase);

}
