package com.tech.challenge.tech_challenge.core.domain.useCases.UpdateOrderPaymentUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateOrderUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdatePaymentUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderByIdUseCase.FindOrderByIdUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindPaymentByIdUseCase.FindPaymentByIdUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.ReceiveOrderUseCase.ReceiveOrderUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateOrderPaymentUseCase implements IUpdateOrderPaymentUseCase {

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

    public Payment execute(UUID orderId, UUID paymentId, EPaymentStatus status) {
        Payment payment = findPaymentByIdUseCase.execute(paymentId);
        payment.setStatus(status);

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
