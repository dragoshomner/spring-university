package com.example.project.repositories.interfaces;

import com.example.project.exceptions.NotFoundUserException;
import com.example.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    default User getById(Long id) {
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundUserException(User.class, id);
        }
        if (!optionalUser.get().isEnabled()) {
            throw new NotFoundUserException(User.class, id);
        }
        return optionalUser.get();
    }
}
