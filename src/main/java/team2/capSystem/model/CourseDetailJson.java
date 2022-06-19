package team2.capSystem.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CourseDetailJson {
    
    private int lecturerId;
    private int courseDetailId;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;

    public CourseDetailJson(int courseDetailId, String courseName, LocalDate startDate, LocalDate endDate) {
        super();
        this.courseDetailId = courseDetailId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;

    }
}
