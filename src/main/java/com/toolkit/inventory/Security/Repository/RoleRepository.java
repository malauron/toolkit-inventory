package com.toolkit.inventory.Security.Repository;

import com.toolkit.inventory.Security.Domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
