package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreateOrderUseCaseTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Test
    public void createTest() throws ValidationException {
        Order order = new Order();
        order.setId(UUID.randomUUID());

        when(orderRepository.save(order)).thenReturn(order);

        Order orderResult = createOrderUseCase.execute(order);

        assertEquals(order, orderResult);
        assertEquals(orderResult.getOrderItems().size(), 0);
        assertEquals(orderResult.getStatus(), EOrderStatus.ORDERING);
    }
}
