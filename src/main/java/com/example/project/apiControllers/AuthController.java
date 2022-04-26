package com.example.project.apiControllers;

import com.example.project.configuration.security.JwtTokenUtil;
import com.example.project.dtos.AuthRequest;
import com.example.project.dtos.AuthenticatedUser;
import com.example.project.dtos.CreateUserRequest;
import com.example.project.dtos.UserView;
import com.example.project.mappers.AuthenticatedUserMapper;
import com.example.project.models.User;
import com.example.project.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/public/")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Endpoints for managing authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticatedUserMapper authenticatedUserMapper;
    private final UserService userService;

    @Operation(summary = "Get JWT token for user", tags = "Auth")
    @PostMapping("login")
    public ResponseEntity<AuthenticatedUser> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = (User) authenticate.getPrincipal();
            String jwtToken = jwtTokenUtil.generateAccessToken(user);

            return ResponseEntity.ok()
                    .body(authenticatedUserMapper.toAuthenticatedUser(user, jwtToken));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Create new client user", tags = "Auth")
    @PostMapping("register")
    public UserView register(@RequestBody @Valid CreateUserRequest request) {
        return userService.create(request);
    }
}
