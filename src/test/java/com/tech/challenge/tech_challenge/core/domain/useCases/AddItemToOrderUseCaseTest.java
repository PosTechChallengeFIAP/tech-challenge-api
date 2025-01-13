package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderBuilder;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AddItemToOrderUseCaseTest {

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductService productService;

    @MockBean
    private AddItemToOrderUseCase addItemToOrderUseCase;

    @Test
    public void addItemTest() throws Exception {
        Order order = new OrderBuilder().build();
        OrderItem orderItem = order.getOrderItems().iterator().next();
        order.setOrderItems(new HashSet<>());
        orderItem.setOrder(null);

        when(productService.getById(orderItem.getProduct().getId())).thenReturn(orderItem.getProduct());
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order orderResult = addItemToOrderUseCase.execute(order.getId(), orderItem);

        assertEquals(order, orderResult);
        assertEquals(orderResult.getOrderItems().size(), 1);
        assertTrue(orderResult.getOrderItems().contains(orderItem));
    }

}
