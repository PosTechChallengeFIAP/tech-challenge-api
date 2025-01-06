package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidClientCPF;
import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidClientName;
import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidEmailAddress;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.util.CPFValidator;
import com.tech.challenge.tech_challenge.core.application.util.EmailValidator;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import org.apache.commons.lang3.StringUtils;

public class ValidateClientEmailUseCase {

    public static void validate(String email) throws ValidationException {

        if (StringUtils.isEmpty(email) && !EmailValidator.isValidEmail(email)) throw new InvalidEmailAddress(email);

    }
}
