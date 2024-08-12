package com.toolkit.inventory.Security.Repository;

import com.toolkit.inventory.Security.Domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
