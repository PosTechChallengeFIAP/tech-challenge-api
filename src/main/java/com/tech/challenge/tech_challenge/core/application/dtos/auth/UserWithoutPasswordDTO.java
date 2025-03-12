package com.tech.challenge.tech_challenge.core.application.dtos.auth;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.auth.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class UserWithoutPasswordDTO {
    private UUID id;
    private String username;
    private Client client;
    private Date createdAt;
    private Date updatedAt;

    public static UserWithoutPasswordDTO fromUser(User user){
        UserWithoutPasswordDTO userResponse = new UserWithoutPasswordDTO();

        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setClient(user.getClient());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getCreatedAt());

        return userResponse;
    }
}
