package com.tech.challenge.tech_challenge.core.domain.useCases.AddClientToOrderUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindClientByIdUseCase.FindClientByIdUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderByIdUseCase.FindOrderByIdUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddClientToOrderUseCase implements IAddClientToOrderUseCase{

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private FindClientByIdUseCase findClientByIdUseCase;

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    public Order execute(UUID orderId, UUID clientId) {
        Order order = findOrderByIdUseCase.execute(orderId);
        Client client = findClientByIdUseCase.execute(clientId);
        client.validate();

        order.setClient(client);
        order.validate();

        return orderRepository.save(order);
    }
}
