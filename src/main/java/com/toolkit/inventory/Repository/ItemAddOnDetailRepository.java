package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ItemAddOnDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemAddOnDetailRepository extends JpaRepository<ItemAddOnDetail, Long> {
}
