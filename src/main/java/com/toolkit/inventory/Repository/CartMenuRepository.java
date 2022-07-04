package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.CartMenu;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {

}
