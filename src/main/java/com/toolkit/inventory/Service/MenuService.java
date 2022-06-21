package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Menu;
import com.toolkit.inventory.Domain.MenuIngredient;
import com.toolkit.inventory.Dto.MenuDto;

public interface MenuService {
    void save(MenuDto menuDto);
    void update(Menu menu);
    void saveMenuIngredient(MenuIngredient menuIngredient);
}
