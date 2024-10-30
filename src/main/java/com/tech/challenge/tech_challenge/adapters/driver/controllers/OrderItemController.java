package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.services.OrderService;
import com.tech.challenge.tech_challenge.core.domain.services.order.AddClientToOrderService;
import com.tech.challenge.tech_challenge.core.domain.services.order.AddPaymentToOrderService;
import com.tech.challenge.tech_challenge.core.domain.services.order.UpdateOrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class OrderItemController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order/{orderId}/orderItem")
    public Order addItem(@PathVariable UUID orderId, @RequestBody OrderItem orderItem) throws Exception {
        return orderService.addItem(orderId, orderItem);
    }

    @DeleteMapping("/order/{orderId}/orderItem/{itemId}")
    public Order addItem(@PathVariable UUID orderId, @PathVariable UUID itemId) throws Exception {
        return orderService.removeItem(orderId, itemId);
    }

    @PatchMapping("/order/{orderId}/orderItem/{itemId}")
    public Order addItem(@PathVariable UUID orderId, @PathVariable UUID itemId, @RequestBody OrderItem orderItem) throws Exception {
        return orderService.editItem(orderId, itemId, orderItem);
    }
}
