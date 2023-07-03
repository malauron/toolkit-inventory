package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ItemAddOnContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemAddOnContentRepository extends JpaRepository<ItemAddOnContent, Long> {
}
