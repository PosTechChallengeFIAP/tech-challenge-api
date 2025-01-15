package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddPaymentToOrderUseCase {

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @Autowired
    private CreatePaymentUseCase createPaymentUseCase;

    @Autowired
    private UpdateOrderUseCase updateOrderUseCase;

    public Order execute(UUID orderId) throws ResourceNotFoundException, ValidationException {
        Order order = findOrderByIdUseCase.execute(orderId);

        Payment createdPayment = createPaymentUseCase.execute(order.getPrice());
        order.setStatus(EOrderStatus.PAYMENT_PENDING);
        order.setPayment(createdPayment);

        return updateOrderUseCase.execute(order);
    }
}
