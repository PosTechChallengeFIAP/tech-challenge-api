package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CreateOrderUseCase {

    @Autowired
    private OrderRepository orderRepository;

    public Order execute(Order order) throws ValidationException {
        order.setOrderItems(Collections.emptySet());
        order.setStatus(EOrderStatus.ORDERING);
        order.validate();

        return orderRepository.save(order);
    }
}
