package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.OrderEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;

public class OrderEntityMapper {
    public static Order toDomain(OrderEntity orderEntity) {
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setClient(
            ClientEntityMapper.toDomain(orderEntity.getClient())
        );
        order.setOrderItems(
            OrderItemEntityMapper.toDomain(orderEntity.getOrderItems())
        );
        order.setPrice(orderEntity.getPrice());
        order.setStatus(orderEntity.getStatus());
        order.setPayment(
            PaymentEntityMapper.toDomain(orderEntity.getPayment())
        );
        return order;
    }

    public static OrderEntity toEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setClient(
            ClientEntityMapper.toEntity(order.getClient())
        );
        orderEntity.setOrderItems(
            OrderItemEntityMapper.toEntity(order.getOrderItems())
        );
        orderEntity.setPrice(order.getPrice());
        orderEntity.setStatus(order.getStatus());
        orderEntity.setPayment(
            PaymentEntityMapper.toEntity(order.getPayment())
        );
        return orderEntity;
    }
}
