package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ItemAddOnContent;
import com.toolkit.inventory.Domain.ItemAddOnDetail;
import com.toolkit.inventory.Projection.ItemAddOnContentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = ItemAddOnContentView.class)
public interface ItemAddOnContentRepository extends JpaRepository<ItemAddOnContent, Long> {
    @Query(value = "SELECT i FROM ItemAddOnContent i WHERE i.itemAddOnDetail = :itemAddOnDetail " +
                    "ORDER BY i.itemAddOnContentId")
    List<ItemAddOnContent> findByItemAddOnDetailOrderById(ItemAddOnDetail itemAddOnDetail);
}
