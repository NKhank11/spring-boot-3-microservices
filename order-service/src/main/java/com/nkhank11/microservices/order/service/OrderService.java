package com.nkhank11.microservices.order.service;

import com.nkhank11.microservices.order.client.InventoryClient;
import com.nkhank11.microservices.order.dto.OrderRequest;
import com.nkhank11.microservices.order.model.Order;
import com.nkhank11.microservices.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {
        boolean isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (!isProductInStock) {
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is not in stock");
        }
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());
        orderRepository.save(order);
    }
}