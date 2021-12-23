package com.example.project.mappers;

import com.example.project.dtos.CreateUserRequest;
import com.example.project.models.Role;
import com.example.project.models.User;
import com.example.project.repositories.interfaces.IRoleRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.stream.Collectors.toSet;

@Mapper(componentModel = "spring")
public abstract class UserEditMapper {

    @Autowired
    protected IRoleRepository roleRepository;

    @Mapping(target = "authorities", ignore = true)
    public abstract User create(CreateUserRequest request);

    @AfterMapping
    protected void afterCreate(CreateUserRequest request, @MappingTarget User user) {
        if (request.getAuthorities() != null) {
            user.setAuthorities(request.getAuthorities()
                    .stream()
                    .map(authority ->
                            roleRepository.findByAuthority(authority).orElse(new Role(authority)))
                    .collect(toSet()));
        }
    }
}
