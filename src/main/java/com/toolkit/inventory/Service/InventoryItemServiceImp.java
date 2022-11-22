package com.toolkit.inventory.Service;

import com.toolkit.inventory.Repository.InventoryItemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class InventoryItemServiceImp implements InventoryItemService {

    private InventoryItemRepository inventoryItemRepository;

    public InventoryItemServiceImp(
            InventoryItemRepository inventoryItemRepository
    ){
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @Override
    @Transactional
    public void setEndingQty(BigDecimal qty, Long inventoryItemId) {
        this.inventoryItemRepository.setEndingQty(qty, inventoryItemId);
    }
}
