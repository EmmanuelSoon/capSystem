package team2.capSystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CourseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_batch_id")
    private int id;
    private Date startDate;
    private Date endDate;
    @ManyToMany(mappedBy = "courses")
    private List<Lecturer> lecturers = new ArrayList<>();
    @OneToMany(mappedBy = "course")
    private List<StudentCourse> student_course = new ArrayList<>();
    @ManyToOne
    private Course course;

    public CourseDetail(Date startDate, Date endDate, Course course) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.course = course;
    }
}
