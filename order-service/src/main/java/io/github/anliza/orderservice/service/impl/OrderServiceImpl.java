package io.github.anliza.orderservice.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import io.github.anliza.orderservice.entity.Order;
import io.github.anliza.orderservice.entity.OrderItem;
import io.github.anliza.orderservice.model.OrderItemRequest;
import io.github.anliza.orderservice.model.OrderRequest;
import io.github.anliza.orderservice.repository.OrderRepository;
import io.github.anliza.orderservice.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @Override
    public void placeOrder(OrderRequest orderRequest) {
       Order order = new Order();
       order.setOrderNumber(UUID.randomUUID().toString());
       order.setOrderTime(Instant.now());
       var orderItems = orderRequest.getOrderItems().stream().map(this::mapToOrderItemEntity).toList();
       order.setOrderItems(orderItems);
       //Checks
       //! All products exists in the inventory
       //! throw an exception with the listing of the products that do have enough stock
       orderRepository.save(order);
    }
    private OrderItem mapToOrderItemEntity(OrderItemRequest itemRequest){
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(itemRequest, orderItem);
        return orderItem;
    }

}