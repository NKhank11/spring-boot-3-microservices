package com.nkhank11.microservices.order.repository;

import com.nkhank11.microservices.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
