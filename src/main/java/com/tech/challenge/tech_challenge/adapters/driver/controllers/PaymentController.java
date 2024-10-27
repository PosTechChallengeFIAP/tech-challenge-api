package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
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
public class PaymentController {

    @Autowired
    AddPaymentToOrderService addPaymentToOrderService;

    @Autowired
    UpdateOrderPaymentService updateOrderPaymentService;

    @PostMapping("/order/{orderId}/payment")
    public Order addPaymentoToOrder(@PathVariable UUID orderId) throws Exception {
        return addPaymentToOrderService.handle(orderId);
    }

    @PatchMapping("/order/{orderId}/payment/{paymentId}")
    public Payment updateOrderPayment(@PathVariable UUID orderId, @PathVariable UUID paymentId, @RequestBody Payment payment) throws Exception {
        return updateOrderPaymentService.handle(orderId, paymentId, payment.getStatus());
    }
}
