package com.tech.challenge.tech_challenge.core.domain.services;


import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidClientCPF;
import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidClientName;
import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidEmailAddress;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.util.CPFValidator;
import com.tech.challenge.tech_challenge.core.application.util.EmailValidator;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import org.apache.commons.lang3.StringUtils;


public class ValidateClient {

    public static void validate(Client client) throws ValidationException {

        if (StringUtils.isEmpty(client.getCpf()) || !CPFValidator.isCPF(client.getCpf())) {
            throw new InvalidClientCPF(client.getCpf());
        }

        client.setCpf(CPFValidator.formatCPF(client.getCpf()));

        if (StringUtils.isEmpty(client.getEmail()) || !EmailValidator.isValidEmail(client.getEmail())) throw new InvalidEmailAddress(client.getEmail());

        if (StringUtils.isEmpty(client.getName())) throw new InvalidClientName();

    }



}
