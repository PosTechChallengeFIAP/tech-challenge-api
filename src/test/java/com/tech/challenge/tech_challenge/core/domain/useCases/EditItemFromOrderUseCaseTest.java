package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EditItemFromOrderUseCaseTest {

    @MockBean
    private IOrderRepository orderRepository;

    @Autowired
    private EditItemToOrderUseCase editItemFromOrderUseCase;

    @Test
    public void editItemTest_Success() throws Exception {
        Order order = new Order();
        order.setId(UUID.randomUUID());

        Product product = new Product();
        product.setId(UUID.randomUUID());

        OrderItem oldOrderItem = buildOrderItem(order, product, 2);

        OrderItem newOrderItem = buildOrderItem(order, product, 5);
        newOrderItem.setId(oldOrderItem.getId());

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(oldOrderItem);

        order.setOrderItems(orderItems);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order orderResult = editItemFromOrderUseCase.execute(order.getId(), oldOrderItem.getId(), newOrderItem);

        assertEquals(order, orderResult);
        assertEquals(orderResult.getOrderItems().size(), 1);
        assertTrue(orderResult.getOrderItems().contains(newOrderItem));
        assertEquals(orderResult.getOrderItems().iterator().next().getQuantity(),5);
    }

    @Test
    public void editItemTest_Exception_WrongProduct() throws Exception {
        Order order = new Order();
        order.setId(UUID.randomUUID());

        Product product1 = new Product();
        product1.setId(UUID.randomUUID());

        Product product2 = new Product();
        product2.setId(UUID.randomUUID());

        OrderItem oldOrderItem = buildOrderItem(order, product1, 2);

        OrderItem newOrderItem = buildOrderItem(order, product2, 5);
        newOrderItem.setId(oldOrderItem.getId());

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(oldOrderItem);

        order.setOrderItems(orderItems);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Exception ex = assertThrows(Exception.class, ()->{
            editItemFromOrderUseCase.execute(order.getId(), oldOrderItem.getId(), newOrderItem);
        });

        assertEquals(ex.getMessage(), "Only the 'quantity' property can be edited for order items.");
    }

    @Test
    public void editItemTest_Exception_WrongId() throws Exception {
        Order order = new Order();
        order.setId(UUID.randomUUID());

        Product product = new Product();
        product.setId(UUID.randomUUID());

        OrderItem oldOrderItem = buildOrderItem(order, product, 2);

        OrderItem newOrderItem = buildOrderItem(order, product, 5);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(oldOrderItem);

        order.setOrderItems(orderItems);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Exception ex = assertThrows(Exception.class, ()->{
            editItemFromOrderUseCase.execute(order.getId(), oldOrderItem.getId(), newOrderItem);
        });

        assertEquals(ex.getMessage(), "Only the 'quantity' property can be edited for order items.");
    }

    private OrderItem buildOrderItem(Order order, Product product, Integer quantity){
        OrderItem orderItem = new OrderItem();
        orderItem.setId(UUID.randomUUID());
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);

        return orderItem;
    }
}
