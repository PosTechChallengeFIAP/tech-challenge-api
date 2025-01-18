package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers;

import java.util.HashSet;
import java.util.Set;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.OrderItemEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;

public class OrderItemEntityMapper {
    public static OrderItem toDomain(OrderItemEntity orderItemEntity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemEntity.getId());
        orderItem.setProduct(
            ProductEntityMapper.toDomain(orderItemEntity.getProduct())
        );
        orderItem.setQuantity(orderItemEntity.getQuantity());
        return orderItem;
    }

    public static Set<OrderItem> toDomain(Set<OrderItemEntity> orderItemsEntity) {
        Set<OrderItem> orderItems = new HashSet<>();

        orderItemsEntity.forEach(orderItemEntity -> {
            OrderItem orderItem = toDomain(orderItemEntity);

            orderItems.add(orderItem);
        });

        return orderItems;
    }

    public static OrderItemEntity toEntity(OrderItem orderItem) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(orderItem.getId());
        orderItemEntity.setProduct(
            ProductEntityMapper.toEntity(orderItem.getProduct())
        );
        orderItemEntity.setQuantity(orderItem.getQuantity());
        return orderItemEntity;
    }

    public static Set<OrderItemEntity> toEntity(Set<OrderItem> orderItems) {
        Set<OrderItemEntity> orderItemsEntity = new HashSet<>();

        orderItems.forEach(orderItem -> {
            OrderItemEntity orderItemEntity = toEntity(orderItem);

            orderItemsEntity.add(orderItemEntity);
        });

        return orderItemsEntity;
    }
}
