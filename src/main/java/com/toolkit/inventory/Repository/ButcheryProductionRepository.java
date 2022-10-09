package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Projection.ButcheryProductionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.LockModeType;
import java.util.Optional;

@RepositoryRestResource(excerptProjection = ButcheryProductionView.class)
public interface ButcheryProductionRepository extends JpaRepository<ButcheryProduction, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<ButcheryProduction> findByButcheryProductionId(Long id);

}
