package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderBuilder;
import com.tech.challenge.tech_challenge.core.domain.services.OrderService;
import com.tech.challenge.tech_challenge.core.domain.services.extended.OrderClientService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    @Qualifier("orderService")
    private OrderService orderService;

    @MockBean
    private OrderClientService orderClientService;

    private String BASE_URL;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        this.BASE_URL = String.format("http://localhost:%d", port);
    }

    @Test
    void listTest_Success() {
        Order order1 = new OrderBuilder()
                .withOrderItems(null)
                .withPayment(null)
                .withStatus(EOrderStatus.ORDERING)
                .build();
        Order order2 = new OrderBuilder()
                .withOrderItems(null)
                .withPayment(null)
                .withStatus(EOrderStatus.ORDERING)
                .build();

        when(orderService.list()).thenReturn(List.of(order1,order2));
        ResponseEntity<List> resultList = this.restTemplate.getForEntity(getFullUrl("/order"),
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

    private String getFullUrl(String endpoint){
        return BASE_URL + endpoint;
    }
}
