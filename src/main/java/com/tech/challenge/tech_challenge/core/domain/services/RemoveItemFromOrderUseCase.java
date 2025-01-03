package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class RemoveItemFromOrderUseCase {

    public void removeItem(OrderItem orderItem, Order order) {
        order.getOrderItems().remove(orderItem);
        orderItem.setOrder(null);
    }

}
