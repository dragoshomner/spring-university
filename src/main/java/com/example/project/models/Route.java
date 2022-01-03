package com.example.project.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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

    private Integer distance;

    private Integer duration;
}
