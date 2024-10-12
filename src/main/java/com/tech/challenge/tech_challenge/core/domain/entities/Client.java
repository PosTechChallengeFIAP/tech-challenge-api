package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.domain.entities.services.CPFValidator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Client {
    private String id;
    private String name;
    private String cpf;
    private String email;

    public Error validate() {
        if (!CPFValidator.isCPF(this.cpf)) {
            return new Error("Invalid CPF");
        }

        if (this.name == null || this.name == "") {
            return new Error("Invalid name");
        }

        if (this.email == null || this.email == "") {
            return new Error("Invalid email");
        }

        return null;
    }
} 