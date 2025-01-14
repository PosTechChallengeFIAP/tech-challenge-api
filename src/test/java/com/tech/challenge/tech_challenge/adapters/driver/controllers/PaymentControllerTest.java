package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.message.MessageResponse;
import com.tech.challenge.tech_challenge.core.domain.entities.*;
import com.tech.challenge.tech_challenge.core.domain.services.extended.OrderPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
public class PaymentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OrderPaymentService orderPaymentService;

    private String BASE_URL;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        this.BASE_URL = String.format("http://localhost:%d", port);
    }

    @Test
    void addPaymentToOrderTest_Success() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();

        when(orderPaymentService.addPaymentToOrder(order.getId())).thenReturn(order);

        ResponseEntity<Order> result  = this.restTemplate.postForEntity(getFullUrl(String.format("/order/%s/payment", order.getId())),
                null,
                Order.class);

        Order orderResult = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(order, orderResult);
    }

    @Test
    void addPaymentToOrderTest_NotFound() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();

        when(orderPaymentService.addPaymentToOrder(order.getId())).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<MessageResponse> result  = this.restTemplate.postForEntity(getFullUrl(String.format("/order/%s/payment", order.getId())),
                null,
                MessageResponse.class);

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void addPaymentToOrderTest_BadRequest() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();

        when(orderPaymentService.addPaymentToOrder(order.getId())).thenThrow(ValidationException.class);

        ResponseEntity<MessageResponse> result  = this.restTemplate.postForEntity(getFullUrl(String.format("/order/%s/payment", order.getId())),
                null,
                MessageResponse.class);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateOrderPaymentTest_Success() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();
        Payment payment = order.getPayment();
        HttpEntity<Payment> entity = new HttpEntity<>(payment);

        when(orderPaymentService.updateOrderPayment(order.getId(),payment.getId(), payment.getStatus())).thenReturn(entity.getBody());

        ResponseEntity<Payment> result  = this.restTemplate.exchange(
                getFullUrl(String.format("/order/%s/payment/%s", order.getId(), payment.getId())),
                HttpMethod.PATCH,
                entity,
                Payment.class);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void updateOrderPaymentTest_NotFound() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();
        Payment payment = order.getPayment();
        HttpEntity<Payment> entity = new HttpEntity<>(payment);

        when(orderPaymentService.updateOrderPayment(order.getId(),payment.getId(), payment.getStatus())).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<MessageResponse> result  = this.restTemplate.exchange(
                getFullUrl(String.format("/order/%s/payment/%s", order.getId(), payment.getId())),
                HttpMethod.PATCH,
                entity,
                MessageResponse.class);

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void updateOrderPaymentTest_BadRequest() throws ValidationException, ResourceNotFoundException {
        Order order = new OrderBuilder().build();
        Payment payment = order.getPayment();
        HttpEntity<Payment> entity = new HttpEntity<>(payment);

        when(orderPaymentService.updateOrderPayment(order.getId(),payment.getId(), payment.getStatus())).thenThrow(ValidationException.class);

        ResponseEntity<MessageResponse> result  = this.restTemplate.exchange(
                getFullUrl(String.format("/order/%s/payment/%s", order.getId(), payment.getId())),
                HttpMethod.PATCH,
                entity,
                MessageResponse.class);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private String getFullUrl(String endpoint){
        return BASE_URL + endpoint;
    }
}
