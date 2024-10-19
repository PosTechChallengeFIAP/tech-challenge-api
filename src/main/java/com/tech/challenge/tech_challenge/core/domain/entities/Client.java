package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.application.util.CPFValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "client")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client )) return false;
        return id != null && id.equals(((Client) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 