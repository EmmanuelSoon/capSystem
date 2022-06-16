package team2.capSystem.services;

import java.time.LocalDate;
import java.util.List;

import team2.capSystem.model.Course;
import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.Lecturer;

public interface CourseService {
    void createCourse(String name, String desc);
    void updateCourse(Course course, String change);
    void getCourse(int id);

    void addCourseDetail(CourseDetail courseDetail, List<Lecturer> lectList);
    CourseDetail createCourseDetail(Course course, LocalDate start, LocalDate end);


}
