package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ProjectPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface ProjectPaymentRepository extends JpaRepository<ProjectPayment, Long> {


}
