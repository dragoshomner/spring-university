package com.example.project;

import com.example.project.models.Driver;
import com.example.project.models.Train;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@Rollback(false)
public class EntityManagerTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    public void findTrain() {
        Train trainFound = entityManager.find(Train.class, 10L);
        assertEquals(trainFound.getCode(), "IR005");
    }

    @Test
    public void updateTrain() {
        Train trainFound = entityManager.find(Train.class, 10L);
        trainFound.setNumberOfSeats(100);
        entityManager.persist(trainFound);
        entityManager.flush();
    }

    @Test
    public void findDriver() {
        Driver driverFound = entityManager.find(Driver.class, 5L);
        assertEquals(driverFound.getYearsOfExperience(), 3);
    }
}
