package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.PurchaseDto;
import com.toolkit.inventory.Repository.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class PurchaseServiceImp implements PurchaseService {

  private PurchaseRepository purchaseRepository;

  private PurchaseItemRepository purchaseItemRepository;

  private ItemRepository itemRepository;

  private ItemUomRepository itemUomRepository;

  private ItemCostRepository itemCostRepository;

  private ItemGenericRepository itemGenericRepository;

  private WarehouseRepository warehouseRepository;

  public PurchaseServiceImp(PurchaseRepository purchaseRepository,
                            PurchaseItemRepository purchaseItemRepository,
                            ItemRepository itemRepository,
                            ItemUomRepository itemUomRepository,
                            ItemCostRepository itemCostRepository,
                            ItemGenericRepository itemGenericRepository,
                            WarehouseRepository warehouseRepository) {
    this.purchaseRepository = purchaseRepository;
    this.purchaseItemRepository = purchaseItemRepository;
    this.itemRepository = itemRepository;
    this.itemUomRepository = itemUomRepository;
    this.itemCostRepository = itemCostRepository;
    this.itemGenericRepository = itemGenericRepository;
    this.warehouseRepository = warehouseRepository;
  }

  @Override
  public PurchaseDto getPurchase(Long purchaseId) {

    PurchaseDto purchaseDto = new PurchaseDto();

    Optional<Purchase> purchase = this.purchaseRepository.findById(purchaseId);

    if (purchase.isPresent()) {

      purchaseDto.setPurchaseId(purchase.get().getPurchaseId());
      purchaseDto.setPurchaseStatus(purchase.get().getPurchaseStatus());
      purchaseDto.setTotalAmt(purchase.get().getTotalAmt());
      purchaseDto.setVendor(purchase.get().getVendor());
      purchaseDto.setWarehouse(purchase.get().getWarehouse());
      purchaseDto.setDateCreated(purchase.get().getDateCreated());

      Set<PurchaseItem> purchaseItems = this.purchaseItemRepository
              .findByPurchaseOrderByItemName(purchase.get());

      purchaseDto.setPurchaseItems(purchaseItems);

    }

    return purchaseDto;

  }

  @Override
  @Transactional
  public Purchase save(PurchaseDto purchaseDto) {

    Purchase purchase = new Purchase();

    purchase.setVendor(purchaseDto.getVendor());
    purchase.setPurchaseStatus("Unposted");
    purchase.setTotalAmt(purchaseDto.getTotalAmt());

    Optional<Warehouse> optWhse = this.warehouseRepository.findById(purchaseDto.getWarehouse().getWarehouseId());

    if (optWhse.isPresent()) {

      purchase.setWarehouse(optWhse.get());

    }

    purchaseDto.getPurchaseItems().forEach(purchaseItem -> {

      Item item = null;

      Optional<Item> tmpItem = this.itemRepository.findById(purchaseItem.getItem().getItemId());

      if (tmpItem.isPresent()) {
        item = tmpItem.get();
      }

      ItemUomId itemUomId = new ItemUomId();

      itemUomId.setItemId(item.getItemId());
      itemUomId.setUomId(purchaseItem.getRequiredUom().getUomId());

      Optional<ItemUom> itemUom = itemUomRepository.findById(itemUomId);

      PurchaseItem newPurchaseItem = new PurchaseItem();

      if (itemUom.isPresent()) {

        newPurchaseItem.setBaseQty(itemUom.get().getQuantity());

      } else {

        newPurchaseItem.setBaseQty(new BigDecimal(1));

      }

      newPurchaseItem.setItem(item);
      newPurchaseItem.setItemClass(item.getItemClass());
      newPurchaseItem.setBaseUom(item.getUom());
      newPurchaseItem.setRequiredUom(purchaseItem.getRequiredUom());
      newPurchaseItem.setPurchasedQty(purchaseItem.getPurchasedQty());
      newPurchaseItem.setCost(purchaseItem.getCost());

      purchase.addPurchaseItem(newPurchaseItem);

    });

    this.purchaseRepository.save(purchase);

    return purchase;

  }

  @Override
  @Transactional
  public PurchaseDto setPurchase(PurchaseDto dto) {

    PurchaseDto purchaseDto = new PurchaseDto();

    Long purchaseId = dto.getPurchaseId();

    purchaseDto.setPurchaseId(purchaseId);

    Optional<Purchase> optPurchase = this.purchaseRepository.findByPurchaseId(purchaseId);

    if (optPurchase.isPresent()) {

      Purchase purchase = optPurchase.get();

      purchaseDto.setPurchaseStatus(purchase.getPurchaseStatus());

      if (purchase.getPurchaseStatus().equals("Unposted")) {

        if (dto.getVendor() != null) {

          purchase.setVendor(dto.getVendor());

        }

        if (dto.getWarehouse() != null) {

          purchase.setWarehouse(dto.getWarehouse());

        }

        this.purchaseRepository.save(purchase);

      }

    }

    return purchaseDto;

  }

  @Override
  @Transactional
  public PurchaseDto setPurchaseStatus(PurchaseDto dto) {

    Long purchaseId = dto.getPurchaseId();

    PurchaseDto purchaseDto = new PurchaseDto();

    purchaseDto.setPurchaseId(purchaseId);

    Optional<Purchase> opt = this.purchaseRepository.findByPurchaseId(purchaseId);

    if (opt.isPresent()) {

      Purchase purchase = opt.get();

      String oldStatus = purchase.getPurchaseStatus();
      String newStatus = dto.getPurchaseStatus();

      purchaseDto.setPurchaseStatus(oldStatus);

      if (oldStatus.equals("Unposted")) {

        purchase.setPurchaseStatus(newStatus);

        if (newStatus.equals("Posted")) {

          Optional<Warehouse> tmpWhse = this.warehouseRepository
                                            .findById(purchase.getWarehouse().getWarehouseId());

          Set<PurchaseItem> purchaseItems = this.purchaseItemRepository
                                                .findByPurchaseOrderByItemId(purchase);

          purchaseItems.forEach(purchaseItem -> {

            BigDecimal baseQty;
            BigDecimal requiredQty;
            BigDecimal purchasedQty;
            BigDecimal ttlQty = BigDecimal.ZERO;
            BigDecimal cost = BigDecimal.ZERO;

            if (!purchaseItem.getItemClass().contains("Branded")) {

              baseQty = purchaseItem.getBaseQty();
              purchasedQty = purchaseItem.getPurchasedQty();
              ttlQty = purchasedQty.multiply(baseQty);
//              ttlQty = purchaseItem.getPurchasedQty().multiply(purchaseItem.getBaseQty());
              cost = purchaseItem.getCost().divide( purchaseItem.getBaseQty());

            } else {

              ItemGeneric itemGeneric = this.itemGenericRepository
                                            .findByMainItemOrderBySubItemName(purchaseItem.getItem());

              PurchaseItemGeneric purchaseItemGeneric = new PurchaseItemGeneric();

              Item item = itemGeneric.getSubItem();
              Uom baseUom = itemGeneric.getSubItem().getUom();
              Uom requiredUom = itemGeneric.getRequiredUom();
              requiredQty = itemGeneric.getRequiredQty();
              purchasedQty = purchaseItem.getPurchasedQty();

              purchaseItemGeneric.setPurchaseItem(purchaseItem);
              purchaseItemGeneric.setItem(item);
              purchaseItemGeneric.setBaseUom(baseUom);
              purchaseItemGeneric.setRequiredUom(requiredUom);
              purchaseItemGeneric.setRequiredQty(requiredQty);
              purchaseItemGeneric.setPurchasedQty(purchasedQty);

              ItemUomId itemUomId = new ItemUomId();

              itemUomId.setItemId(item.getItemId());
              itemUomId.setUomId(requiredUom.getUomId());

              Optional<ItemUom> optionalItemUom = this.itemUomRepository.findById(itemUomId);

              if (optionalItemUom.isPresent()) {
                baseQty = optionalItemUom.get().getQuantity();
              }


            }

            this.itemCostRepository.setQtyCost(ttlQty, cost, purchaseItem.getItem(), tmpWhse.get());

          });

        }

        this.purchaseRepository.save(purchase);

      }

    }

    return purchaseDto;

  }



}
