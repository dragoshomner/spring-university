package com.example.project.dtos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AuthRequest {

    @NotNull
    @Schema(example = "dragos")
    private String username;
    @NotNull
    @Schema(example = "pass123")
    private String password;

}
