package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ProjectBroker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource()
public interface ProjectBrokerRepository extends JpaRepository<ProjectBroker, Long> {
  Page<ProjectBroker> findByBrokerNameContainingOrderByBrokerName(
          @RequestParam("brokerName") String brokerName,
          Pageable pageable
  );
}
