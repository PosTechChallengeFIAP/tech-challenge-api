package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.message.MessageResponse;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderBuilder;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.useCases.RemoveItemToOrderUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.AddItemToOrderUseCase.AddItemToOrderUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.EditItemToOrderUseCase.EditItemToOrderUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderItemControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    @Qualifier("addItemToOrderUseCase")
    private AddItemToOrderUseCase addItemToOrderUseCase;

    @MockBean
    @Qualifier("removeItemToOrderUseCase")
    private RemoveItemToOrderUseCase removeItemToOrderUseCase;

    @MockBean
    @Qualifier("editItemToOrderUseCase")
    private EditItemToOrderUseCase editItemToOrderUseCase;

    private String BASE_URL;

    @BeforeEach
    public void setUp(){
        this.BASE_URL = String.format("http://localhost:%d", port);
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    void addItemTest_Success() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();
        OrderItem orderItem = order.getOrderItems().iterator().next();

        when(addItemToOrderUseCase.execute(order.getId(),orderItem)).thenReturn(order);

        ResponseEntity<Order> result = this.restTemplate.postForEntity(
                getFullUrl(String.format("/order/%s/orderItem", order.getId())),
                orderItem,
                Order.class
        );

        Order orderResult = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(order, orderResult);
        assertTrue(order.getOrderItems().contains(orderItem));
    }

    @Test
    void addItemTest_NotFound() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();
        OrderItem orderItem = order.getOrderItems().iterator().next();

        when(addItemToOrderUseCase.execute(order.getId(),orderItem)).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.postForEntity(
                getFullUrl(String.format("/order/%s/orderItem", order.getId())),
                orderItem,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void addItemTest_BadRequest() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();
        OrderItem orderItem = order.getOrderItems().iterator().next();

        when(addItemToOrderUseCase.execute(order.getId(),orderItem)).thenThrow(ValidationException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.postForEntity(
                getFullUrl(String.format("/order/%s/orderItem", order.getId())),
                orderItem,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void removeItemTest_Success() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();
        OrderItem orderItem = order.getOrderItems().iterator().next();
        order.removeItem(orderItem);

        when(removeItemToOrderUseCase.execute(order.getId(),orderItem.getId())).thenReturn(order);

        ResponseEntity<Order> result = this.restTemplate.exchange(
                getFullUrl(String.format("/order/%s/orderItem/%s", order.getId(), orderItem.getId())),
                HttpMethod.DELETE,
                null,
                Order.class
        );

        Order orderResult = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(order, orderResult);
    }

    @Test
    void removeItemTest_NotFound() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();
        OrderItem orderItem = order.getOrderItems().iterator().next();
        order.removeItem(orderItem);

        when(removeItemToOrderUseCase.execute(order.getId(),orderItem.getId())).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.exchange(
                getFullUrl(String.format("/order/%s/orderItem/%s", order.getId(), orderItem.getId())),
                HttpMethod.DELETE,
                null,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void editItemTest_Success() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();
        OrderItem orderItem = order.getOrderItems().iterator().next();

        when(editItemToOrderUseCase.execute(order.getId(),orderItem.getId(),orderItem)).thenReturn(order);

        HttpEntity<OrderItem> entity = new HttpEntity<>(orderItem);

        ResponseEntity<Order> result = this.restTemplate.exchange(
                getFullUrl(String.format("/order/%s/orderItem/%s", order.getId(), orderItem.getId())),
                HttpMethod.PATCH,
                entity,
                Order.class
        );

        Order orderResult = result.getBody();
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(order, orderResult);
    }

    @Test
    void editItemTest_NotFound() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();
        OrderItem orderItem = order.getOrderItems().iterator().next();

        when(editItemToOrderUseCase.execute(order.getId(),orderItem.getId(),orderItem)).thenThrow(ResourceNotFoundException.class);

        HttpEntity<OrderItem> entity = new HttpEntity<>(orderItem);

        ResponseEntity<MessageResponse> result = this.restTemplate.exchange(
                getFullUrl(String.format("/order/%s/orderItem/%s", order.getId(), orderItem.getId())),
                HttpMethod.PATCH,
                entity,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void editItemTest_BadRequest() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();
        OrderItem orderItem = order.getOrderItems().iterator().next();

        when(editItemToOrderUseCase.execute(order.getId(),orderItem.getId(),orderItem)).thenThrow(ValidationException.class);

        HttpEntity<OrderItem> entity = new HttpEntity<>(orderItem);

        ResponseEntity<MessageResponse> result = this.restTemplate.exchange(
                getFullUrl(String.format("/order/%s/orderItem/%s", order.getId(), orderItem.getId())),
                HttpMethod.PATCH,
                entity,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private String getFullUrl(String endpoint){
        return BASE_URL + endpoint;
    }
}
