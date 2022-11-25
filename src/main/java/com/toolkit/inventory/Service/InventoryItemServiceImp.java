package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.InventoryHistory;
import com.toolkit.inventory.Domain.InventoryHistoryItem;
import com.toolkit.inventory.Domain.InventoryItem;
import com.toolkit.inventory.Domain.Warehouse;
import com.toolkit.inventory.Dto.InventoryItemDto;
import com.toolkit.inventory.Repository.InventoryHistoryItemRepository;
import com.toolkit.inventory.Repository.InventoryHistoryRepository;
import com.toolkit.inventory.Repository.InventoryItemRepository;
import com.toolkit.inventory.Repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class InventoryItemServiceImp implements InventoryItemService {

    private InventoryItemRepository inventoryItemRepository;
    private WarehouseRepository warehouseRepository;
    private InventoryHistoryRepository inventoryHistoryRepository;
    private InventoryHistoryItemRepository inventoryHistoryItemRepository;

    public InventoryItemServiceImp(
            InventoryItemRepository inventoryItemRepository,
            WarehouseRepository warehouseRepository,
            InventoryHistoryRepository inventoryHistoryRepository,
            InventoryHistoryItemRepository inventoryHistoryItemRepository
    ){
        this.inventoryItemRepository = inventoryItemRepository;
        this.warehouseRepository = warehouseRepository;
        this.inventoryHistoryRepository = inventoryHistoryRepository;
        this.inventoryHistoryItemRepository = inventoryHistoryItemRepository;
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
    public void finalizeInventory(InventoryItemDto inventoryItemDto) {

        Optional<Warehouse> optWhse = this.warehouseRepository
                .findById(inventoryItemDto.getWarehouse().getWarehouseId());
        if (optWhse.isPresent()) {

            Warehouse warehouse = optWhse.get();

            Set<InventoryItem> inventoryItems = this.inventoryItemRepository
                    .findAllByWarehouseIdWithQty(warehouse.getWarehouseId());

            if (!inventoryItems.isEmpty()) {

                InventoryHistory invHis = new InventoryHistory();

                invHis.setWarehouse(warehouse);

                InventoryHistory inventoryHistory = this.inventoryHistoryRepository.saveAndFlush(invHis);

                inventoryItems.forEach(inventoryItem -> {
                    InventoryHistoryItem inventoryHistoryItem = new InventoryHistoryItem();

                    inventoryHistoryItem.setInventoryHistory(inventoryHistory);
                    inventoryHistoryItem.setItem(inventoryItem.getItem());
                    inventoryHistoryItem.setBeginningQty(inventoryItem.getBeginningQty());
                    inventoryHistoryItem.setPurchasedQty(inventoryItem.getPurchasedQty());
                    inventoryHistoryItem.setEndingQty(inventoryItem.getEndingQty());
                    inventoryHistoryItem.setCost(inventoryItem.getCost());
                    inventoryHistoryItem.setPrice(inventoryItem.getPrice());

                    this.inventoryHistoryItemRepository.save(inventoryHistoryItem);

                });

                this.inventoryItemRepository.setQty(warehouse.getWarehouseId());

            }

        }

    }
}
