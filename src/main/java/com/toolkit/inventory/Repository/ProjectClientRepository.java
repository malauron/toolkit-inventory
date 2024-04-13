package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ProjectClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource()
public interface ProjectClientRepository extends JpaRepository<ProjectClient, Long> {
  Page<ProjectClient> findByClientNameContainingOrderByClientName(
          @RequestParam("clientName") String clientName,
          Pageable pageable
  );
}
