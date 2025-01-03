package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductService productService;

    @Test
    public void listTest(){
        Order order1 = new Order();
        Order order2 = new Order();

        order1.setId(UUID.randomUUID());
        order2.setId(UUID.randomUUID());

        when(orderRepository.findAll()).thenReturn(List.of(order1,order2));

        List<Order> orders = orderService.list();

        assertEquals(orders.size(), 2);
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    public void getByIdTest_Success() throws Exception {
        Order order = new Order();

        order.setId(UUID.randomUUID());

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order orderResult = orderService.getById(order.getId());

        assertEquals(order, orderResult);
    }

    @Test
    public void getByIdTest_Exception(){
        UUID id = UUID.randomUUID();

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->{
            orderService.getById(id);
        });
    }

    @Test
    public void createTest() throws ValidationException {
        Order order = new Order();
        order.setId(UUID.randomUUID());

        when(orderRepository.save(order)).thenReturn(order);

        Order orderResult = orderService.create(order);

        assertEquals(order, orderResult);
        assertEquals(orderResult.getOrderItems().size(), 0);
        assertEquals(orderResult.getStatus(), EOrderStatus.ORDERING);
    }

    @Test
    public void addItemTest() throws Exception {
        Order order = new OrderBuilder().build();
        OrderItem orderItem = order.getOrderItems().iterator().next();
        order.setOrderItems(new HashSet<>());
        orderItem.setOrder(null);

        when(productService.getById(orderItem.getProduct().getId())).thenReturn(orderItem.getProduct());
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order orderResult = orderService.addItem(order.getId(), orderItem);

        assertEquals(order, orderResult);
        assertEquals(orderResult.getOrderItems().size(), 1);
        assertTrue(orderResult.getOrderItems().contains(orderItem));
    }

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

        Order orderResult = orderService.removeItem(order.getId(), orderItem2.getId());

        assertEquals(order, orderResult);
        assertEquals(orderResult.getOrderItems().size(), 1);
        assertTrue(orderResult.getOrderItems().contains(orderItem1));
        assertFalse(orderResult.getOrderItems().contains(orderItem2));
    }

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

        Order orderResult = orderService.editItem(order.getId(), oldOrderItem.getId(), newOrderItem);

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
            orderService.editItem(order.getId(), oldOrderItem.getId(), newOrderItem);
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
            orderService.editItem(order.getId(), oldOrderItem.getId(), newOrderItem);
        });

        assertEquals(ex.getMessage(), "Only the 'quantity' property can be edited for order items.");
    }

    @Test
    public void updateTest_Success() throws ValidationException {
        Order order = mock(Order.class);

        when(orderRepository.save(order)).thenReturn(order);

        Order orderResult = orderService.update(order);

        assertEquals(order,orderResult);
    }

    @Test
    public void updateTest_Exception() throws Exception {
        Order order = mock(Order.class);
        ValidateOrderUseCase orderValidator = new ValidateOrderUseCase();

        when(orderRepository.save(order)).thenReturn(order);
        //doThrow(ValidationException.class).doNothing().when(order).validate();

        assertThrows(ValidationException.class, ()->{
            orderService.update(order);
        });
    }

    private OrderItem buildOrderItem(Order order, Product product, Integer quantity){
        OrderItem orderItem = new OrderItem();
        orderItem.setId(UUID.randomUUID());
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setOrder(order);

        return orderItem;
    }
}
