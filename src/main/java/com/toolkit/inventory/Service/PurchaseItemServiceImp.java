package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Domain.ItemUomId;
import com.toolkit.inventory.Domain.Purchase;
import com.toolkit.inventory.Domain.PurchaseItem;
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
  public PurchaseItem putPurchaseItem(PurchaseItem item) {

    PurchaseItem newItem = new PurchaseItem();

    Optional<Purchase> purchase = this.purchaseRepository.findById(item.getPurchase().getPurchaseId());
    newItem.setPurchase(purchase.get());

    ItemUomId itemUomId = new ItemUomId();
    itemUomId.setItemId(item.getItem().getItemId());
    itemUomId.setUomId(item.getRequiredUom().getUomId());

    if (item.getPurchaseItemId() != null) {
      newItem.setPurchaseItemId(item.getPurchaseItemId());
    }

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
    this.purchaseRepository.setTotalAmt(item.getPurchase().getPurchaseId());
    return newItem;
  }

  @Override
  public void deleteById(Long purchaseItemId) {
    this.purchaseItemRepository.deleteById(purchaseItemId);
  }


}