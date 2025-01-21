package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.message.MessageResponse;
import com.tech.challenge.tech_challenge.core.domain.entities.*;
import com.tech.challenge.tech_challenge.core.domain.useCases.*;
import com.tech.challenge.tech_challenge.core.domain.useCases.AddClientToOrderUseCase.AddClientToOrderUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    @Qualifier("findOrdersUseCase")
    private FindOrdersUseCase findOrdersUseCase;

    @MockBean
    @Qualifier("findOrderByIdUseCase")
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @MockBean
    @Qualifier("createOrderUseCase")
    private CreateOrderUseCase createOrderUseCase;

    @MockBean
    @Qualifier("addClientToOrderUseCase")
    private AddClientToOrderUseCase addClientToOrderUseCase;

    private String BASE_URL;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        this.BASE_URL = String.format("http://localhost:%d", port);
    }

    @Test
    void listTest_Success() {
        Order order1 = new OrderBuilder()
                .withOrderItems(new ArrayList<>())
                .withPayment(null)
                .withStatus(EOrderStatus.ORDERING)
                .build();
        Order order2 = new OrderBuilder()
                .withOrderItems(new ArrayList<>())
                .withPayment(null)
                .withStatus(EOrderStatus.ORDERING)
                .build();

        when(findOrdersUseCase.execute()).thenReturn(List.of(order2,order1));

        ResponseEntity<List> resultList  = this.restTemplate.getForEntity(getFullUrl("/order"),
                List.class);

        List<Order> orders = new ArrayList<>();
        for(Object object : Objects.requireNonNull(resultList.getBody())){
            orders.add(mapper.convertValue(object, Order.class));
        }

        assertEquals(resultList.getStatusCode(), HttpStatus.OK);
        assertEquals(orders.size(),2);
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    void getByIdTest_Success() throws ResourceNotFoundException {
        Order order = new OrderBuilder()
                .withOrderItems(new ArrayList<>())
                .withPayment(null)
                .withStatus(EOrderStatus.ORDERING)
                .build();

        when(findOrderByIdUseCase.execute(order.getId())).thenReturn(order);
        ResponseEntity<Order> result = this.restTemplate.getForEntity(getFullUrl("/order/" + order.getId()),
                Order.class);

        Order orderResult = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(order,orderResult);
    }

    @Test
    void getByIdTest_NotFound() throws ResourceNotFoundException {
        UUID id = UUID.randomUUID();

        when(findOrderByIdUseCase.execute(id)).thenThrow(ResourceNotFoundException.class);
        ResponseEntity<MessageResponse> result = this.restTemplate.getForEntity(getFullUrl("/order/" + id),
                MessageResponse.class);

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getByIdTest_BadRequest() throws ResourceNotFoundException {
        String id = "7834687346";

        ResponseEntity<MessageResponse> result = this.restTemplate.getForEntity(getFullUrl("/order/" + id),
                MessageResponse.class);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void createTest_Success() throws ValidationException {
        Order order = new OrderBuilder().withId(null).build();
        Order createdOrder = new OrderBuilder().build();

        when(createOrderUseCase.execute(any())).thenReturn(createdOrder);

        ResponseEntity<Order> result = this.restTemplate.postForEntity(
                getFullUrl("/order"),
                order,
                Order.class
        );

        Order orderResult = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(createdOrder, orderResult);
    }

    @Test
    void createTest_BadRequest() throws ValidationException {
        Order order = new OrderBuilder().build();

        when(createOrderUseCase.execute(any())).thenThrow(ValidationException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.postForEntity(
                getFullUrl("/order"),
                order,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void addClientTest_Success() throws ValidationException, ResourceNotFoundException {
        Client client = new ClientBuilder().build();
        Order order = new OrderBuilder().withClient(client).build();

        when(addClientToOrderUseCase.execute(order.getId(), client.getId())).thenReturn(order);

        ResponseEntity<Order> result = this.restTemplate.postForEntity(
                getFullUrl(String.format("/order/%s/client/%s", order.getId(), client.getId())),
                order,
                Order.class
        );

        Order orderResult = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(order, orderResult);
    }

    @Test
    void addClientTest_NotFound() throws ValidationException, ResourceNotFoundException {
        Client client = new ClientBuilder().build();
        Order order = new OrderBuilder().withClient(client).build();

        when(addClientToOrderUseCase.execute(order.getId(), client.getId())).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.postForEntity(
                getFullUrl(String.format("/order/%s/client/%s", order.getId(), client.getId())),
                order,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void addClientTest_BadRequest() throws ValidationException, ResourceNotFoundException {
        Client client = new ClientBuilder().build();
        Order order = new OrderBuilder().withClient(client).build();

        when(addClientToOrderUseCase.execute(order.getId(), client.getId())).thenThrow(ValidationException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.postForEntity(
                getFullUrl(String.format("/order/%s/client/%s", order.getId(), client.getId())),
                order,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private String getFullUrl(String endpoint){
        return BASE_URL + endpoint;
    }
}
