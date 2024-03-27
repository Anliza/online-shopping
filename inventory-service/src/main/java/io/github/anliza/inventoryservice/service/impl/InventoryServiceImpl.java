package io.github.anliza.inventoryservice.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import io.github.anliza.inventoryservice.entity.Inventory;
import io.github.anliza.inventoryservice.model.InventoryCreateDto;
import io.github.anliza.inventoryservice.model.InventoryResponse;
import io.github.anliza.inventoryservice.repository.InventoryRepository;
import io.github.anliza.inventoryservice.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository){
        this.inventoryRepository=inventoryRepository;
    }

    @Override
    public InventoryResponse createInventory(InventoryCreateDto inventoryCreateDto) {
       var savedObj =  inventoryRepository.save(mapToInventory(inventoryCreateDto));
       return mapToInventoryResponse(savedObj);

    }
    private Inventory mapToInventory(InventoryCreateDto source){
        Inventory target = new Inventory();
        BeanUtils.copyProperties(source, target);
        return target;

    }
    private InventoryResponse mapToInventoryResponse(Inventory source){
        InventoryResponse target = new InventoryResponse();
        BeanUtils.copyProperties(source, target);
        return target;

    }

}

