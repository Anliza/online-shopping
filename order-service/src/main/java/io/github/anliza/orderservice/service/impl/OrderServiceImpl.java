package io.github.anliza.orderservice.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.anliza.orderservice.entity.Order;
import io.github.anliza.orderservice.entity.OrderItem;
import io.github.anliza.orderservice.exception.InventoryServiceException;
import io.github.anliza.orderservice.exception.NotEnoughQuantityException;
import io.github.anliza.orderservice.exception.OrderServiceException;
import io.github.anliza.orderservice.model.GenericResponse;
import io.github.anliza.orderservice.model.OrderItemRequest;
import io.github.anliza.orderservice.model.OrderRequest;
import io.github.anliza.orderservice.repository.OrderRepository;
import io.github.anliza.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public OrderServiceImpl(OrderRepository orderRepository, WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String placeOrder(OrderRequest orderRequest) {

        Order order = new Order();

        // Checks
        // ! All products exists in the inventory
        // http://localhost:6002/api/inventory/check
        // restTemplate

        List<String> productCodes = new ArrayList<>();
        List<Integer> productQuantities = new ArrayList<>();

        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            productCodes.add(orderItemRequest.getProductCode());
            productQuantities.add(orderItemRequest.getQuantity());
        }
        log.info("{}", productCodes);
        log.info("{}", productQuantities);
        GenericResponse<?> response = webClient.get()
                .uri("http://localhost:6002/api/inventory/check",
                        uriBuilder -> uriBuilder
                                .queryParam("productCodes", productCodes)
                                .queryParam("productQuantities", productQuantities)
                                .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> handleError(clientResponse))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<?>>() {
                })
                .block();
        if (response.isSuccess()) {
            // stock
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderTime(Instant.now());
            var orderItems = orderRequest.getOrderItems().stream().map(this::mapToOrderItemEntity).toList();
            order.setOrderItems(orderItems);
            orderRepository.save(order);
            //TODO: make the call to inventory to reduce quantity
            //TODO: process payment for an order
            return order.getOrderNumber();

        } else {
            // ! throw an exception with the listing of the products that do have enough
            log.error("Not Enough stock");
            log.info("{}", response.getData());
            if(response.getData() instanceof Map){
                throw new NotEnoughQuantityException(response.getMsg(),(Map<String, Integer>) response.getData());
            }
            throw new OrderServiceException(response.getMsg());

        }

    }

    private Mono<? extends Throwable> handleError(ClientResponse response) {
        log.error("Client error received: {}", response.statusCode());
        return Mono.error(new InventoryServiceException("Error in inventory service"));
    }

    private OrderItem mapToOrderItemEntity(OrderItemRequest itemRequest) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(itemRequest, orderItem);
        return orderItem;
    }

}