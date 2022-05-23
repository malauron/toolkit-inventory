package com.toolkit.inventory.Service;

import com.toolkit.inventory.Dto.MenuDto;

public interface MenuService {
    void save(MenuDto menuDto);
    void update(MenuDto menuDto);
}
