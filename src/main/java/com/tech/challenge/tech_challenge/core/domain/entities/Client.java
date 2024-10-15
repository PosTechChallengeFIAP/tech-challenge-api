package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.application.util.CPFValidator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String cpf;

    @Column
    private String email;

    public Client(){}

    public Error validate() {
        if (!CPFValidator.isCPF(this.cpf)) {
            return new Error("Invalid CPF");
        }

        if (this.name == null || this.name.isEmpty()) {
            return new Error("Invalid name");
        }

        if (this.email == null || this.email.isEmpty()){
            return new Error("Invalid email");
        }

        return null;
    }
} 