package com.example.project.databaseInitializer;

import com.example.project.dtos.CreateUserRequest;
import com.example.project.models.Role;
import com.example.project.services.UserService;
import com.mysql.cj.log.Log;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserDataInitializer implements IDataInitializer {
    private final UserService userService;
    private final Logger logger;

    private final List<String> usernames = List.of(
            "admin",
            "dragos",
            "userTest"
    );
    private final List<String> fullNames = List.of(
            "Administrator Platforma",
            "Dragos Homner",
            "User Test"
    );
    private final List<String> roles = List.of(
            Role.ADMIN,
            Role.CLIENT,
            Role.CLIENT
    );
    private final String password = "pass123";

    public UserDataInitializer(UserService userService, Logger logger) {
        this.userService = userService;
        this.logger = logger;
    }

    public void initialize() {
        for (int i = 0; i < usernames.size(); i++) {
            try {
                CreateUserRequest request = new CreateUserRequest();
                request.setUsername(usernames.get(i));
                request.setFullName(fullNames.get(i));
                request.setPassword(password);
                request.setAuthorities(Set.of(roles.get(i)));

                userService.create(request);
            } catch (Exception e) {
                logger.info(String.format("[UserDataInitializer] Username %s already added", usernames.get(i)));
            }
        }
    }
}
