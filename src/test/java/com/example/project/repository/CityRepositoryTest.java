package com.example.project.repository;

import com.example.project.models.City;
import com.example.project.repositories.interfaces.ICityRepository;
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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CityRepositoryTest {

    @Autowired
    private ICityRepository cityRepository;

    @Test
    @Order(1)
    public void addCity() {
        City city = new City("Paris");
        cityRepository.save(city);
    }

    @Test
    @Order(2)
    public void findByName() {
        Optional<City> city = cityRepository.findByName("Paris");
        assertFalse(city.isPresent());
        log.info("findByName ...");
        city.ifPresent(city1 ->  log.info(city1.getName()));
    }

    @Test
    @Order(3)
    public void findById() {
        Optional<City> city = cityRepository.findById(1L);
        assertFalse(city.isPresent());
        log.info("findById ...");
        city.ifPresent(city1 ->  log.info(city1.getName()));
    }
}
