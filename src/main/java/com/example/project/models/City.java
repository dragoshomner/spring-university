package com.example.project.models;

import com.example.project.dtos.ObjectMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Getter @Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {
        @Index(name="unique_name", columnList = "name", unique = true)
})
public class City {
    @Id @GeneratedValue
    @Schema(hidden = true)
    private Long id;

    @NotNull
    @Size(min = 1, message = "City name cannot be empty")
    private String name;

    @CreatedDate
    @Schema(hidden = true)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Schema(hidden = true)
    private LocalDateTime modifiedAt;

    public City(String name) {
        this.name = name;
    }

    public static ArrayList<ObjectMapping> mapping = new ArrayList(Arrays.asList(
        new ObjectMapping("id","ID", true, false, true, "number"),
        new ObjectMapping("name", "Name", true, true, false, "text")
    ));
}
