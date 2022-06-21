package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.Menu;
import com.toolkit.inventory.Domain.MenuIngredient;
import com.toolkit.inventory.Dto.MenuDto;
import com.toolkit.inventory.Service.MenuService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class MenuController {

    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/menus")
    public MenuDto save(@RequestBody MenuDto menuDto) {
        menuService.save(menuDto);
        return menuDto;
    }

//    @PutMapping("/menus")
//    public void update(@RequestBody MenuDto menuDto) {
//        menuService.save(menuDto);
//    }

    @PutMapping("/menus")
    public void update(@RequestBody Menu menu) {
        menuService.update(menu);
    }

    @PostMapping("/menus/menuIngredient")
    public void saveMenuIngredient(@RequestBody MenuIngredient menuIngredient) {
        menuService.saveMenuIngredient(menuIngredient);
    }


}
