package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID>{
}
