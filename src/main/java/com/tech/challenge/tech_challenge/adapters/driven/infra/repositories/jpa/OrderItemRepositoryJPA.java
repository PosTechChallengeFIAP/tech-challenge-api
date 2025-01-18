package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa;

import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepositoryJPA extends JpaRepository<OrderItem, UUID> {
    @Query(value = "select * from order_item where order_id = ?1", nativeQuery = true)
    List<OrderItem> getByOrder(UUID orderId);
}
