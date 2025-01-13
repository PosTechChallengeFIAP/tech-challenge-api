package com.tech.challenge.tech_challenge.core.domain.services.extended;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.services.ClientService;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderByIdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderClientService {

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientService clientService;

    public Order addClientToOrder(UUID orderId, UUID clientId) throws ValidationException, ResourceNotFoundException {
        Order order = findOrderByIdUseCase.execute(orderId);
        Client client = clientService.getById(clientId);
        client.validate();

        order.setClient(client);
        order.validate();

        return orderRepository.save(order);
    }
}
