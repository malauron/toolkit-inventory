package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.InventoryItem;
import com.toolkit.inventory.Dto.InventoryItemDto;
import com.toolkit.inventory.Repository.InventoryItemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class InventoryItemServiceImp implements InventoryItemService {

    private InventoryItemRepository inventoryItemRepository;

    public InventoryItemServiceImp(
            InventoryItemRepository inventoryItemRepository
    ){
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @Override
    public Set<InventoryItem> findAllByWarehouseIdWithQty(Long warehouseId) {

        return this.inventoryItemRepository.findAllByWarehouseIdWithQty(warehouseId);

    }

    @Override
    @Transactional
    public void setPrice(InventoryItemDto inventoryItemDto) {
        Optional<InventoryItem> invItem = this.inventoryItemRepository.
                findByInventoryItemId(inventoryItemDto.getInventoryItemId());

        if (invItem.isPresent()) {
            InventoryItem i = invItem.get();
            i.setPrice(inventoryItemDto.getPrice());
            this.inventoryItemRepository.save(i);
        }
    }

    @Override
    @Transactional
    public void setEndingQty(BigDecimal qty, Long inventoryItemId) {
        this.inventoryItemRepository.setEndingQty(qty, inventoryItemId);
    }

    @Override
    @Transactional
    public void setQty(Long id) {

        this.inventoryItemRepository.setQty(id);

    }
}
