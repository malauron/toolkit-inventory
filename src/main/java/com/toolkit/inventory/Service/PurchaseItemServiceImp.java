package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Domain.ItemUomId;
import com.toolkit.inventory.Domain.Purchase;
import com.toolkit.inventory.Domain.PurchaseItem;
import com.toolkit.inventory.Dto.PurchaseDto;
import com.toolkit.inventory.Repository.ItemUomRepository;
import com.toolkit.inventory.Repository.PurchaseItemRepository;
import com.toolkit.inventory.Repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PurchaseItemServiceImp implements PurchaseItemService {

  @Autowired
  private PurchaseRepository purchaseRepository;

  @Autowired
  PurchaseItemRepository purchaseItemRepository;

  @Autowired
  private ItemUomRepository itemUomRepository;

  @Transactional
  @Override
  public PurchaseDto putPurchaseItem(PurchaseItem item) {

    PurchaseDto purchaseDto = new PurchaseDto();

    Long purchaseId = item.getPurchase().getPurchaseId();

    purchaseDto.setPurchaseId(purchaseId);

    Optional<Purchase> optPurchase = this.purchaseRepository.findByPurchaseId(purchaseId);

    if (optPurchase.isPresent()) {

      Purchase purchase = optPurchase.get();

      purchaseDto.setPurchaseStatus(purchase.getPurchaseStatus());

      PurchaseItem newItem = new PurchaseItem();

      newItem.setPurchase(purchase);

      if (purchase.getPurchaseStatus().equals("Unposted")) {

        if (item.getPurchaseItemId() != null) {

          newItem.setPurchaseItemId(item.getPurchaseItemId());

        }

        ItemUomId itemUomId = new ItemUomId();

        itemUomId.setItemId(item.getItem().getItemId());
        itemUomId.setUomId(item.getRequiredUom().getUomId());

        Optional<ItemUom> itemUom = this.itemUomRepository.findById(itemUomId);

        if (itemUom.isPresent()) {

          newItem.setBaseQty(itemUom.get().getQuantity());

        } else {

          newItem.setBaseQty(new BigDecimal(1));

        }

        newItem.setItem(item.getItem());
        newItem.setBaseUom(item.getItem().getUom());
        newItem.setRequiredUom(item.getRequiredUom());
        newItem.setPurchasedQty(item.getPurchasedQty());
        newItem.setCost(item.getCost());

        this.purchaseItemRepository.save(newItem);

        purchase.setTotalAmt(this.purchaseRepository.getTotalAmt(purchase));

        this.purchaseRepository.save(purchase);

        purchaseDto.setPurchaseItem(newItem);

      }

    }

    return purchaseDto;

  }

  @Transactional
  @Override
  public PurchaseDto delete(PurchaseItem purchaseItem) {

    PurchaseDto purchaseDto = new PurchaseDto();

    Long purchaseItemId = purchaseItem.getPurchaseItemId();

    Long purchaseId = purchaseItem.getPurchase().getPurchaseId();

    purchaseDto.setPurchaseId(purchaseId);

    Optional<Purchase> optPurchase = this.purchaseRepository.findByPurchaseId(purchaseId);

    if (optPurchase.isPresent()) {

      Purchase purchase = optPurchase.get();

      purchaseDto.setPurchaseStatus(purchase.getPurchaseStatus());

      if (purchase.getPurchaseStatus().equals("Unposted")) {

        this.purchaseItemRepository.deleteById(purchaseItemId);

        purchase.setTotalAmt(this.purchaseRepository.getTotalAmt(purchase));

      }

    }

    return purchaseDto;

  }


}
