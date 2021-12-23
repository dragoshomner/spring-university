package com.example.project.mappers;

import com.example.project.dtos.UserView;
import com.example.project.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserViewMapper {
    public abstract UserView toUserView(User user);
}