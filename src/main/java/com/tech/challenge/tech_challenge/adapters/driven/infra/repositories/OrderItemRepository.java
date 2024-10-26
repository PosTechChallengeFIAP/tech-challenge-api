package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    @Query(value = "select * from order_item where order_id = ?1", nativeQuery = true)
    List<OrderItem> getByOrder(UUID orderId);
}
