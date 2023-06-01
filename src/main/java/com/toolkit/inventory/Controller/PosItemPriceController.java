package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Dto.PosItemPriceDto;
import com.toolkit.inventory.Service.PosItemPriceService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/posItemPrices")
    public PosItemPriceDto save(@RequestBody PosItemPriceDto posItemPriceDto) {
        return posItemPriceService.save(posItemPriceDto);
    }
}
