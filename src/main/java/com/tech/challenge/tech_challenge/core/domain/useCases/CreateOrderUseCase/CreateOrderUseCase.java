package com.tech.challenge.tech_challenge.core.domain.useCases.CreateOrderUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CreateOrderUseCase {

    @Autowired
    private IOrderRepository orderRepository;

    public Order execute(Order order) {
        order.setOrderItems(Collections.emptySet());
        order.setStatus(EOrderStatus.ORDERING);
        order.validate();

        return orderRepository.save(order);
    }
}
