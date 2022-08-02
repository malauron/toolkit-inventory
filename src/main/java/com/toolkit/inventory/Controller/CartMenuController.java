package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Domain.Menu;
import com.toolkit.inventory.Dto.CartMenuDto;
import com.toolkit.inventory.Service.CartMenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CartMenuController {
    private CartMenuService cartMenuService;

    public CartMenuController(CartMenuService cartMenuService) {
        this.cartMenuService = cartMenuService;
    }

//    @GetMapping("/cartMenus")
//    public List<CartMenu> getAllMenus() {
//        return this.cartMenuService.findAllCartMenus();
//    }

    @PostMapping("/cartMenus")
    public CartMenuDto save(@RequestBody CartMenuDto cartMenuDto) {
        this.cartMenuService.save(cartMenuDto);
        return cartMenuDto;
    }

    @PostMapping("/cartSingleMenu")
    public void save(@RequestBody Menu menu) {
        this.cartMenuService.saveSingleMenu(menu.getMenuId());
    }

    @PutMapping("/cartMenus")
    public void update(@RequestBody CartMenu cartMenu) {
        this.cartMenuService.update(cartMenu);
    }

    @DeleteMapping("/cartMenus")
    public void delete(@RequestBody CartMenu cartMenu) {
        this.cartMenuService.delete(cartMenu);
    }
}
