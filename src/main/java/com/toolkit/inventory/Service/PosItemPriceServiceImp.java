package com.toolkit.inventory.Service;

import com.toolkit.inventory.Dto.PosItemPriceDto;
import com.toolkit.inventory.Repository.PosItemPriceRepository;

public class PosItemPriceServiceImp implements PosItemPriceService {

    private PosItemPriceRepository posItemPriceRepository;

    public PosItemPriceServiceImp(
            PosItemPriceRepository posItemPriceRepository
    ){
        this.posItemPriceRepository = posItemPriceRepository;
    }
    @Override
    public PosItemPriceDto save(PosItemPriceDto posItemPriceDto) {
        return null;
    }
}
