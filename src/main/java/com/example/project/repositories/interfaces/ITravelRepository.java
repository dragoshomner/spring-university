package com.example.project.repositories.interfaces;

import com.example.project.models.Travel;
import org.springframework.data.repository.CrudRepository;

public interface ITravelRepository extends CrudRepository<Travel, Long> {
}
