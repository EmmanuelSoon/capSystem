package team2.capSystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class StudentCourse{

    @Id
    private int id;
    
    @ManyToOne
    private Student student;
    
    @ManyToOne
    @JoinColumn(name="course_batch_id")
    private CourseDetail course;
    private double gpa;

    public StudentCourse(Student student, CourseDetail course, double gpa) {
        this.student = student;
        this.course = course;
        this.gpa = gpa;
    }
}
