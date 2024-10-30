package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.services.extended.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class PaymentController {

    @Autowired
    private OrderPaymentService orderPaymentService;

    @PostMapping("/order/{orderId}/payment")
    public Order addPaymentoToOrder(@PathVariable UUID orderId) throws Exception {
        return orderPaymentService.addPaymentToOrder(orderId);
    }

    @PatchMapping("/order/{orderId}/payment/{paymentId}")
    public Payment updateOrderPayment(@PathVariable UUID orderId, @PathVariable UUID paymentId, @RequestBody Payment payment) throws Exception {
        return orderPaymentService.updateOrderPayment(orderId, paymentId, payment.getStatus());
    }
}
