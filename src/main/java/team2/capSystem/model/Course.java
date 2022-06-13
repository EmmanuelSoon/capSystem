package team2.capSystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    private String name;
    private String description;

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
