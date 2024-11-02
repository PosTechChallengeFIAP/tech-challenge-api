package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class OrderItemController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order/{orderId}/orderItem")
    public ResponseEntity addItem(@PathVariable UUID orderId, @RequestBody OrderItem orderItem) throws Exception {
        try{
            Order order = orderService.addItem(orderId, orderItem);
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/order/{orderId}/orderItem/{itemId}")
    public ResponseEntity deleteItem(@PathVariable UUID orderId, @PathVariable UUID itemId) throws Exception {
        Order order = orderService.removeItem(orderId, itemId);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PatchMapping("/order/{orderId}/orderItem/{itemId}")
    public Order editItem(@PathVariable UUID orderId, @PathVariable UUID itemId, @RequestBody OrderItem orderItem) throws Exception {
        return orderService.editItem(orderId, itemId, orderItem);
    }
}
