package com.example.project.repository;

import com.example.project.models.Route;
import com.example.project.repositories.interfaces.IRouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class RouteRepositoryTest {

    @Autowired
    private IRouteRepository routeRepository;

    @Test
    @Order(1)
    public void findAll() {
        List<Route> routes = (List<Route>) routeRepository.findAll();
        assertFalse(routes.isEmpty());
        log.info("findAll ...");
        routes.forEach(route -> log.info(route.getDescription()));
    }

    @Test
    @Order(2)
    public void findById() {
        Optional<Route> route = routeRepository.findById(150L);
        assertTrue(route.isPresent());
        log.info("findById ...");
        route.ifPresent(route1 ->  log.info(route1.getDescription()));
    }
}
