package com.tech.challenge.tech_challenge.core.application.exceptions;

public class InvalidClientCPF extends RuntimeException {
    public InvalidClientCPF(String cpf) {
        super(String.format("The cpf %s is invalid", cpf));
    }
}
