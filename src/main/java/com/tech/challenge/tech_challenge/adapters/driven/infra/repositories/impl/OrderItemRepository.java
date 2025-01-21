package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.OrderItemRepositoryJPA;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class OrderItemRepository implements IOrderItemRepository {

    @Autowired
    private OrderItemRepositoryJPA orderItemRepositoryJPA;
    
    @Override
    public OrderItem save(OrderItem orderItemToSave) {
        return orderItemRepositoryJPA.save(orderItemToSave);
    }

    @Override
    public List<OrderItem> getByOrder(UUID orderId) {
        return orderItemRepositoryJPA.getByOrder(orderId);
    }
}
