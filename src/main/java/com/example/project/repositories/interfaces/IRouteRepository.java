package com.example.project.repositories.interfaces;

import com.example.project.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IRouteRepository extends JpaRepository<Route, Long> {
}
