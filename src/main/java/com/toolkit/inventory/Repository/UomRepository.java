package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Uom;
import com.toolkit.inventory.Projection.UomView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(excerptProjection = UomView.class)
public interface UomRepository  extends JpaRepository<Uom, Long> {
  @Query(value = "select * from uoms order by uom_name", nativeQuery = true)
  List<Uom> findAllUoms();
}
