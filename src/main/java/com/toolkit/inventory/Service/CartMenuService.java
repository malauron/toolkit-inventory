package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Dto.CartMenuCountDto;
import com.toolkit.inventory.Dto.CartMenuDto;

import java.util.List;

public interface CartMenuService {

    void save(CartMenuDto cartMenuDto);
    void update(CartMenu cartMenu);
    void saveSingleMenu(Long menuId);
    void delete(CartMenu cartMenu);
    void deleteById(Long cartMenuId);
    CartMenuCountDto getCartMenuCount();
}
