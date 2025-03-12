package com.tech.challenge.tech_challenge.core.application.dtos.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDTO {
    private String username;
    private String password;
}
