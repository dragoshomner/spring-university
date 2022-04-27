package com.example.project.repository;

import com.example.project.models.Driver;
import com.example.project.repositories.interfaces.IDriverRepository;
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
@ActiveProfiles("h2")
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class DriverRepositoryTest {

    @Autowired
    private IDriverRepository driverRepository;

    @Test
    @Order(1)
    public void addDriver() {
        Driver driver = new Driver("John", 10);
        driverRepository.save(driver);
    }

    @Test
    @Order(2)
    public void findAll() {
        List<Driver> drivers = (List<Driver>) driverRepository.findAll();
        assertFalse(drivers.isEmpty());
        log.info("findByName ...");
        drivers.forEach(driver -> log.info(driver.getName()));
    }

    @Test
    @Order(3)
    public void findById() {
        Optional<Driver> driver = driverRepository.findById(1L);
        assertTrue(driver.isPresent());
        log.info("findById ...");
        driver.ifPresent(driver1 ->  log.info(driver1.getName()));
    }
}
