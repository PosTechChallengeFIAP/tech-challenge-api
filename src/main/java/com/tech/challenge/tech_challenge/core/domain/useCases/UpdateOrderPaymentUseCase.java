package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateOrderPaymentUseCase {

    @Autowired
    private FindPaymentByIdUseCase findPaymentByIdUseCase;

    @Autowired
    private UpdatePaymentUseCase updatePaymentUseCase;

    @Autowired
    private ReceiveOrderUseCase receiveOrderUseCase;

    @Autowired
    private UpdateOrderUseCase updateOrderUseCase;

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    public Payment execute(UUID orderId, UUID paymentId, EPaymentStatus status) throws ResourceNotFoundException, ValidationException {
        Payment payment = findPaymentByIdUseCase.execute(paymentId);
        payment.setSatus(status);

        Order order = findOrderByIdUseCase.execute(orderId);
        order.setPayment(payment);

        Payment updatedPayment = updatePaymentUseCase.execute(payment);
        updateOrderUseCase.execute(order);

        if(status == EPaymentStatus.PAID) {
            receiveOrderUseCase.execute(order);
        }

        return updatedPayment;
    }
}
