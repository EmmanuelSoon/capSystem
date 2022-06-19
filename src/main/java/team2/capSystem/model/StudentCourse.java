package team2.capSystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Data
@NoArgsConstructor
@IdClass(StudentCourseId.class)
public class StudentCourse{

    //Child
    @Id
    @ManyToOne 
    @JsonBackReference
    private Student student;
    
    //Child
    @Id
    @ManyToOne
    @JoinColumn(name="course_batch_id")
    @JsonBackReference
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

    @Override
    public String toString(){
        return student.name + ": " + course.getCourse().getName() + ", " + gpa;
    }
}
