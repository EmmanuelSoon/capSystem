package team2.capSystem.model;

import java.io.Serializable;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StudentCourseId implements Serializable {
    private int course;
    private int student;

    public StudentCourseId(int courseId, int studentId) {
        super();
        this.course = courseId;
        this.student = studentId;
    }
    
}
