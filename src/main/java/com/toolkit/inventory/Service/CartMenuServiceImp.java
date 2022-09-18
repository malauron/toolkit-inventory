package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Domain.CartMenuIngredient;
import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Domain.Menu;
import com.toolkit.inventory.Dto.CartMenuCountDto;
import com.toolkit.inventory.Dto.CartMenuDto;
import com.toolkit.inventory.Repository.CartMenuRepository;
import com.toolkit.inventory.Repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class CartMenuServiceImp implements  CartMenuService {

    @Autowired
    private CartMenuRepository cartMenuRepository;

    @Autowired
    private MenuRepository menuRepository;

//    @Autowired
//    private ItemUomRepository itemUomRepository;

//    public CartMenuServiceImp(CartMenuRepository cartMenuRepository, MenuRepository menuRepository) {
//        this.cartMenuRepository = cartMenuRepository;
//        this.menuRepository = menuRepository;
//    }

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
    public void saveSingleMenu(Long menuId) {
        Optional<Menu> result = this.menuRepository.findById(menuId);

        Menu menu = null;

        if (result.isPresent()) {
            menu = result.get();
        } else {

        }

        CartMenu cartMenu = new CartMenu();
        cartMenu.setMenu(menu);
        cartMenu.setOrderQty(new BigDecimal(1));
        cartMenu.setPrice(new BigDecimal(0));
        cartMenu.setLineTotal(new BigDecimal(0));

        menu.getMenuIngredient().forEach(ing -> {
            CartMenuIngredient cartMenuIngredient = new CartMenuIngredient();
//            ItemUomId itemUomId = new ItemUomId();
//
//            itemUomId.setItem(ing.getItem());
//            itemUomId.setUom(ing.getRequiredUom());

            //Getting the base quantity of the required UOM.
//            Optional<ItemUom> itemUom = itemUomRepository.findById(itemUomId);
            Optional<ItemUom> itemUom = null;

            cartMenuIngredient.setItem(ing.getItem());
            cartMenuIngredient.setBaseUom(ing.getItem().getUom());
            cartMenuIngredient.setBaseQty(itemUom.get().getQuantity());
            cartMenuIngredient.setRequiredUom(ing.getRequiredUom());
            cartMenuIngredient.setRequiredQty(ing.getRequiredQty());
            cartMenuIngredient.setOrderedQty(new BigDecimal(1));
            cartMenuIngredient.setMenuIngredientId(ing.getMenuIngredientId());

            cartMenu.addIngredient(cartMenuIngredient);

        });


        this.cartMenuRepository.save(cartMenu);
    }

    @Override
    @Transactional
    public void delete(CartMenu cartMenu) {
        this.cartMenuRepository.delete(cartMenu);
    }

    @Override
    @Transactional
    public void deleteById(Long cartMenuId) {
        this.cartMenuRepository.deleteById(cartMenuId);
    }

    @Override()
    public CartMenuCountDto getCartMenuCount() {
        CartMenuCountDto cartMenuCountDto = new CartMenuCountDto();
        cartMenuCountDto.setCartMenuCount(this.cartMenuRepository.count());
        return cartMenuCountDto;
    }
}
