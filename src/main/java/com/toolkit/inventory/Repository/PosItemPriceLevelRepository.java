package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.PosItemPriceLevel;
import com.toolkit.inventory.Projection.PosItemPriceLevelView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = PosItemPriceLevelView.class)
public interface PosItemPriceLevelRepository extends JpaRepository<PosItemPriceLevel, Long> {

    @Query(value = "SELECT p FROM PosItemPriceLevel p " +
            "WHERE p.posItemPrice.posItemPriceId = :posItemPriceId " +
            "AND p.customerGroup.customerGroupId = :customerGroupId")
    Optional<PosItemPriceLevel> findByPosItemPriceIdAndCustomerGroupId(
            @RequestParam Long posItemPriceId,
            @RequestParam Long customerGroupId
    );

}
