package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.PosItemPrice;
import com.toolkit.inventory.Projection.PosItemPriceView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource(excerptProjection = PosItemPriceView.class)
public interface PosItemPriceRepository extends JpaRepository<PosItemPrice, Long> {

    Set<PosItemPrice> findByItem(Item item);

}
