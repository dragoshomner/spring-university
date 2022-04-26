package com.example.project.services;

import com.example.project.dtos.CreateUserRequest;
import com.example.project.exceptions.DuplicateEntityException;
import com.example.project.mappers.UserEditMapper;
import com.example.project.dtos.UserView;
import com.example.project.mappers.UserViewMapper;
import com.example.project.models.User;
import com.example.project.repositories.interfaces.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import java.util.HashSet;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final IUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserViewMapper userViewMapper;
    private final UserEditMapper userEditMapper;

    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("User with username - %s, not found", username))
                );
    }

    public User getById(Long userId) throws UsernameNotFoundException {
        return userRepo
                .findById(userId)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("User with id - %s, not found", userId))
                );
    }

    @Transactional
    public UserView create(CreateUserRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateEntityException(User.class);
        }
        if (request.getAuthorities() == null) {
            request.setAuthorities(new HashSet<>());
        }

        User user = userEditMapper.create(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepo.save(user);

        return userViewMapper.toUserView(user);
    }
}
