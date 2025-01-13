package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindOrdersUseCaseTest {
    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private FindOrdersUseCase findOrdersUseCase;

    @Test
    public void listTest(){
        Order order1 = new Order();
        Order order2 = new Order();

        order1.setId(UUID.randomUUID());
        order2.setId(UUID.randomUUID());

        when(orderRepository.findAll()).thenReturn(List.of(order1,order2));

        List<Order> orders = findOrdersUseCase.execute();

        assertEquals(orders.size(), 2);
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }
}
