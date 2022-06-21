package team2.capSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    //Parent
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Getter(onMethod_=@JsonIgnore) 
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

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
