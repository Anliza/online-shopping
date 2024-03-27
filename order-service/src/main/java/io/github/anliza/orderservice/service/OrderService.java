package io.github.anliza.orderservice.service;

import io.github.anliza.orderservice.model.OrderRequest;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest);

}
