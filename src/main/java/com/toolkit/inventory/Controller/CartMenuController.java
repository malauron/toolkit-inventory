package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Dto.CartMenuDto;
import com.toolkit.inventory.Service.CartMenuService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CartMenuController {
    private CartMenuService cartMenuService;

    public CartMenuController(CartMenuService cartMenuService) {
        this.cartMenuService = cartMenuService;
    }

    @PostMapping("/cartMenus")
    public CartMenuDto save(@RequestBody CartMenuDto cartMenuDto) {
        this.cartMenuService.save(cartMenuDto);
        return cartMenuDto;
    }

    @PutMapping("/cartMenus")
    public void update(@RequestBody CartMenu cartMenu) {
        this.cartMenuService.update(cartMenu);
    }
}
