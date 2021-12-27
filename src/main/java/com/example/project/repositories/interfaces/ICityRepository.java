package com.example.project.repositories.interfaces;

import com.example.project.models.City;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ICityRepository extends CrudRepository<City, Long> {
    Optional<City> findByName(String name);
}
