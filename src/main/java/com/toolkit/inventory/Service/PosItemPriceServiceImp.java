package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.PosItemPriceDto;
import com.toolkit.inventory.Projection.PosItemPriceView;
import com.toolkit.inventory.Repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PosItemPriceServiceImp implements PosItemPriceService {

    private PosItemPriceRepository posItemPriceRepository;
    private PosItemPriceLevelRepository posItemPriceLevelRepository;
    private ItemRepository itemRepository;
    private WarehouseRepository warehouseRepository;
    private CustomerGroupRepository customerGroupRepository;

    public PosItemPriceServiceImp(
            PosItemPriceRepository posItemPriceRepository,
            PosItemPriceLevelRepository posItemPriceLevelRepository,
            ItemRepository itemRepository,
            WarehouseRepository warehouseRepository,
            CustomerGroupRepository customerGroupRepository
    ){
        this.posItemPriceRepository = posItemPriceRepository;
        this.posItemPriceLevelRepository = posItemPriceLevelRepository;
        this.itemRepository = itemRepository;
        this.warehouseRepository = warehouseRepository;
        this.customerGroupRepository = customerGroupRepository;
    }

    @Override
    public PosItemPriceDto save(PosItemPriceDto posItemPriceDto) {

        PosItemPrice posItemPrice;

        if (posItemPriceDto.getPosItemPriceId() > 0) {

            Optional<PosItemPrice> optionalPosItemPrice = this.posItemPriceRepository
                                                            .findById(posItemPriceDto.getPosItemPriceId());
            posItemPrice = optionalPosItemPrice.get();
            posItemPrice.setDefaultPrice(posItemPriceDto.getDefaultPrice());

            this.posItemPriceRepository.save(posItemPrice);

        } else {

            posItemPrice = new PosItemPrice();

            Optional<Item> optionalItem =  this.itemRepository
                    .findById(posItemPriceDto.getItem().getItemId());
            posItemPrice.setItem(optionalItem.get());

            Optional<Warehouse> optionalWarehouse = this.warehouseRepository
                    .findByWarehouseId(posItemPriceDto.getWarehouse().getWarehouseId());
            posItemPrice.setWarehouse(optionalWarehouse.get());

            posItemPrice.setDefaultPrice(posItemPriceDto.getDefaultPrice());

            try {

                this.posItemPriceRepository.save(posItemPrice);

            } catch (DataIntegrityViolationException e){

                Optional<PosItemPriceView> optionalPosItemPrice = this.posItemPriceRepository
                                                                        .findByWarehouseIdAndItemId(
                                                                        posItemPrice.getWarehouse().getWarehouseId(),
                                                                        posItemPrice.getItem().getItemId());
                posItemPrice.setPosItemPriceId(optionalPosItemPrice.get().getPosItemPriceId());

                this.posItemPriceRepository.save(posItemPrice);

            };

            posItemPriceDto.setPosItemPriceId(posItemPrice.getPosItemPriceId());

        }

        posItemPriceDto.setItem(posItemPrice.getItem());
        posItemPriceDto.setWarehouse(posItemPrice.getWarehouse());

        Set<PosItemPriceLevel> posItemPriceLevels = new HashSet<>();

        posItemPriceDto.getPosItemPriceLevels().forEach(priceLevelDto -> {

            PosItemPriceLevel posItemPriceLevel;

            if (priceLevelDto.getPosItemPriceLevelId() > 0) {

                Optional<PosItemPriceLevel> optPriceLevel = this.posItemPriceLevelRepository
                                                                    .findById(priceLevelDto.getPosItemPriceLevelId());

                posItemPriceLevel = optPriceLevel.get();
                posItemPriceLevel.setPrice(priceLevelDto.getPrice());

                this.posItemPriceLevelRepository.save(posItemPriceLevel);

            } else {

                posItemPriceLevel = new PosItemPriceLevel();
                posItemPriceLevel.setPosItemPrice(posItemPrice);

                Optional<CustomerGroup> optCustGrp = this.customerGroupRepository
                                                            .findById(priceLevelDto.getCustomerGroup().getCustomerGroupId());
                posItemPriceLevel.setCustomerGroup(optCustGrp.get());
                posItemPriceLevel.setPrice(priceLevelDto.getPrice());

                System.out.println(posItemPriceLevel);

                try {

                    this.posItemPriceLevelRepository.save(posItemPriceLevel);

                } catch (DataIntegrityViolationException e) {

                    Optional<PosItemPriceLevel> optPriceLevel = this.posItemPriceLevelRepository
                                                                        .findByPosItemPriceIdAndCustomerGroupId(
                                                                                posItemPriceLevel.getPosItemPrice().getPosItemPriceId(),
                                                                                posItemPriceLevel.getCustomerGroup().getCustomerGroupId()
                                                                        );
                    posItemPriceLevel.setPosItemPriceLevelId(optPriceLevel.get().getPosItemPriceLevelId());

                    this.posItemPriceLevelRepository.save(posItemPriceLevel);

                }

            }

            posItemPriceLevels.add(posItemPriceLevel);

//            posItemPriceDto.getPosItemPriceLevels().add(posItemPriceLevel);

        });

        posItemPriceDto.setPosItemPriceLevels(posItemPriceLevels);

        return posItemPriceDto;
    }

}
