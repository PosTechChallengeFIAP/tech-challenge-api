package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindOrderItemByIdUseCase {

    public OrderItem execute(Order order, UUID orderItemId){
        return order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getId().equals(orderItemId)).findFirst()
                .orElseThrow();
    }
}
