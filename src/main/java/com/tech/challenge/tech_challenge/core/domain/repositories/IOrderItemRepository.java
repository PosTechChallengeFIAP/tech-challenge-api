package com.tech.challenge.tech_challenge.core.domain.repositories;

import java.util.List;
import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;

public interface IOrderItemRepository {
    List<OrderItem> getByOrder(UUID orderId);
}
