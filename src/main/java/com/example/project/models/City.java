package com.example.project.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {
        @Index(name="unique_name", columnList = "name", unique = true)
})
public class City {
    @Id @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 1, message = "City name cannot be empty")
    private String name;
}
