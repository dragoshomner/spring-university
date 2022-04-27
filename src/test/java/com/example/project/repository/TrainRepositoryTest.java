package com.example.project.repository;

import com.example.project.models.Train;
import com.example.project.repositories.interfaces.ITrainRepository;
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
public class TrainRepositoryTest {

    @Autowired
    private ITrainRepository trainRepository;

    @Test
    @Order(1)
    public void addTrain() {
        Train train = new Train("John", 10);
        trainRepository.save(train);
    }

    @Test
    @Order(2)
    public void findAll() {
        List<Train> trains = (List<Train>) trainRepository.findAll();
        assertFalse(trains.isEmpty());
        log.info("findByName ...");
        trains.forEach(train -> log.info(train.getCode()));
    }

    @Test
    @Order(3)
    public void findById() {
        Optional<Train> train = trainRepository.findById(1L);
        assertTrue(train.isPresent());
        log.info("findById ...");
        train.ifPresent(train1 ->  log.info(train1.getCode()));
    }
}
