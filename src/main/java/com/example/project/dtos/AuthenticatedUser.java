package com.example.project.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticatedUser {
    @JsonProperty("userData")
    UserView userView;
    String jwtToken;
}
