package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.PurchaseDto;
import com.toolkit.inventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class PurchaseServiceImp implements PurchaseService {

  @Autowired
  private PurchaseRepository purchaseRepository;

  @Autowired
  private PurchaseItemRepository purchaseItemRepository;

  @Autowired
  private ItemUomRepository itemUomRepository;

  @Autowired
  private ItemCostRepository itemCostRepository;

  @Autowired
  private WarehouseRepository warehouseRepository;

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

    purchaseDto.getPurchaseItems().forEach(item -> {

      PurchaseItem purchaseItem = new PurchaseItem();

      ItemUomId itemUomId = new ItemUomId();

      itemUomId.setItem(item.getItem());
      itemUomId.setUom(item.getRequiredUom());

      Optional<ItemUom> itemUom = itemUomRepository.findById(itemUomId);

      if (itemUom.isPresent()) {

        purchaseItem.setBaseQty(itemUom.get().getQuantity());

      } else {

        purchaseItem.setBaseQty(new BigDecimal(1));

      }

      purchaseItem.setItem(item.getItem());
      purchaseItem.setBaseUom(item.getItem().getUom());
      purchaseItem.setRequiredUom(item.getRequiredUom());
      purchaseItem.setPurchasedQty(item.getPurchasedQty());
      purchaseItem.setCost(item.getCost());

      purchase.addPurchaseItem(purchaseItem);

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

          Optional<Warehouse> tmpWhse = this.warehouseRepository.findById(purchase.getWarehouse().getWarehouseId());

          Set<PurchaseItem> purchaseItems = this.purchaseItemRepository.findByPurchaseOrderByItemId(purchase);

          purchaseItems.forEach(purchaseItem -> {

            BigDecimal qty = purchaseItem.getPurchasedQty().multiply(purchaseItem.getBaseQty());
            BigDecimal cost = purchaseItem.getCost().divide( purchaseItem.getBaseQty());

            this.itemCostRepository.setQtyCost(qty, cost, purchaseItem.getItem(), tmpWhse.get());

          });

        }

        this.purchaseRepository.save(purchase);

      }

    }

    return purchaseDto;

  }
}
