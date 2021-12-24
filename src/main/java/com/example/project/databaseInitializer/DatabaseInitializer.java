package com.example.project.databaseInitializer;

import com.example.project.dtos.CreateUserRequest;
import com.example.project.models.Role;
import com.example.project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import org.slf4j.Logger;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserDataInitializer userDataInitializer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.userDataInitializer.initialize();
    }
}
