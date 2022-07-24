package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Dto.CartMenuDto;

public interface CartMenuService {
    void save(CartMenuDto cartMenuDto);
    void update(CartMenu cartMenu);
    void saveMenu(Long menuId);
}
