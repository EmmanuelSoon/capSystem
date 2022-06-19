package team2.capSystem.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class StudentCourseJson {
    
    private int studentId;
    private int courseDetailId;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double gpa;

    public StudentCourseJson(int studentId, int courseDetailId, String courseName, LocalDate startDate, LocalDate endDate, double gpa) {
        super();
        this.studentId = studentId;
        this.courseDetailId = courseDetailId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.gpa = gpa;

    }
}
