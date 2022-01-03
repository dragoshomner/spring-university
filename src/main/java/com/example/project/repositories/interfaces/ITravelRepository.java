package com.example.project.repositories.interfaces;

import com.example.project.models.Travel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITravelRepository extends CrudRepository<Travel, Long> {
    @Query("FROM Travel as t WHERE t.route.cityFrom.id = :cityFromId AND t.route.cityTo.id = :cityToId")
    List<Travel> findAllByCityFromAndCityToId(Long cityFromId, Long cityToId);

    @Query("FROM Travel as t WHERE t.route.cityFrom.id = :cityFromId")
    List<Travel> findAllByCityFromId(Long cityFromId);

    @Query("FROM Travel as t WHERE t.route.cityTo.id = :cityToId")
    List<Travel> findAllByCityToId(Long cityToId);
}
