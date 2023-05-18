package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.PosItemPriceLevel;
import com.toolkit.inventory.Projection.PosItemPriceLevelView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = PosItemPriceLevelView.class)
public interface PosItemPriceLevelRepository extends JpaRepository<PosItemPriceLevel, Long> {
}
