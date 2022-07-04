package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Domain.CartMenuIngredient;
import com.toolkit.inventory.Dto.CartMenuDto;
import com.toolkit.inventory.Repository.CartMenuRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class CartMenuServiceImp implements  CartMenuService {
    private CartMenuRepository cartMenuRepository;

    public CartMenuServiceImp(CartMenuRepository cartMenuRepository) {
        this.cartMenuRepository = cartMenuRepository;
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
}
