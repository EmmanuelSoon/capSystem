package team2.capSystem.model;

import lombok.*;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Lecturer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lecturerId;

    //Parent 
    @JsonIgnore
    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "lecture_course",
            joinColumns = @JoinColumn(name = "lecture_id"),
            inverseJoinColumns =  @JoinColumn(name = "course_batch_id")
    )
    private List<CourseDetail> courses = new ArrayList<CourseDetail>();

    @Builder
    public Lecturer(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    

    //Utility methods 

    public void addCourseDetail(CourseDetail course){
        this.courses.add(course);
        //course.getLecturers().add(this);
    }

    public void removeCourseDetail(CourseDetail course){
        this.courses.remove(course);
        course.getLecturers().remove(this);
    }
}
