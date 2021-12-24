package com.example.project.mappers;

import com.example.project.dtos.AuthenticatedUser;
import com.example.project.models.User;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AuthenticatedUserMapper {

    @Autowired
    protected UserViewMapper userViewMapper;

    public AuthenticatedUser toAuthenticatedUser(User user, String jwtToken) {
        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUserView(userViewMapper.toUserView(user));
        authenticatedUser.setJwtToken(jwtToken);
        return authenticatedUser;
    }
}
