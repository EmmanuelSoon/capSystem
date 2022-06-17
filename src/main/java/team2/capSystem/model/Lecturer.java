package team2.capSystem.model;

import lombok.*;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Lecturer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lecturerId;

    //Parent (dont have delete because the coursedetail can still continue even when one lecturer is dropped)
    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
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
        course.getLecturers().add(this);
    }

    public void removeCourseDetail(CourseDetail course){
        this.courses.remove(course);
        course.getLecturers().remove(this);
    }
}
