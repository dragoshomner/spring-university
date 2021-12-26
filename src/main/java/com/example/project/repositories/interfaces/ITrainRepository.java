package com.example.project.repositories.interfaces;

import com.example.project.models.Train;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ITrainRepository extends CrudRepository<Train, Long> {
    Optional<Train> findByCode(String code);
    Page<Train> findAll(Pageable pageable);
}
