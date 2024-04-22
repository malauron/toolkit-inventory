package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Project;
import com.toolkit.inventory.Domain.ProjectParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProjectParameterRepository extends JpaRepository<ProjectParameter, Long> {

}
