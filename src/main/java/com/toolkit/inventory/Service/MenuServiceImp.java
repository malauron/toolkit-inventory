package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Menu;
import com.toolkit.inventory.Domain.MenuIngredient;
import com.toolkit.inventory.Dto.MenuDto;
import com.toolkit.inventory.Repository.MenuRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

import static java.awt.SystemColor.menu;

@Service
public class MenuServiceImp implements MenuService {

    private MenuRepository menuRepository;

    public MenuServiceImp(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    @Override
    public void save(MenuDto menuDto) {

        Menu menu = menuDto.getMenu();
        Set<MenuIngredient> menuIngredients = menuDto.getMenuIngredient();
        menuIngredients.forEach( ing -> menu.addIngredient(ing));

//        menuDto.getMenuIngredient()
//                .forEach( ing -> menu.addIngredient(ing));


//        menu.getMenuIngredient().forEach(
//                ingredient -> ingredient.setMenu(menu)
//        );
//        menu.getMenuIngredient().forEach(
//                ingredient -> dispaly(ingredient)
//                );
        menuRepository.save(menu);
    }

//    private void dispaly(MenuIngredient ingredient) {
//
//        System.out.println(ingredient.toString());
//        System.out.println(ingredient.getMenu().toString());
//        System.out.println(ingredient.getItem().toString());
//        System.out.println(ingredient.getRequiredUom().toString());
//        System.out.println(ingredient.getRequiredQty().toString());
//
//    }

    @Transactional
    @Override
    public void update(MenuDto menuDto) {

        Menu menu = menuDto.getMenu();
        Set<MenuIngredient> menuIngredients = menuDto.getMenuIngredient();
        menuIngredients.forEach( ing -> menu.addIngredient(ing));
        menuRepository.save(menu);
    }
}
