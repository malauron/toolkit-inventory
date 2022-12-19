package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Menu;
import com.toolkit.inventory.Projection.MenuView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(excerptProjection = MenuView.class)
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Page<Menu> findByMenuNameContainingOrderByMenuName(@RequestParam("menuName") String menuName, Pageable pageable);

}
