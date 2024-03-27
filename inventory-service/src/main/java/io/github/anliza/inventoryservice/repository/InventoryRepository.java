package io.github.anliza.inventoryservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.github.anliza.inventoryservice.entity.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory,String>{

}
