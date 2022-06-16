package team2.capSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    List<CourseDetail> CourseDetails = new ArrayList<>();

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Course(String name, String description, List<CourseDetail> CourseDetails) {
        this.name = name;
        this.description = description;
    }
    public void Add(CourseDetail cd) {
        this.CourseDetails.add(cd);
    }
}
