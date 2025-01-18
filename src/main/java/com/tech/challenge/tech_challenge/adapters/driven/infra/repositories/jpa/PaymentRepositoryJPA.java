package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

public interface PaymentRepositoryJPA extends JpaRepository<Payment, UUID>{
}
