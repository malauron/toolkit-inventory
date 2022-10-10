package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.PurchaseDto;
import com.toolkit.inventory.Repository.ItemRepository;
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

  private PurchaseRepository purchaseRepository;

  private PurchaseItemRepository purchaseItemRepository;

  private ItemRepository itemRepository;

  private ItemUomRepository itemUomRepository;

  @Autowired
  public PurchaseItemServiceImp(
          PurchaseRepository purchaseRepository,
          PurchaseItemRepository purchaseItemRepository,
          ItemRepository itemRepository,
          ItemUomRepository itemUomRepository
  ) {
    this.purchaseRepository = purchaseRepository;
    this.purchaseItemRepository = purchaseItemRepository;
    this.itemRepository = itemRepository;
    this.itemUomRepository = itemUomRepository;
  }

  @Transactional
  @Override
  public PurchaseDto putPurchaseItem(PurchaseItem purchaseItem) {

    PurchaseDto purchaseDto = new PurchaseDto();

    Long purchaseId = purchaseItem.getPurchase().getPurchaseId();

    purchaseDto.setPurchaseId(purchaseId);

    Optional<Purchase> optPurchase = this.purchaseRepository.findByPurchaseId(purchaseId);

    if (optPurchase.isPresent()) {

      Purchase purchase = optPurchase.get();

      purchaseDto.setPurchaseStatus(purchase.getPurchaseStatus());

      PurchaseItem newPurchaseItem = new PurchaseItem();

      newPurchaseItem.setPurchase(purchase);

      if (purchase.getPurchaseStatus().equals("Unposted")) {

        if (purchaseItem.getPurchaseItemId() != null) {

          newPurchaseItem.setPurchaseItemId(purchaseItem.getPurchaseItemId());

        }

        Item item = null;

        Optional<Item> tmpItem = this.itemRepository.findById(purchaseItem.getItem().getItemId());

        if (tmpItem.isPresent()) {
          item = tmpItem.get();
        }

        ItemUomId itemUomId = new ItemUomId();

        itemUomId.setItemId(item.getItemId());
        itemUomId.setUomId(purchaseItem.getRequiredUom().getUomId());

        Optional<ItemUom> itemUom = this.itemUomRepository.findById(itemUomId);

        if (itemUom.isPresent()) {

          newPurchaseItem.setBaseQty(itemUom.get().getQuantity());

        } else {

          newPurchaseItem.setBaseQty(new BigDecimal(1));

        }

        newPurchaseItem.setItem(item);
        newPurchaseItem.setBaseUom(item.getUom());
        newPurchaseItem.setItemClass(item.getItemClass());
        newPurchaseItem.setRequiredUom(purchaseItem.getRequiredUom());
        newPurchaseItem.setPurchasedQty(purchaseItem.getPurchasedQty());
        newPurchaseItem.setPurchasePrice(purchaseItem.getPurchasePrice());
        newPurchaseItem.setTotalAmount((purchaseItem.getTotalAmount()));

        this.purchaseItemRepository.save(newPurchaseItem);

        purchase.setTotalAmt(this.purchaseRepository.getTotalAmt(purchase));

        this.purchaseRepository.save(purchase);

        purchaseDto.setPurchaseItem(newPurchaseItem);

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
