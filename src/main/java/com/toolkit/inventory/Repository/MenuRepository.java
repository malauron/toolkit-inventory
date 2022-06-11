package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
