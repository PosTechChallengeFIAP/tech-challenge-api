package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.application.exceptions.ClientMustHaveNameAndEmailOrValidCPFException;
import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidClientCPF;
import com.tech.challenge.tech_challenge.core.application.util.CPFValidator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;
import java.util.Objects;

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
        boolean hasName = !Objects.isNull(this.name);
        boolean hasEmail = !Objects.isNull(this.email);
        boolean hasCPF = !Objects.isNull(this.cpf);
        
        if (!hasCPF && !(hasName && hasEmail)) {
            return new ClientMustHaveNameAndEmailOrValidCPFException();
        }

        boolean hasValidCPF = CPFValidator.isCPF(this.cpf);
        if (!hasValidCPF && hasCPF) {
            return new InvalidClientCPF(this.cpf);
        }

        return null;
    }
} 