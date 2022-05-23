package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Uom;
import com.toolkit.inventory.Projection.UomView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = UomView.class)
public interface UomRepository  extends JpaRepository<Uom, Long> {
}
