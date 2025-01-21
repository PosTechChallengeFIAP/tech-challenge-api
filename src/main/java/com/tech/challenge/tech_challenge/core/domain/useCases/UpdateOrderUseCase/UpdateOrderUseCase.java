package com.tech.challenge.tech_challenge.core.domain.useCases.UpdateOrderUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;
import com.tech.challenge.tech_challenge.core.domain.services.generic.Patcher;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderByIdUseCase.FindOrderByIdUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateOrderUseCase implements IUpdateOrderUseCase {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @Autowired
    private Patcher<Order> orderPatcher;

    public Order execute(Order order) {
        order.validate();

        return orderRepository.save(order);
    }

    public Order execute(UUID id, Order order) throws IllegalAccessException  {

        Order orderRecord = findOrderByIdUseCase.execute(id);

        Order updatedOrder = orderPatcher.execute(orderRecord,order);

        updatedOrder.validate();

        return orderRepository.save(updatedOrder);
    }
}
