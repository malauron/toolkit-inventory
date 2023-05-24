package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Service.PosItemPriceService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class PosItemPriceController {

    private PosItemPriceService posItemPriceService;

    public PosItemPriceController(
            PosItemPriceService posItemPriceService
    ){
        this.posItemPriceService = posItemPriceService;
    }
}
