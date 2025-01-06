package com.tech.challenge.tech_challenge.core.domain.services;


import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;



public class ValidateClientUseCase {

    public static void validate(Client client) throws ValidationException {

        client.setCpf(ValidateClientCPFUseCase.validateCPF(client.getCpf()));

        ValidateClientEmailUseCase.validate(client.getEmail());

        ValidateClientNameUseCase.validate(client.getName());

    }



}
