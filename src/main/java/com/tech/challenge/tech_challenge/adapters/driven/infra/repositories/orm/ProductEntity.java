package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm;
import com.tech.challenge.tech_challenge.core.domain.entities.EProductCategory;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private EProductCategory category;

    @Column(nullable = false)
    private Double price;

    @Column
    private Boolean active;
}
