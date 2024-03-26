package io.github.anliza.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.anliza.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer>{

}