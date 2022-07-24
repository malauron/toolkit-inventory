package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Domain.CartMenuIngredient;
import com.toolkit.inventory.Domain.Menu;
import com.toolkit.inventory.Dto.CartMenuDto;
import com.toolkit.inventory.Repository.CartMenuRepository;
import com.toolkit.inventory.Repository.MenuRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class CartMenuServiceImp implements  CartMenuService {
    private CartMenuRepository cartMenuRepository;
    private MenuRepository menuRepository;

    public CartMenuServiceImp(CartMenuRepository cartMenuRepository, MenuRepository menuRepository) {
        this.cartMenuRepository = cartMenuRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    @Override
    public void save(CartMenuDto cartMenuDto) {
        CartMenu cartMenu = cartMenuDto.getCartMenu();
        Set<CartMenuIngredient> cartMenuIngredients = cartMenuDto.getCartMenuIngredients();
        cartMenuIngredients.forEach( ing -> cartMenu.addIngredient(ing));
        cartMenuRepository.save(cartMenu);
    }

    @Transactional
    @Override
    public void update(CartMenu cartMenu) {
        cartMenuRepository.save(cartMenu);
    }

    @Transactional
    @Override
    public void saveMenu(Long menuId) {
        Optional<Menu> result = this.menuRepository.findById(menuId);
        System.out.println(result.get());
    }
}
