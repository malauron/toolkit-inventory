package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ProjectContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProjectContractRepository extends JpaRepository<ProjectContract, Long> {

}
