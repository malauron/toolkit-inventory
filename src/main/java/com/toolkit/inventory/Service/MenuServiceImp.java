package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Menu;
import com.toolkit.inventory.Domain.MenuIngredient;
import com.toolkit.inventory.Dto.MenuDto;
import com.toolkit.inventory.Repository.MenuIngredientRepository;
import com.toolkit.inventory.Repository.MenuRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

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
        menuRepository.save(menu);
    }

    @Transactional
    @Override
    public void update(Menu menu) {
        menuRepository.save(menu);
    }

}
