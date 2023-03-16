package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.CustomerGroup;
import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemPrice;
import com.toolkit.inventory.Dto.ItemPriceDto;
import com.toolkit.inventory.Repository.CustomerGroupRepository;
import com.toolkit.inventory.Repository.ItemPriceRepository;
import com.toolkit.inventory.Repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemPriceServiceImp implements ItemPriceService{

    private ItemRepository itemRepository;

    private CustomerGroupRepository customerGroupRepository;

    private ItemPriceRepository itemPriceRepository;

    public ItemPriceServiceImp(ItemRepository itemRepository,
                               CustomerGroupRepository customerGroupRepository,
                               ItemPriceRepository itemPriceRepository) {
        this.itemRepository = itemRepository;
        this.customerGroupRepository = customerGroupRepository;
        this.itemPriceRepository = itemPriceRepository;
    }

    @Override
    public ItemPriceDto save(ItemPriceDto itemPriceDto){

        Optional<Item> item = this.itemRepository
                .findById(itemPriceDto.getItem().getItemId());

        Optional<CustomerGroup> customerGroup = this.customerGroupRepository
                .findById(itemPriceDto.getCustomerGroup().getCustomerGroupId());

        if (itemPriceDto.getItemPriceId() > 0) {

            Optional<ItemPrice> itemPrice = this.itemPriceRepository
                    .findById(itemPriceDto.getItemPriceId());

            if (itemPrice.isPresent()) {
                ItemPrice currentItemPrice = itemPrice.get();
                currentItemPrice.setItem(item.get());
                currentItemPrice.setCustomerGroup(customerGroup.get());
                currentItemPrice.setPrice(itemPriceDto.getPrice());

                this.itemPriceRepository.save(currentItemPrice);
            }
        } else {

            ItemPrice newItemPrice = new ItemPrice();
            newItemPrice.setItem(item.get());
            newItemPrice.setCustomerGroup(customerGroup.get());
            newItemPrice.setPrice(itemPriceDto.getPrice());

            this.itemPriceRepository.saveAndFlush(newItemPrice);
        }

        return null;
    }

}
