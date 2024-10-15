package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @GetMapping("/order")
    public List<Order> all(){
        return orderService.list();
    }


    @PostMapping("/order")
    public Order newOrder(@RequestBody Order order){
        return orderService.create(order);
    }
}
