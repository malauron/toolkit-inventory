package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ButcheryReleasingDto;
import com.toolkit.inventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class ButcheryReleasingServiceImp implements ButcheryReleasingService {

    private ButcheryReleasingRepository butcheryReleasingRepository;

    private ButcheryReleasingItemRepository butcheryReleasingItemRepository;

    private ItemRepository itemRepository;

    private ItemUomRepository itemUomRepository;

    private ItemCostRepository itemCostRepository;

    private ItemGenericRepository itemGenericRepository;

    private WarehouseRepository warehouseRepository;

    private CustomerRepository customerRepository;

    private ButcheryProductionItemRepository butcheryProductionItemRepository;

    @Autowired
    public ButcheryReleasingServiceImp(ButcheryReleasingRepository butcheryReleasingRepository,
                                       ButcheryReleasingItemRepository butcheryReleasingItemRepository,
                                       ItemRepository itemRepository, ItemUomRepository itemUomRepository,
                                       ItemCostRepository itemCostRepository,
                                       ItemGenericRepository itemGenericRepository,
                                       WarehouseRepository warehouseRepository,
                                       CustomerRepository customerRepository,
                                       ButcheryProductionItemRepository butcheryProductionItemRepository) {
        this.butcheryReleasingRepository = butcheryReleasingRepository;
        this.butcheryReleasingItemRepository = butcheryReleasingItemRepository;
        this.itemRepository = itemRepository;
        this.itemUomRepository = itemUomRepository;
        this.itemCostRepository = itemCostRepository;
        this.itemGenericRepository = itemGenericRepository;
        this.warehouseRepository = warehouseRepository;
        this.customerRepository = customerRepository;
        this.butcheryProductionItemRepository = butcheryProductionItemRepository;

    }

    @Override
    public ButcheryReleasingDto getButcheryReleasing(Long butcheryReleasingId) {

        ButcheryReleasingDto butcheryReleasingDto =
                new ButcheryReleasingDto();

        Optional<ButcheryReleasing> optProd
                = this.butcheryReleasingRepository.findById(butcheryReleasingId);

        if (optProd.isPresent()) {
            butcheryReleasingDto.setButcheryReleasingId(butcheryReleasingId);
            butcheryReleasingDto.setWarehouse(optProd.get().getWarehouse());
            butcheryReleasingDto.setCustomer(optProd.get().getCustomer());
            butcheryReleasingDto.setTotalAmount(optProd.get().getTotalAmount());
            butcheryReleasingDto.setReleasingStatus(optProd.get().getReleasingStatus());
            butcheryReleasingDto.setDateCreated(optProd.get().getDateCreated());

            Set<ButcheryReleasingItem> butcheryReleasingItems =
                    this.butcheryReleasingItemRepository
                            .findByButcheryReleasingOrderByItemName(optProd.get());

            butcheryReleasingDto.setButcheryReleasingItems(butcheryReleasingItems);
        }

        return butcheryReleasingDto;

    }

    @Override
    @Transactional
    public ButcheryReleasing save(ButcheryReleasingDto butcheryReleasingDto) {

        ButcheryReleasing newButcheryReleasing = new ButcheryReleasing();

        newButcheryReleasing.setTotalAmount(butcheryReleasingDto.getTotalAmount());
        newButcheryReleasing.setReleasingStatus("Unposted");

        Optional<Warehouse> optWhse = this.warehouseRepository.findById(butcheryReleasingDto.getWarehouse().getWarehouseId());

        if (optWhse.isPresent()) {
            newButcheryReleasing.setWarehouse(optWhse.get());
        }

        Optional<Customer> optCust = this.customerRepository.findById(butcheryReleasingDto.getCustomer().getCustomerId());

        if (optCust.isPresent()) {
            newButcheryReleasing.setCustomer(optCust.get());
        }

        butcheryReleasingDto.getButcheryReleasingItems().forEach(butcheryReleasingItem -> {

            Item item = null;

            Optional<Item> optItem = this.itemRepository.findById(butcheryReleasingItem.getItem().getItemId());

            if (optItem.isPresent()) {
                item = optItem.get();
            }

            ItemUomId itemUomId = new ItemUomId();

            itemUomId.setItemId(item.getItemId());
            itemUomId.setUomId(butcheryReleasingItem.getRequiredUom().getUomId());

            Optional<ItemUom> itemUom = itemUomRepository.findById(itemUomId);

            ButcheryReleasingItem newButcheryReleasingItem =
                    new ButcheryReleasingItem();

            if (itemUom.isPresent()) {

                newButcheryReleasingItem.setBaseQty(itemUom.get().getQuantity());

            } else {

                newButcheryReleasingItem.setBaseQty(new BigDecimal(1));

            }

            newButcheryReleasingItem.setItem(item);
            newButcheryReleasingItem.setCost(BigDecimal.ZERO);
            newButcheryReleasingItem.setBarcode(butcheryReleasingItem.getBarcode());
            newButcheryReleasingItem.setItemClass(item.getItemClass());
            newButcheryReleasingItem.setBaseUom(item.getUom());
            newButcheryReleasingItem.setRequiredUom(butcheryReleasingItem.getRequiredUom());
            newButcheryReleasingItem.setReleasedQty(butcheryReleasingItem.getReleasedQty());
            newButcheryReleasingItem.setItemPrice(butcheryReleasingItem.getItemPrice());
            newButcheryReleasingItem.setTotalAmount(butcheryReleasingItem.getTotalAmount());

            newButcheryReleasing.addButcheryReleasingItem(newButcheryReleasingItem);

        });

        this.butcheryReleasingRepository.save(newButcheryReleasing);

        return newButcheryReleasing;
    }

    @Override
    @Transactional
    public ButcheryReleasingDto setButcheryReleasing(ButcheryReleasingDto butcheryReleasingDto) {

        ButcheryReleasingDto newButcheryReleasingDto = new ButcheryReleasingDto();

        Long butcheryReleasingId = butcheryReleasingDto.getButcheryReleasingId();

        newButcheryReleasingDto.setButcheryReleasingId(butcheryReleasingId);

        Optional<ButcheryReleasing> optProd =
                this.butcheryReleasingRepository.findByButcheryReleasingId(butcheryReleasingId);

        if (optProd.isPresent()) {

            ButcheryReleasing butcheryReleasing = optProd.get();

            newButcheryReleasingDto.setReleasingStatus(butcheryReleasing.getReleasingStatus());

            if (butcheryReleasing.getReleasingStatus().equals("Unposted")) {
                if (butcheryReleasingDto.getWarehouse() != null) {
                    butcheryReleasing.setWarehouse(butcheryReleasingDto.getWarehouse());
                }

                if (butcheryReleasingDto.getCustomer() != null) {
                    butcheryReleasing.setCustomer(butcheryReleasingDto.getCustomer());
                }
            }

            this.butcheryReleasingRepository.save(butcheryReleasing);

        }

        return newButcheryReleasingDto;

    }

    @Override
    @Transactional
    public ButcheryReleasingDto setReleasingStatus(ButcheryReleasingDto dto) {

        Long relId = dto.getButcheryReleasingId();

        ButcheryReleasingDto  newButcheryReleasingDto = new ButcheryReleasingDto();

        newButcheryReleasingDto.setButcheryReleasingId(relId);

        Optional<ButcheryReleasing> optProd = this.butcheryReleasingRepository.findByButcheryReleasingId(relId);

        if (optProd.isPresent()) {

            ButcheryReleasing butcheryReleasing = optProd.get();

            String oldStatus = butcheryReleasing.getReleasingStatus();
            String newStatus = dto.getReleasingStatus();

            newButcheryReleasingDto.setReleasingStatus(oldStatus);

            if (oldStatus.equals("Unposted")) {

                butcheryReleasing.setReleasingStatus(newStatus);

                if (newStatus.equals("Posted")) {

                    Warehouse warehouse = butcheryReleasing.getWarehouse();

                    Set<ButcheryReleasingItem> butcheryReleasingItems =
                            this.butcheryReleasingItemRepository
                                    .findByButcheryReleasingOrderByItemName(butcheryReleasing);

                    butcheryReleasingItems.forEach(butcheryReleasingItem -> {

                        Optional<ItemCost> optItemCost = this.itemCostRepository
                                .findByItemAndWarehouse(butcheryReleasingItem.getItem(), warehouse);

                        if (optItemCost.isPresent()) {

                            ItemCost itemCost = optItemCost.get();

                            butcheryReleasingItem.setCost(itemCost.getCost());

                            BigDecimal baseQty = butcheryReleasingItem.getBaseQty();
                            BigDecimal releasedQty = butcheryReleasingItem.getReleasedQty();
                            BigDecimal ttlQty =  releasedQty.multiply(baseQty)
                                                            .multiply(new BigDecimal(-1));

                            this.butcheryReleasingItemRepository.save(butcheryReleasingItem);
                            this.itemCostRepository.setQty(ttlQty, itemCost.getItemCostId());

                        }

                        Optional<ButcheryProductionItem> optProdItem = this.butcheryProductionItemRepository
                                .findFirstByBarcodeAndItemAndWarehouseAndIsAvailableIsTrue(
                                        butcheryReleasingItem.getBarcode(),
                                        butcheryReleasingItem.getItem(),
                                        butcheryReleasingItem.getButcheryReleasing().getWarehouse()
                                );

                        if (optProdItem.isPresent()) {
                            System.out.println(optProdItem.get().getButcheryProductionItemId());
                            optProdItem.get().setIsAvailable(false);
                            this.butcheryProductionItemRepository.save(optProdItem.get());

                            butcheryReleasingItem.setButcheryProductionItem(optProdItem.get());
                        }

                    });

                }

                this.butcheryReleasingRepository.save(butcheryReleasing);

            }

        }

        return newButcheryReleasingDto;
    }

    @Override
    @Transactional
    public ButcheryReleasingDto putButcheryReleasingItem(
            ButcheryReleasingItem butcheryReleasingItem) {

        ButcheryReleasingDto releasingDto = new ButcheryReleasingDto();

        Long releasingId = butcheryReleasingItem
                .getButcheryReleasing().getButcheryReleasingId();

        releasingDto.setButcheryReleasingId(releasingId);

        Optional<ButcheryReleasing> optProd = this.butcheryReleasingRepository
                .findByButcheryReleasingId(releasingId);

        if (optProd.isPresent()) {

            ButcheryReleasing butcheryReleasing = optProd.get();

            releasingDto.setReleasingStatus(butcheryReleasing.getReleasingStatus());

            ButcheryReleasingItem newReleasingItem = new ButcheryReleasingItem();

            newReleasingItem.setButcheryReleasing(butcheryReleasing);

            if (butcheryReleasing.getReleasingStatus().equals("Unposted")) {

                if (butcheryReleasingItem.getButcheryReleasingItemId() != null) {

                    newReleasingItem.setButcheryReleasingItemId(
                            butcheryReleasingItem.getButcheryReleasingItemId());

                }

                Item item = null;

                Optional<Item> tmpItem = this.itemRepository.findById(
                        butcheryReleasingItem.getItem().getItemId());

                if (tmpItem.isPresent()) {
                    item = tmpItem.get();
                }

                ItemUomId itemUomId = new ItemUomId();

                itemUomId.setItemId(item.getItemId());
                itemUomId.setUomId(butcheryReleasingItem.getRequiredUom().getUomId());

                Optional<ItemUom> itemUom = this.itemUomRepository.findById(itemUomId);

                if (itemUom.isPresent()) {

                    newReleasingItem.setBaseQty(itemUom.get().getQuantity());

                } else {

                    newReleasingItem.setBaseQty(new BigDecimal(1));

                }

                newReleasingItem.setItem(item);
                newReleasingItem.setCost(BigDecimal.ZERO);
                newReleasingItem.setBarcode(butcheryReleasingItem.getBarcode());
                newReleasingItem.setBaseUom(item.getUom());
                newReleasingItem.setItemClass(item.getItemClass());
                newReleasingItem.setRequiredUom(butcheryReleasingItem.getRequiredUom());
                newReleasingItem.setReleasedQty(butcheryReleasingItem.getReleasedQty());
                newReleasingItem.setItemPrice(butcheryReleasingItem.getItemPrice());
                newReleasingItem.setTotalAmount((butcheryReleasingItem.getTotalAmount()));

                this.butcheryReleasingItemRepository.save(newReleasingItem);

                butcheryReleasing.setTotalAmount(this.butcheryReleasingRepository.getTotalAmount(butcheryReleasing));

                this.butcheryReleasingRepository.save(butcheryReleasing);

                releasingDto.setButcheryReleasingItem(newReleasingItem);

            }

        }

        return releasingDto;

    }

    @Override
    @Transactional
    public ButcheryReleasingDto deleteButcheryReleasingItem(
            ButcheryReleasingItem butcheryReleasingItem) {

        ButcheryReleasingDto butcheryReleasingDto =
                new ButcheryReleasingDto();

        Long releasingItemId = butcheryReleasingItem.getButcheryReleasingItemId();

        Long releasingId = butcheryReleasingItem.getButcheryReleasing().getButcheryReleasingId();

        butcheryReleasingDto.setButcheryReleasingId(releasingId);

        Optional<ButcheryReleasing> optProd = this.butcheryReleasingRepository.findByButcheryReleasingId(releasingId);

        if (optProd.isPresent()) {
            ButcheryReleasing releasing = optProd.get();

            butcheryReleasingDto.setReleasingStatus(releasing.getReleasingStatus());

            if (releasing.getReleasingStatus().equals("Unposted")) {

                this.butcheryReleasingItemRepository.deleteById(releasingItemId);

                releasing.setTotalAmount(this.butcheryReleasingRepository
                        .getTotalAmount(releasing));
            }
        }

        return butcheryReleasingDto;
    }
}
