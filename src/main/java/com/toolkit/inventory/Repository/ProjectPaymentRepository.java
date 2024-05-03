package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ProjectPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource()
public interface ProjectPaymentRepository extends JpaRepository<ProjectPayment, Long> {

    @Query(value = "SELECT p FROM ProjectPayment p " +
                   "WHERE CONCAT(p.paymentId, '') LIKE %:searchDesc% " +
                   "OR p.client.clientName LIKE %:searchDesc% " +
                   "OR p.referenceNo LIKE %:searchDesc% " +
                   "ORDER BY p.paymentId DESC")
    Page<ProjectPayment> findByCustomParam(
            @RequestParam("searchDesc") String searchDesc,
            Pageable pageable
    );
}
