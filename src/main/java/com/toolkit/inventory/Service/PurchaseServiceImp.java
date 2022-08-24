package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.PurchaseDto;
import com.toolkit.inventory.Repository.ItemUomRepository;
import com.toolkit.inventory.Repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PurchaseServiceImp implements PurchaseService {

  @Autowired
  private PurchaseRepository purchaseRepository;
  @Autowired
  private ItemUomRepository itemUomRepository;

  @Override
  @Transactional
  public Purchase save(PurchaseDto purchaseDto) {

    Purchase purchase = new Purchase();
    purchase.setVendor(purchaseDto.getVendor());
    purchase.setTotalAmt(purchaseDto.getTotalAmt());

    purchaseDto.getPurchaseItems().forEach(item -> {
      PurchaseItem purchaseItem = new PurchaseItem();
      ItemUomId itemUomId = new ItemUomId();

      itemUomId.setItemId(item.getItem().getItemId());
      itemUomId.setUomId(item.getRequiredUom().getUomId());

      Optional<ItemUom> itemUom = itemUomRepository.findById(itemUomId);

      purchaseItem.setItem(item.getItem());
      purchaseItem.setBaseUom(item.getItem().getUom());
      purchaseItem.setBaseQty(itemUom.get().getQuantity());
      purchaseItem.setRequiredUom(item.getRequiredUom());
      purchaseItem.setRequiredQty(item.getRequiredQty());
      purchaseItem.setPurchasedQty(item.getPurchasedQty());

      purchase.addPurchaseItem(purchaseItem);

    });

    this.purchaseRepository.save(purchase);

    return purchase;
  }
}
