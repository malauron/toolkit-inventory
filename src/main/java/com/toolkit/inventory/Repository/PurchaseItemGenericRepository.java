package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.PurchaseItemGeneric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PurchaseItemGenericRepository extends JpaRepository<PurchaseItemGeneric, Long> {
}
