package com.tech.challenge.tech_challenge.core.domain.useCases.sendToSqsPayment;

import java.util.UUID;

public interface ISendToSQSPayment {
    void execute(UUID orderId, UUID paymentId, String paymentStatus);
}
