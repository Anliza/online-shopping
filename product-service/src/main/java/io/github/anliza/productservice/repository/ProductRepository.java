package io.github.anliza.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.anliza.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    
}
