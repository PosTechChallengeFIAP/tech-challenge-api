package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.OrderRepositoryJPA;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers.OrderEntityMapper;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.OrderEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;

@Repository
public class OrderRepository implements IOrderRepository {

    @Autowired
    private OrderRepositoryJPA orderRepositoryJPA;

    @Override
    public Order save(Order orderToSave) {
        OrderEntity orderEntityToSave = toEntity(orderToSave);
        OrderEntity savedOrderEntity = orderRepositoryJPA.save(orderEntityToSave);
        Order order = toDomain(savedOrderEntity);
        return order;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        Optional<Order> order = orderRepositoryJPA.findById(id).map(orderEntity -> toDomain(orderEntity));
        return order;
    }

    @Override
    public List<Order> findAll() {
        List<OrderEntity> ordersEntity = orderRepositoryJPA.findAll();
        List<Order> orders = toDomain(ordersEntity);
        return orders;
    }

    private Order toDomain(OrderEntity orderEntity) {
        return OrderEntityMapper.toDomain(orderEntity);
    }

    private List<Order> toDomain(List<OrderEntity> ordersEntity) {
        List<Order> orders = new ArrayList<>();

        ordersEntity.forEach(orderEntity -> {
            Order order = toDomain(orderEntity);
            orders.add(order);
        });

        return orders;
    }

    private OrderEntity toEntity(Order order) {
        return OrderEntityMapper.toEntity(order);
    }
}
