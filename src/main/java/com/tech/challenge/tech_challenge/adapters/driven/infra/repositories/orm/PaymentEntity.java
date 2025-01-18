package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm;

import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, name = "`value`")
    private Double value;

    @Enumerated(EnumType.ORDINAL)
    private EPaymentStatus status;

    @Column(name = "payment_url")
    private String paymentURL;
}
