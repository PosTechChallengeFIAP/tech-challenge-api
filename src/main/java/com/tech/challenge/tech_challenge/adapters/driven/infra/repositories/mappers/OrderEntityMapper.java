package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.OrderEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;

public class OrderEntityMapper {
    public static Order toDomain(OrderEntity orderEntity) {
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setClient(
            orderEntity.getClient() != null ? ClientEntityMapper.toDomain(orderEntity.getClient()) : null
        );
        order.setOrderItems(
            orderEntity.getOrderItems() != null ? OrderItemEntityMapper.toDomain(orderEntity.getOrderItems()) : null
        );
        order.setPrice(orderEntity.getPrice());
        order.setStatus(orderEntity.getStatus());
        order.setPayment(
            orderEntity.getPayment() != null ? PaymentEntityMapper.toDomain(orderEntity.getPayment()) : null
        );
        return order;
    }

    public static OrderEntity toEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setClient(
            order.getClient() != null ? ClientEntityMapper.toEntity(order.getClient()) : null
        );
        orderEntity.setOrderItems(
            order.getOrderItems() != null ? OrderItemEntityMapper.toEntity(order.getOrderItems()) : null
        );
        orderEntity.setPrice(order.getPrice());
        orderEntity.setStatus(order.getStatus());
        orderEntity.setPayment(
            order.getPayment() != null ? PaymentEntityMapper.toEntity(order.getPayment()) : null
        );
        return orderEntity;
    }
}
