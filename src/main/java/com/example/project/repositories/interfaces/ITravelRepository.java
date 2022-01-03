package com.example.project.repositories.interfaces;

import com.example.project.models.Travel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITravelRepository extends CrudRepository<Travel, Long> {
    @Query("FROM Travel as t WHERE t.route.cityFrom.id = :cityFromId")
    List<Travel> findAllByCityFromId(Long cityFromId);
}
