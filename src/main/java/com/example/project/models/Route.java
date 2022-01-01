package com.example.project.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Route {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_from_id", nullable = false)
    private City cityFrom;

    @ManyToOne
    @JoinColumn(name = "city_to_id", nullable = false)
    private City cityTo;

//    @ManyToOne
//    @JoinColumn(name = "train_id", nullable = false)
//    private Train train;
//
//    @ManyToOne
//    @JoinColumn(name = "driver_id", nullable = false)
//    private Driver driver;

//    @NotNull
//    private LocalDateTime departureTime;
//
//    private LocalDateTime arrivalTime;

    private Integer distance;

    private Integer duration;

//    private Integer remainingNumberOfSeats;

//    @CreatedDate
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    private LocalDateTime modifiedAt;
}
