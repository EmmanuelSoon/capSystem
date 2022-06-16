package team2.capSystem.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team2.capSystem.model.Course;
import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.Lecturer;

@Service
@Transactional
public interface CourseService {
    //Courses
    void createCourse(String name, String desc);
    void updateCourse(Course course, String change);
    void getCourse(int id);
    void deleteCourse(int id);

    //Course Details 
    void addLecturer(CourseDetail courseDetail, Lecturer lecturer);
    CourseDetail createCourseDetail(Course course, LocalDate start, LocalDate end);
    
    
    //StudentCourses 

}
