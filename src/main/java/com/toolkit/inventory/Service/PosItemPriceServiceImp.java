package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.PosItemPrice;
import com.toolkit.inventory.Domain.Warehouse;
import com.toolkit.inventory.Dto.PosItemPriceDto;
import com.toolkit.inventory.Projection.PosItemPriceView;
import com.toolkit.inventory.Repository.ItemRepository;
import com.toolkit.inventory.Repository.PosItemPriceRepository;
import com.toolkit.inventory.Repository.WarehouseRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PosItemPriceServiceImp implements PosItemPriceService {

    private PosItemPriceRepository posItemPriceRepository;
    private ItemRepository itemRepository;
    private WarehouseRepository warehouseRepository;

    public PosItemPriceServiceImp(
            PosItemPriceRepository posItemPriceRepository,
            ItemRepository itemRepository,
            WarehouseRepository warehouseRepository
    ){
        this.posItemPriceRepository = posItemPriceRepository;
        this.itemRepository = itemRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public PosItemPriceDto save(PosItemPriceDto posItemPriceDto) {

        Optional<Item> optionalItem =  this.itemRepository
                .findById(posItemPriceDto.getItem().getItemId());
        posItemPriceDto.setItem(optionalItem.get());

        Optional<Warehouse> optionalWarehouse = this.warehouseRepository
                .findByWarehouseId(posItemPriceDto.getWarehouse().getWarehouseId());
        posItemPriceDto.setWarehouse(optionalWarehouse.get());

        if (posItemPriceDto.getPosItemPriceId() > 0) {

        } else {

            PosItemPrice posItemPrice = new PosItemPrice();
            posItemPrice.setItem(optionalItem.get());
            posItemPrice.setWarehouse(optionalWarehouse.get());
            posItemPrice.setDefaultPrice(posItemPriceDto.getDefaultPrice());

            try {

                this.savePosItemPrice(posItemPrice);

                posItemPriceDto.setPosItemPriceId(posItemPrice.getPosItemPriceId());

            } catch (DataIntegrityViolationException e){
                Optional<PosItemPriceView> optionalPosItemPrice = this.posItemPriceRepository
                                                                .findByWarehouseIdAndItemId(
                                                                        posItemPrice.getWarehouse().getWarehouseId(),
                                                                        posItemPrice.getItem().getItemId());

                posItemPrice.setPosItemPriceId(optionalPosItemPrice.get().getPosItemPriceId());

                this.savePosItemPrice(posItemPrice);

                posItemPriceDto.setPosItemPriceId(posItemPrice.getPosItemPriceId());

            };
        }
        return posItemPriceDto;
    }

    private void savePosItemPrice(PosItemPrice posItemPrice) throws DataIntegrityViolationException {
        this.posItemPriceRepository.saveAndFlush(posItemPrice);
    }
}
