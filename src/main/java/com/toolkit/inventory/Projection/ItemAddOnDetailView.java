package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemAddOnContent;
import com.toolkit.inventory.Domain.ItemAddOnDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "itemAddOnDetailView", types = {ItemAddOnDetail.class})
public interface ItemAddOnDetailView {

    Long getItemAddOnDetailId();
//    Item getItem();
    String getDescription();
    Boolean getIsRequired();
    Long getMaxNoOfItems();

    @Value("#{@itemAddOnContentRepository.findByItemAddOnDetailOrderById(target)}")
    Set<ItemAddOnContentView> getItemAddOnContents();
}
