package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.PosSaleDto;
import com.toolkit.inventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class PosSaleServiceImp implements PosSaleService {

    private PosSaleRepository posSaleRepository;

    private PosSaleItemRepository posSaleItemRepository;

    private ItemRepository itemRepository;

    private ItemUomRepository itemUomRepository;

    private ItemCostRepository itemCostRepository;

    private ItemGenericRepository itemGenericRepository;

    private WarehouseRepository warehouseRepository;

    private CustomerRepository customerRepository;

    @Autowired
    public PosSaleServiceImp(PosSaleRepository posSaleRepository,
                             PosSaleItemRepository posSaleItemRepository,
                             ItemRepository itemRepository, ItemUomRepository itemUomRepository,
                             ItemCostRepository itemCostRepository,
                             ItemGenericRepository itemGenericRepository,
                             WarehouseRepository warehouseRepository,
                             CustomerRepository customerRepository) {
        this.posSaleRepository = posSaleRepository;
        this.posSaleItemRepository = posSaleItemRepository;
        this.itemRepository = itemRepository;
        this.itemUomRepository = itemUomRepository;
        this.itemCostRepository = itemCostRepository;
        this.itemGenericRepository = itemGenericRepository;
        this.warehouseRepository = warehouseRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public PosSaleDto getPosSale(Long posSaleId) {

        PosSaleDto posSaleDto =
                new PosSaleDto();

        Optional<PosSale> optRel
                = this.posSaleRepository.findById(posSaleId);

        if (optRel.isPresent()) {
            posSaleDto.setPosSaleId(posSaleId);
            posSaleDto.setWarehouse(optRel.get().getWarehouse());
            posSaleDto.setCustomer(optRel.get().getCustomer());
            posSaleDto.setTotalAmount(optRel.get().getTotalAmount());
            posSaleDto.setSaleStatus(optRel.get().getSaleStatus());
            posSaleDto.setDateCreated(optRel.get().getDateCreated());

            Set<PosSaleItem> posSaleItems =
                    this.posSaleItemRepository
                            .findByPosSaleOrderByItemName(optRel.get());

            posSaleDto.setPosSaleItems(posSaleItems);
        }

        return posSaleDto;

    }

    @Override
    @Transactional
    public PosSale save(PosSaleDto posSaleDto) {

        PosSale newPosSale = new PosSale();

        newPosSale.setTotalAmount(posSaleDto.getTotalAmount());
        newPosSale.setSaleStatus("Unposted");

        Optional<Warehouse> optWhse = this.warehouseRepository.findById(posSaleDto.getWarehouse().getWarehouseId());

        if (optWhse.isPresent()) {
            newPosSale.setWarehouse(optWhse.get());
        }

        Optional<Customer> optCust = this.customerRepository.findById(posSaleDto.getCustomer().getCustomerId());
        newPosSale.setCustomer(optCust.get());

        posSaleDto.getPosSaleItems().forEach(posSaleItem -> {

            Item item = null;

            Optional<Item> optItem = this.itemRepository.findById(posSaleItem.getItem().getItemId());

            if (optItem.isPresent()) {
                item = optItem.get();
            }

            ItemUomId itemUomId = new ItemUomId();

            itemUomId.setItemId(item.getItemId());
            itemUomId.setUomId(posSaleItem.getRequiredUom().getUomId());

            Optional<ItemUom> itemUom = itemUomRepository.findById(itemUomId);

            PosSaleItem newPosSaleItem =
                    new PosSaleItem();

            if (itemUom.isPresent()) {

                newPosSaleItem.setBaseQty(itemUom.get().getQuantity());

            } else {

                newPosSaleItem.setBaseQty(new BigDecimal(1));

            }

            newPosSaleItem.setItem(item);
            newPosSaleItem.setCost(BigDecimal.ZERO);
            newPosSaleItem.setBarcode(posSaleItem.getBarcode());
            newPosSaleItem.setItemClass(item.getItemClass());
            newPosSaleItem.setBaseUom(item.getUom());
            newPosSaleItem.setRequiredUom(posSaleItem.getRequiredUom());
            newPosSaleItem.setReleasedQty(posSaleItem.getReleasedQty());
            newPosSaleItem.setItemPrice(posSaleItem.getItemPrice());
            newPosSaleItem.setTotalAmount(posSaleItem.getTotalAmount());

            newPosSale.addPosSaleItem(newPosSaleItem);

        });

        this.posSaleRepository.save(newPosSale);

        return newPosSale;
    }

    @Override
    @Transactional
    public PosSaleDto setPosSale(PosSaleDto posSaleDto) {

        PosSaleDto newPosSaleDto = new PosSaleDto();

        Long posSaleId = posSaleDto.getPosSaleId();

        newPosSaleDto.setPosSaleId(posSaleId);

        Optional<PosSale> optProd =
                this.posSaleRepository.findByPosSaleId(posSaleId);

        if (optProd.isPresent()) {

            PosSale posSale = optProd.get();

            newPosSaleDto.setSaleStatus(posSale.getSaleStatus());

            if (posSale.getSaleStatus().equals("Unposted")) {
                if (posSaleDto.getWarehouse() != null) {
                    posSale.setWarehouse(posSaleDto.getWarehouse());
                }

                if (posSaleDto.getCustomer() != null) {
                    Optional<Customer> optCust = this.customerRepository.findById(posSaleDto.getCustomer().getCustomerId());
                    posSale.setCustomer(optCust.get());
                }

                this.posSaleRepository.save(posSale);

            } else {
                newPosSaleDto.setErrorDescription("Unable to process your request since this transaction has already been tagged as " +
                        posSale.getSaleStatus());
            }

        }

        return newPosSaleDto;

    }

    @Override
    @Transactional
    public PosSaleDto setSaleStatus(PosSaleDto dto) {

        Long saleId = dto.getPosSaleId();

        PosSaleDto  newPosSaleDto = new PosSaleDto();

        newPosSaleDto.setPosSaleId(saleId);

        Optional<PosSale> optProd = this.posSaleRepository.findByPosSaleId(saleId);

        if (optProd.isPresent()) {

            PosSale posSale = optProd.get();

            String oldStatus = posSale.getSaleStatus();
            String newStatus = dto.getSaleStatus();

            newPosSaleDto.setSaleStatus(oldStatus);

            if (oldStatus.equals("Unposted")) {

                posSale.setSaleStatus(newStatus);

                if (newStatus.equals("Posted")) {

                    Warehouse warehouse = posSale.getWarehouse();

                    Set<PosSaleItem> posSaleItems =
                            this.posSaleItemRepository
                                    .findByPosSaleOrderByItemName(posSale);

                    posSaleItems.forEach(posSaleItem -> {

                        Optional<ItemCost> optItemCost = this.itemCostRepository
                                .findByItemAndWarehouse(posSaleItem.getItem(), warehouse);

                        if (optItemCost.isPresent()) {

                            ItemCost itemCost = optItemCost.get();

                            posSaleItem.setCost(itemCost.getCost());

                            BigDecimal baseQty = posSaleItem.getBaseQty();
                            BigDecimal releasedQty = posSaleItem.getReleasedQty();
                            BigDecimal ttlQty =  releasedQty.multiply(baseQty)
                                                            .multiply(new BigDecimal(-1));

                            this.posSaleItemRepository.save(posSaleItem);
                            this.itemCostRepository.setQty(ttlQty, BigDecimal.ZERO, itemCost.getItemCostId());

                        }

                    });

                }

                this.posSaleRepository.save(posSale);

            } else {
                newPosSaleDto.setErrorDescription("Unable to process your request since this transaction has already been tagged as " +
                        oldStatus);
            }

        }

        return newPosSaleDto;
    }

    @Override
    @Transactional
    public PosSaleDto putPosSaleItem(
            PosSaleItem posSaleItem) {

        PosSaleDto saleDto = new PosSaleDto();

        Long saleId = posSaleItem
                .getPosSale().getPosSaleId();

        saleDto.setPosSaleId(saleId);

        Optional<PosSale> optProd = this.posSaleRepository
                .findByPosSaleId(saleId);

        if (optProd.isPresent()) {

            PosSale posSale = optProd.get();

            saleDto.setSaleStatus(posSale.getSaleStatus());

            if (posSale.getSaleStatus().equals("Unposted")) {

                PosSaleItem newReleasingItem = new PosSaleItem();

                newReleasingItem.setPosSale(posSale);

                if (posSaleItem.getPosSaleItemId() != null) {

                    newReleasingItem.setPosSaleItemId(
                            posSaleItem.getPosSaleItemId());

                }

                Item item = null;

                Optional<Item> tmpItem = this.itemRepository.findById(
                        posSaleItem.getItem().getItemId());

                if (tmpItem.isPresent()) {
                    item = tmpItem.get();
                }

                ItemUomId itemUomId = new ItemUomId();

                itemUomId.setItemId(item.getItemId());
                itemUomId.setUomId(posSaleItem.getRequiredUom().getUomId());

                Optional<ItemUom> itemUom = this.itemUomRepository.findById(itemUomId);

                if (itemUom.isPresent()) {

                    newReleasingItem.setBaseQty(itemUom.get().getQuantity());

                } else {

                    newReleasingItem.setBaseQty(new BigDecimal(1));

                }

                newReleasingItem.setItem(item);
                newReleasingItem.setCost(BigDecimal.ZERO);
                newReleasingItem.setBarcode(posSaleItem.getBarcode());
                newReleasingItem.setBaseUom(item.getUom());
                newReleasingItem.setItemClass(item.getItemClass());
                newReleasingItem.setRequiredUom(posSaleItem.getRequiredUom());
                newReleasingItem.setReleasedQty(posSaleItem.getReleasedQty());
                newReleasingItem.setItemPrice(posSaleItem.getItemPrice());
                newReleasingItem.setTotalAmount((posSaleItem.getTotalAmount()));

                this.posSaleItemRepository.save(newReleasingItem);

                posSale.setTotalAmount(this.posSaleRepository.getTotalAmount(posSale));

                this.posSaleRepository.save(posSale);

                saleDto.setPosSaleItem(newReleasingItem);

            } else {
                saleDto.setErrorDescription("Unable to process your request since this transaction has already been tagged as " +
                        posSale.getSaleStatus());
            }

        }

        return saleDto;

    }

    @Override
    @Transactional
    public PosSaleDto deletePosSaleItem(
            PosSaleItem posSaleItem) {

        PosSaleDto posSaleDto =
                new PosSaleDto();

        Long saleItemId = posSaleItem.getPosSaleItemId();

        Long saleId = posSaleItem.getPosSale().getPosSaleId();

        posSaleDto.setPosSaleId(saleId);

        Optional<PosSale> optProd = this.posSaleRepository.findByPosSaleId(saleId);

        if (optProd.isPresent()) {
            PosSale sale = optProd.get();

            posSaleDto.setSaleStatus(sale.getSaleStatus());

            if (sale.getSaleStatus().equals("Unposted")) {

                this.posSaleItemRepository.deleteById(saleItemId);

                sale.setTotalAmount(this.posSaleRepository
                        .getTotalAmount(sale));
            } else {
                posSaleDto.setErrorDescription("Unable to process your request since " +
                        "this transaction has already been tagged as " +
                        sale.getSaleStatus());
            }
        }

        return posSaleDto;
    }

    @Override
    public Set<PosSaleItem> getPosSaleItems(Long posSaleId) {

        return this.posSaleItemRepository.findByPosSaleIdOrderByItemName(posSaleId);

    }
}
