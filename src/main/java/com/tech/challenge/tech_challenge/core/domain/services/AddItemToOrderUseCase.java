package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class AddItemToOrderUseCase {

    public void addItem(OrderItem orderItem, Order order) {

        if(Objects.isNull(order.getOrderItems())) order.setOrderItems(new HashSet<>());

        OrderItem orderItemFound = findOrderItem(orderItem, order);

        if (orderItemFound != null) {

            int lastQuantity = orderItemFound.getQuantity();
            orderItemFound.setQuantity(lastQuantity+1);

        } else{

            order.getOrderItems().add(orderItem);
            orderItem.setOrder(order);

        }

    }

    private OrderItem findOrderItem(OrderItem orderItem,Order order) {
        return order.getOrderItems().stream()
                .filter(item -> item.getId() == orderItem.getId())
                .findFirst()
                .orElse(null);
    }
}
