package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "client")
public class ClientEntity {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String cpf;

    @Column
    private String email;
} 