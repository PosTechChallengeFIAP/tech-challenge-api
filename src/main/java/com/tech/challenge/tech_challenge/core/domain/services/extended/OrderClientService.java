package com.tech.challenge.tech_challenge.core.domain.services.extended;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.services.ClientService;
import com.tech.challenge.tech_challenge.core.domain.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderClientService extends OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientService clientService;

    public Order addClientToOrder(UUID orderId, UUID clientId) throws Exception {
        Order order = getById(orderId);
        Client client = clientService.getById(clientId);
        Error err = client.validate();
        if (err != null) {
            throw err;
        }

        order.setClient(client);
        err = order.validate();
        if(err != null) {
            throw err;
        }

        return orderRepository.save(order);
    }
}
