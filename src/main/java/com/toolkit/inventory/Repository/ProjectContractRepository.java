package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ProjectContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface ProjectContractRepository extends JpaRepository<ProjectContract, Long> {

    @Query(value = "SELECT p FROM ProjectContract p " +
                    "WHERE p.isCancelled = false " +
                    "AND p.ttlBalance > 0 AND p.client.clientId = :clientId " +
                    "ORDER BY p.contractId")
    Set<ProjectContract> getActiveContractsByClientId(Long clientId);
}
