package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
