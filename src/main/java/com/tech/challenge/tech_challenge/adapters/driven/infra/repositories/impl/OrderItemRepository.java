package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.OrderItemRepositoryJPA;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers.OrderItemEntityMapper;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.OrderItemEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class OrderItemRepository implements IOrderItemRepository {

    @Autowired
    private OrderItemRepositoryJPA orderItemRepositoryJPA;
    
    @Override
    public List<OrderItem> getByOrder(UUID orderId) {
        List<OrderItemEntity> orderItemsEntity = orderItemRepositoryJPA.getByOrder(orderId);
        List<OrderItem> orderItems = toDomain(orderItemsEntity);
        return orderItems;
    }

    private OrderItem toDomain(OrderItemEntity orderItemEntity) {
        return OrderItemEntityMapper.toDomain(orderItemEntity);
    }

    private List<OrderItem> toDomain(List<OrderItemEntity> orderItemsEntity) {
        List<OrderItem> orderItems = new ArrayList<>();

        orderItemsEntity.forEach(orderItemEntity -> {
            OrderItem orderItem = toDomain(orderItemEntity);
            orderItems.add(orderItem);
        });

        return orderItems;
    }
}
