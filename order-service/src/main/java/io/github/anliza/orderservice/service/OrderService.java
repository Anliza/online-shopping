package io.github.anliza.orderservice.service;

import io.github.anliza.orderservice.model.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);

}
