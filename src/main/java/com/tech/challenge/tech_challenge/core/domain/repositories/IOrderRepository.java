package com.tech.challenge.tech_challenge.core.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;

public interface IOrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    List<Order> findAll();
}
