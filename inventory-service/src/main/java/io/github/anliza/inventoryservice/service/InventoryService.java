package io.github.anliza.inventoryservice.service;

import io.github.anliza.inventoryservice.model.InventoryCreateDto;
import io.github.anliza.inventoryservice.model.InventoryResponse;

public interface InventoryService {

    InventoryResponse createInventory(InventoryCreateDto inventoryCreateDto);

}