package team2.capSystem.model;

import java.io.Serializable;


public class StudentCourseId implements Serializable {
    private CourseDetail course;
    private Student student;

    public StudentCourseId(CourseDetail course, Student student) {
        super();
        this.course = course;
        this.student = student;
    }
    
}
