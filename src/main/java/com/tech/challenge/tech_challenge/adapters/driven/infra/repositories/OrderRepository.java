package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

}
