package io.github.anliza.inventoryservice.service;

import java.util.List;

import io.github.anliza.inventoryservice.model.InventoryCreateDto;
import io.github.anliza.inventoryservice.model.InventoryResponse;

public interface InventoryService {

    InventoryResponse createInventory(InventoryCreateDto inventoryCreateDto);
    
    Boolean checkInventory(List<String> productCodes, List<Integer> productQuantities);

}