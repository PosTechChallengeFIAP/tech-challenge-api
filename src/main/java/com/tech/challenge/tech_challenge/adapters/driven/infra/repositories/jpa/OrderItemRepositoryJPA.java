package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepositoryJPA extends JpaRepository<OrderItemEntity, UUID> {
    @Query(value = "select * from order_item where order_id = ?1", nativeQuery = true)
    List<OrderItemEntity> getByOrder(UUID orderId);
}
