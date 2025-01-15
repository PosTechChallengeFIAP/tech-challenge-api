package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.PaymentRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddClientToOrderUseCase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FindClientByIdUseCase findClientByIdUseCase;

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    public Order execute(UUID orderId, UUID clientId) throws ValidationException, ResourceNotFoundException {
        Order order = findOrderByIdUseCase.execute(orderId);
        Client client = findClientByIdUseCase.execute(clientId);
        client.validate();

        order.setClient(client);
        order.validate();

        return orderRepository.save(order);
    }
}
