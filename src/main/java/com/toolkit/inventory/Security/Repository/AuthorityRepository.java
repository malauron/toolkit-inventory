package com.toolkit.inventory.Security.Repository;

import com.toolkit.inventory.Security.Domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
