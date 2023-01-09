package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryReleasing;
import com.toolkit.inventory.Domain.PosSale;
import com.toolkit.inventory.Projection.ButcheryReleasingView;
import com.toolkit.inventory.Projection.PosSaleView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(excerptProjection = PosSaleView.class)
public interface PosSaleRepository extends JpaRepository<PosSale, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<PosSale> findByPosSaleId(Long id);

    @Query(value = "SELECT SUM(b.totalAmount) AS totalAmount FROM PosSaleItem b " +
                   "WHERE b.posSale = :posSale")
    BigDecimal getTotalAmount(PosSale posSale);

    @Query(value = "SELECT b FROM PosSale b " +
                   "WHERE (CONCAT(b.posSaleId,'') LIKE %:posSaleId% " +
                   "OR b.warehouse.warehouseName LIKE %:warehouseName%) " +
                   "AND b.saleStatus IN :saleStatus " +
                   "ORDER BY b.posSaleId DESC")
    Page<PosSale> findByCustomParam(
            @RequestParam("posSaleId") String posSaleId,
            @RequestParam("warehouseName") String warehouseName,
            @RequestParam("saleStatus") Set<String> saleStatus,
            Pageable pageable);

}
