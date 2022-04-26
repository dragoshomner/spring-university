package com.example.project.models;

import com.example.project.dtos.ObjectMapping;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "travel")
public class Travel {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime arrivalTime;

    private Integer remainingNumberOfSeats;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Travel(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return route.getCityFrom().getName() + " " + route.getCityTo().getName() + " on " + departureTime.toString();
    }

    public static ArrayList<ObjectMapping> mapping = new ArrayList(Arrays.asList(
            new ObjectMapping("id","ID", true, false, true, "number"),
            new ObjectMapping("route", "Route", true, true, false, "select", "getDescription()"),
            new ObjectMapping("train", "Train", true, true, false, "select", "code"),
            new ObjectMapping("driver", "Driver", true, true, false, "select", "name"),
            new ObjectMapping("departureTime", "Departure", true, true, false, "datetime-local"),
            new ObjectMapping("arrivalTime", "Arrival", true, true, false, "datetime-local")
    ));
}
