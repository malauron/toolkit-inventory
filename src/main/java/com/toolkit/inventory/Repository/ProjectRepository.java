package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
