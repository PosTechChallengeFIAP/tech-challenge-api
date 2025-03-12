package com.tech.challenge.tech_challenge.adapters.driver.controllers.auth;

import com.tech.challenge.tech_challenge.core.application.dtos.auth.LoginUserDTO;
import com.tech.challenge.tech_challenge.core.application.dtos.auth.RegisterClientDTO;
import com.tech.challenge.tech_challenge.core.application.dtos.auth.UserWithoutPasswordDTO;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.message.EMessageType;
import com.tech.challenge.tech_challenge.core.application.message.MessageResponse;
import com.tech.challenge.tech_challenge.core.application.message.auth.LoginResponse;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.auth.User;
import com.tech.challenge.tech_challenge.core.domain.services.auth.IAuthenticationService;
import com.tech.challenge.tech_challenge.core.domain.services.auth.IJwtService;
import com.tech.challenge.tech_challenge.core.domain.useCases.CreateClientUseCase.ICreateClientUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private IJwtService jwtService;

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private ICreateClientUseCase createClientUseCase;

    @PostMapping("/signup")
    @Operation(summary = "Create client", description = "This endpoint is used to create a new client",
            tags = {"Client"},
            responses ={
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = {
                                    @Content(schema = @Schema(implementation = Client.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity newClient(@RequestBody RegisterClientDTO registerUserDTO){
        try {
            Client createdClient = createClientUseCase.execute(registerUserDTO.getClient());

            User user = authenticationService.signup(registerUserDTO, createdClient.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(UserWithoutPasswordDTO.fromUser(user));
        }catch (ValidationException | DataIntegrityViolationException ex){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}