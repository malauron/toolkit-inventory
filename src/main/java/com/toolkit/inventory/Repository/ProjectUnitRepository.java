package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ProjectUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource()
public interface ProjectUnitRepository extends JpaRepository<ProjectUnit, Long> {

    @Query(value = "SELECT u FROM ProjectUnit u " +
                   "WHERE (u.unitCode LIKE %:searchDesc% " +
                            "OR u.unitDescription LIKE %:searchDesc% " +
                            "OR u.currentContract.client.clientName LIKE %:searchDesc%) " +
                   "ORDER BY u.unitId")
    Page<ProjectUnit> findByCustomParams(
            @RequestParam("searchDesc") String searchDesc,
            Pageable pageable);
}
