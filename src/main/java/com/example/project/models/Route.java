package com.example.project.models;

import com.example.project.dtos.ObjectMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Route {
    @Id
    @GeneratedValue
    @Schema(hidden = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_from_id", nullable = false)
    private City cityFrom;

    @ManyToOne
    @JoinColumn(name = "city_to_id", nullable = false)
    private City cityTo;

    @Min(value = 1, message = "Minimum distance value is 1")
    @Max(value = 5000, message = "Maximum distance value is 5000")
    private Integer distance;

    @Min(value = 1, message = "Minimum duration value is 1")
    private Integer duration;

    public String getDescription() {
        return this.cityFrom.getName() + " - " + this.cityTo.getName();
    }

    public Route(City cityFrom, City cityTo, Integer distance, Integer duration) {
        this.cityTo = cityTo;
        this.cityFrom = cityFrom;
        this.duration = duration;
        this.distance = distance;
    }

    public static ArrayList<ObjectMapping> mapping = new ArrayList(Arrays.asList(
            new ObjectMapping("id","ID", true, false, true, "number"),
            new ObjectMapping("cityFrom", "From", true, true, false, "select", "name"),
            new ObjectMapping("cityTo", "To", true, true, false, "select", "name"),
            new ObjectMapping("distance", "Distance", true, true, false, "integer"),
            new ObjectMapping("duration", "Duration", true, true, false, "integer")
    ));
}
