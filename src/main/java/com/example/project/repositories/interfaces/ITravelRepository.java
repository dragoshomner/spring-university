package com.example.project.repositories.interfaces;

import com.example.project.models.Travel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ITravelRepository extends JpaRepository<Travel, Long> {

    @Query(value = "FROM Travel as t WHERE " +
            "(:cityFromId is null or t.route.cityFrom.id = :cityFromId) AND " +
            "(:cityToId is null or t.route.cityTo.id = :cityToId) AND " +
            "(:dateFrom is null or t.departureTime > :dateFrom)", nativeQuery = true)
    List<Travel> findAllByCustomParameters(Optional<Long> cityFromId,
                                           Optional<Long> cityToId,
                                           Optional<LocalDateTime> dateFrom,
                                           Pageable pageable);
}
