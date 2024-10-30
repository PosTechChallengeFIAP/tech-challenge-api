package com.tech.challenge.tech_challenge.core.domain.services.order;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderBuilder;
import com.tech.challenge.tech_challenge.core.domain.services.ClientService;
import com.tech.challenge.tech_challenge.core.domain.services.extended.OrderClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderClientServiceTest {

    @Autowired
    private OrderClientService orderClientService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ClientService clientService;

    @Test
    public void addClientToOrderTest_Success() throws Exception {
        Client client = mock(Client.class);
        UUID clientId = UUID.randomUUID();

        OrderBuilder orderBuilder = new OrderBuilder();
        Order order = orderBuilder.build();

        when(clientService.getById(clientId)).thenReturn(client);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(client.validate()).thenReturn(null);
        when(client.getId()).thenReturn(clientId);

        Order orderResult = orderClientService.addClientToOrder(order.getId(),clientId);

        assertEquals(client, orderResult.getClient());
    }

    @Test
    public void addClientToOrderTest_Exception_InvalidClient() throws Exception {
        Client client = mock(Client.class);
        Error error = mock(Error.class);
        UUID clientId = UUID.randomUUID();

        OrderBuilder orderBuilder = new OrderBuilder();
        Order order = orderBuilder.build();

        when(clientService.getById(clientId)).thenReturn(client);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(client.validate()).thenReturn(error);
        when(client.getId()).thenReturn(clientId);

        Error thrownError = assertThrows(Error.class, ()->{
            orderClientService.addClientToOrder(order.getId(),clientId);
        });

        assertEquals(error, thrownError);
    }

    @Test
    public void addClientToOrderTest_Exception_InvalidOrder() throws Exception {
        Client client = mock(Client.class);
        UUID clientId = UUID.randomUUID();

        Order order = new Order();
        order.setId(UUID.randomUUID());

        when(clientService.getById(clientId)).thenReturn(client);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(client.validate()).thenReturn(null);
        when(client.getId()).thenReturn(clientId);

        Error thrownError = assertThrows(Error.class, ()->{
            orderClientService.addClientToOrder(order.getId(),clientId);
        });

        assertTrue(thrownError.getMessage().contains("Invalid"));
    }
}
