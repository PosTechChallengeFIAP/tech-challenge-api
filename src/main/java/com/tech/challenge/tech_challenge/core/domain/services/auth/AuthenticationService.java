package com.tech.challenge.tech_challenge.core.domain.services.auth;

import com.tech.challenge.tech_challenge.core.application.dtos.auth.LoginUserDTO;
import com.tech.challenge.tech_challenge.core.application.dtos.auth.RegisterClientDTO;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.auth.User;
import com.tech.challenge.tech_challenge.core.domain.repositories.IClientRepository;
import com.tech.challenge.tech_challenge.core.domain.repositories.IUserRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindClientByIdUseCase.IFindClientByIdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService implements IAuthenticationService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IFindClientByIdUseCase findClientByIdUseCase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(RegisterClientDTO input, UUID clientId) {
        User user = new User();
        user.setUsername(input.getClient().getCpf());
        user.setEncryptedPassword(passwordEncoder.encode(input.getPassword()));

        Client client = findClientByIdUseCase.execute(clientId);
        user.setClient(client);

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }
}