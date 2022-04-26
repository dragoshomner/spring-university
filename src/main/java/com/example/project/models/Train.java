package com.example.project.models;

import com.example.project.dtos.ObjectMapping;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Getter @Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {
        @Index(name="unique_code", columnList = "code", unique = true)
})
public class Train {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size.List({
            @Size(min=3, message = "The code must be at least {min} characters"),
            @Size(max=10, message = "The code must be less than {max} characters")
    })
    private String code;

    @Min(1)
    private Integer numberOfSeats;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Train(String code, Integer numberOfSeats) {
        this.code = code;
        this.numberOfSeats = numberOfSeats;
    }

    public static ArrayList<ObjectMapping> mapping = new ArrayList(Arrays.asList(
            new ObjectMapping("id","ID", true, false, true, "number"),
            new ObjectMapping("code", "Code", true, true, true, "text"),
            new ObjectMapping("numberOfSeats", "Number of seats", true, true, false, "number")
    ));
}
