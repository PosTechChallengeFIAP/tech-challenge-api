package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.services.generic.Patcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateOrderUseCase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @Autowired
    private Patcher<Order> orderPatcher;

    public Order execute(Order order) throws ValidationException {
        order.validate();

        return orderRepository.save(order);
    }

    public Order execute(UUID id, Order order) throws ResourceNotFoundException, ValidationException, IllegalAccessException  {

        Order orderRecord = findOrderByIdUseCase.execute(id);

        Order updatedOrder = orderPatcher.execute(orderRecord,order);

        updatedOrder.validate();

        return orderRepository.save(updatedOrder);
    }
}
