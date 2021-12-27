package com.example.project.databaseInitializer;

import com.example.project.dtos.CityDto;
import com.example.project.dtos.CreateTrain;
import com.example.project.exceptions.DuplicateEntityException;
import com.example.project.models.City;
import com.example.project.models.Train;
import com.example.project.services.CityService;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CityDataInitializer implements IDataInitializer {
    public final CityService cityService;
    public final Logger logger;

    @Override
    public void initialize() {
        try {
            if (cityService.getCount() > 0) {
                throw new DuplicateEntityException(City.class);
            }
            String currentDirectory = System.getProperty("user.dir");
            String csvPath = "/src/main/java/com/example/project/databaseInitializer/csv/cities.csv";
            try (CSVReader reader = new CSVReader(new FileReader(currentDirectory + csvPath))) {
                List<String[]> r = reader.readAll();
                r.forEach(x -> cityService.save(new CityDto(x[0])));
            }
        } catch (Exception e) {
            logger.info("[CityDataInitializer] " + e.getMessage());
        }
    }
}
