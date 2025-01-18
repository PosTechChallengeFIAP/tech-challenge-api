package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "`order`")
public class OrderEntity {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"client_id\"")
    private ClientEntity client;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<OrderItemEntity> orderItems;

    @Transient
    private double price;

    @Enumerated(EnumType.ORDINAL)
    private EOrderStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private PaymentEntity payment;
}
