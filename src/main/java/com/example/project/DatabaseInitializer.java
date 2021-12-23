package com.example.project;

import com.example.project.dtos.CreateUserRequest;
import com.example.project.models.Role;
import com.example.project.services.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import org.slf4j.Logger;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {
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

    private final UserService userService;
    private final Logger logger;

    public DatabaseInitializer(UserService userService, Logger logger) {
        this.userService = userService;
        this.logger = logger;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        for (int i = 0; i < usernames.size(); i++) {
            try {
                CreateUserRequest request = new CreateUserRequest();
                request.setUsername(usernames.get(i));
                request.setFullName(fullNames.get(i));
                request.setPassword(password);
                request.setAuthorities(Set.of(roles.get(i)));

                userService.create(request);
            } catch (Exception e) {
                logger.info(String.format("[DatabaseInitializer] Username %s already added", usernames.get(i)));
            }
        }
    }
}
