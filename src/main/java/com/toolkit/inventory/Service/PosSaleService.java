package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.PosSale;
import com.toolkit.inventory.Domain.PosSaleItem;
import com.toolkit.inventory.Dto.PosSaleDto;

import java.util.Set;

public interface PosSaleService {
    PosSaleDto getPosSale(Long posSaleId);
    PosSale save(PosSaleDto posSaleDto);
    PosSaleDto setPosSale(PosSaleDto posSaleDto);
    PosSaleDto setSaleStatus(PosSaleDto posSaleDto);
    PosSaleDto deletePosSaleItem(PosSaleItem posSaleItem);
    PosSaleDto putPosSaleItem(PosSaleItem posSaleItem);
    Set<PosSaleItem> getPosSaleItems(Long posSaleItemId);

}
