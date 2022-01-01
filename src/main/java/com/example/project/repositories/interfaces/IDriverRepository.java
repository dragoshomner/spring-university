package com.example.project.repositories.interfaces;

import com.example.project.models.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface IDriverRepository extends CrudRepository<Driver, Long> {
    Page<Driver> findAll(Pageable pageable);
}
