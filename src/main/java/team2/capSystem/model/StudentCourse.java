package team2.capSystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@IdClass(StudentCourseId.class)
public class StudentCourse{

    //Child
    @Id
    @ManyToOne
    private Student student;
    
    //Child
    @Id
    @ManyToOne
    @JoinColumn(name="course_batch_id")
    private CourseDetail course;
    private double gpa;

    public StudentCourse(Student student, CourseDetail course, double gpa) {
        this.student = student;
        this.course = course;
        this.gpa = gpa;
    }
    public StudentCourse(Student student, CourseDetail course) {
        this.student = student;
        this.course = course;
        this.gpa = -1.0;
    }
}
