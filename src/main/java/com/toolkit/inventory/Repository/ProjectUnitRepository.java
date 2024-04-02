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

    //The JDBC driver is not parsing the query correctly, hence the escape quotes.
    @Query(value = "SELECT `a`.* FROM `project_units` as `a` " +
                   "LEFT OUTER JOIN `project_contracts` as `b` ON `a`.`current_contract_id` = `b`.`contract_id` " +
                   "LEFT OUTER JOIN `project_clients` as `c` ON `b`.`client_id` = `c`.`client_id` " +
                   "WHERE `a`.`unit_code` LIKE %:searchDesc% " +
                   "OR `a`.`unit_description` LIKE %:searchDesc% " +
                   "OR `c`.`client_name` LIKE %:searchDesc% ", nativeQuery = true)
    Page<ProjectUnit> findByCustomParams(
            @RequestParam("searchDesc") String searchDesc,
            Pageable pageable);
}
