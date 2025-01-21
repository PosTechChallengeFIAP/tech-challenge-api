package com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderItemByIdUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;

public interface IFindOrderItemByIdUseCase {
    OrderItem execute(Order order, UUID orderItemId);
}
