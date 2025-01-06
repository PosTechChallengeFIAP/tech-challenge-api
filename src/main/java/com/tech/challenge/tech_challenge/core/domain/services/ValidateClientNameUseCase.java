package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidClientName;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import org.apache.commons.lang3.StringUtils;

public class ValidateClientNameUseCase {

    public static void validate(String name) throws ValidationException {

        if (StringUtils.isEmpty(name)) throw new InvalidClientName();

    }

}
