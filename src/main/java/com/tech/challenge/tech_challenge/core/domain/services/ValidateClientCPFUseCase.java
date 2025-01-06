package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidClientCPF;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.util.CPFValidator;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import org.apache.commons.lang3.StringUtils;

public class ValidateClientCPFUseCase {

    public static String validateCPF(String cpf) throws ValidationException {
        if (StringUtils.isEmpty(cpf) || !CPFValidator.isCPF(cpf)) {
            throw new InvalidClientCPF(cpf);
        }
        return CPFValidator.formatCPF(cpf);

    }

}
