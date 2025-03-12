package com.tech.challenge.tech_challenge.core.domain.services.auth;

import com.tech.challenge.tech_challenge.core.application.dtos.auth.LoginUserDTO;
import com.tech.challenge.tech_challenge.core.application.dtos.auth.RegisterClientDTO;
import com.tech.challenge.tech_challenge.core.domain.entities.auth.User;

import java.util.UUID;

public interface IAuthenticationService {
    User signup(RegisterClientDTO input, UUID clientId);

    User authenticate(LoginUserDTO input);
}