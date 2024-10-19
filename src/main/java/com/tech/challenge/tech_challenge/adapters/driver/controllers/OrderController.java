package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/order")
    public List<Order> all(){
        return orderService.list();
    }

    @GetMapping("/order/{id}")
    public Order one(@PathVariable UUID id) throws Exception {
        return orderService.getById(id);
    }

    @PostMapping("/order")
    public Order create(@RequestBody Order order) throws Exception {
        return orderService.create(order);
    }

    @PatchMapping("/order/addItem")
    public Order addItem(@RequestBody OrderItem orderItem) throws Exception {
        return orderService.addItem(orderItem);
    }
}
