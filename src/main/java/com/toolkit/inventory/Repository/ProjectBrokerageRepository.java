package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ProjectBrokerage;
import com.toolkit.inventory.Domain.Vendor;
import com.toolkit.inventory.Projection.VendorView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource()
public interface ProjectBrokerageRepository extends JpaRepository<ProjectBrokerage, Long> {
  Page<ProjectBrokerage> findByBrokerageNameContainingOrderByBrokerageName(
          @RequestParam("brokerageName") String brokerageName,
          Pageable pageable
  );
}
