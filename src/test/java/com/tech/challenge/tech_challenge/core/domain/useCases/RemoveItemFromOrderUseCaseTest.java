package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import org.hibernate.Remove;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RemoveItemFromOrderUseCaseTest {

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private RemoveItemFromOrderUseCase removeItemFromOrderUseCase;
    @Test
    public void removeItemTest() throws Exception {
        Order order = new Order();
        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();

        orderItem1.setId(UUID.randomUUID());
        orderItem2.setId(UUID.randomUUID());
        order.setId(UUID.randomUUID());

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        order.setOrderItems(orderItems);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order orderResult = removeItemFromOrderUseCase.execute(order.getId(), orderItem2.getId());

        assertEquals(order, orderResult);
        assertEquals(orderResult.getOrderItems().size(), 1);
        assertTrue(orderResult.getOrderItems().contains(orderItem1));
        assertFalse(orderResult.getOrderItems().contains(orderItem2));
    }

}
