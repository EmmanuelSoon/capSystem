package team2.capSystem.model;

import lombok.*;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class Lecturer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lecturerId;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "lecture_course",
            joinColumns = @JoinColumn(name = "lecture_id"),
            inverseJoinColumns =  @JoinColumn(name = "course_batch_id")
    )
    private List<CourseDetail> courses = new ArrayList<>();

    public Lecturer(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
