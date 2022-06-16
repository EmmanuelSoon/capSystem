package team2.capSystem.services;

import java.time.LocalDate;

import team2.capSystem.model.Course;

public interface CourseService {
    void createCourse(String name, String desc);
    void updateCourse(Course course, String change);
    void getCourse(int id);

    void addCourseDetail(Course course, LocalDate start, LocalDate end);


}
