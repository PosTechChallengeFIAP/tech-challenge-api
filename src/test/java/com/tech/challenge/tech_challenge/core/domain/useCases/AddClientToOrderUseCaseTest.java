package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AddClientToOrderUseCaseTest {

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private FindClientByIdUseCase findClientByIdUseCase;

    @MockBean
    private AddClientToOrderUseCase addClientToOrderUseCase;

    @Test
    public void addClientToOrderTest_Success() throws Exception {
        Client client = mock(Client.class);
        UUID clientId = UUID.randomUUID();

        OrderBuilder orderBuilder = new OrderBuilder();
        Order order = orderBuilder.build();

        when(findClientByIdUseCase.execute(clientId)).thenReturn(client);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(client.getId()).thenReturn(clientId);

        Order orderResult = addClientToOrderUseCase.execute(order.getId(),clientId);

        assertEquals(client, orderResult.getClient());
    }

    @Test
    public void addClientToOrderTest_Exception_InvalidClient() throws Exception {
        Client client = mock(Client.class);
        UUID clientId = UUID.randomUUID();

        OrderBuilder orderBuilder = new OrderBuilder();
        Order order = orderBuilder.build();

        when(findClientByIdUseCase.execute(clientId)).thenReturn(client);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        doThrow(ValidationException.class).doNothing().when(client).validate();
        when(client.getId()).thenReturn(clientId);

        assertThrows(ValidationException.class, ()->{
            addClientToOrderUseCase.execute(order.getId(),clientId);
        });
    }

    @Test
    public void addClientToOrderTest_Exception_InvalidOrder() throws Exception {
        Client client = mock(Client.class);
        UUID clientId = UUID.randomUUID();

        Order order = mock(Order.class);
        UUID id = UUID.randomUUID();

        when(order.getId()).thenReturn(id);
        when(client.getId()).thenReturn(clientId);
        when(findClientByIdUseCase.execute(clientId)).thenReturn(client);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        doThrow(ValidationException.class).doNothing().when(order).validate();
        when(client.getId()).thenReturn(clientId);

        assertThrows(ValidationException.class, ()->{
            addClientToOrderUseCase.execute(order.getId(),clientId);
        });
    }


}
