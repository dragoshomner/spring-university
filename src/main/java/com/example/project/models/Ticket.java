package com.example.project.models;

import com.example.project.dtos.ObjectMapping;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    public Ticket(User user, Travel travel) {
        this.user = user;
        this.travel = travel;
    }

    public static ArrayList<ObjectMapping> mapping = new ArrayList(Arrays.asList(
            new ObjectMapping("id","ID", true, false, true, "number"),
            new ObjectMapping("user", "User", true, true, false, "select", "fullName"),
            new ObjectMapping("travel", "Travel", true, true, false, "select", "getDescription()")
    ));
}
