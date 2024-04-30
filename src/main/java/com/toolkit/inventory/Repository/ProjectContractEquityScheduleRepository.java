package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Project;
import com.toolkit.inventory.Domain.ProjectContract;
import com.toolkit.inventory.Domain.ProjectContractEquitySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface ProjectContractEquityScheduleRepository extends JpaRepository<ProjectContractEquitySchedule, Long> {

    @Query(value = "SELECT p FROM ProjectContractEquitySchedule p " +
                    "WHERE p.contract = :contract " +
                    "ORDER BY p.scheduleId")
    Set<ProjectContractEquitySchedule> findByProjectContract(ProjectContract contract);

}
