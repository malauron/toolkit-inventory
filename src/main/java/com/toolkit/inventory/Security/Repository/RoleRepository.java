package com.toolkit.inventory.Security.Repository;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Domain.ButcheryProductionItem;
import com.toolkit.inventory.Domain.Uom;
import com.toolkit.inventory.Security.Domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select r.* from roles r " +
                   "inner join user_roles u on r.role_id = u.role_id " +
                   "WHERE u.user_id = :userId order by r.role_name", nativeQuery = true)
    Set<Role> findByUserId(Long userId);

}
