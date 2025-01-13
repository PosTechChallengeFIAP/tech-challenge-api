package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class RemoveItemFromOrderUseCase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @Autowired
    private FindOrderItemByIdUseCase findOrderItemByIdUseCase;

    public Order execute(UUID orderId, UUID itemId) throws ResourceNotFoundException, NoSuchElementException {
        Order order = findOrderByIdUseCase.execute(orderId);
        order.removeItem(findOrderItemByIdUseCase.execute(order, itemId));

        return orderRepository.save(order);
    }
}
