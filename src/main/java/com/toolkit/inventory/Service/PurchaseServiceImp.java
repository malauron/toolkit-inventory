package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.PurchaseDto;
import com.toolkit.inventory.Repository.ItemUomRepository;
import com.toolkit.inventory.Repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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
    purchase.setPurchaseStatus("Unposted");
    purchase.setTotalAmt(purchaseDto.getTotalAmt());

    purchaseDto.getPurchaseItems().forEach(item -> {
      PurchaseItem purchaseItem = new PurchaseItem();
      ItemUomId itemUomId = new ItemUomId();

      itemUomId.setItemId(item.getItem().getItemId());
      itemUomId.setUomId(item.getRequiredUom().getUomId());

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
  public void setVendor(PurchaseDto purchaseDto) {
    this.purchaseRepository.setVendor(
            purchaseDto.getVendor(),
            purchaseDto.getPurchaseId()
    );
  }


}
