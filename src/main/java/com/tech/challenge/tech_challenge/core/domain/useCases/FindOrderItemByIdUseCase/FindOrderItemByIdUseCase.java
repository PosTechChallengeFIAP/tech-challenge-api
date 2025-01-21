package com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderItemByIdUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindOrderItemByIdUseCase implements IFindOrderItemByIdUseCase {

    public OrderItem execute(Order order, UUID orderItemId){
        return order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getId().equals(orderItemId)).findFirst()
                .orElseThrow();
    }
}
