package team2.capSystem.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team2.capSystem.model.*;


@Service
@Transactional
public interface CourseService {
    
    boolean tableExist();
    boolean courseDetailTableExist();

    //Courses
    void createCourse(String name, String desc);
    void updateCourse(Course course, String change);
    void getCourse(int id);
    void deleteCourse(int id);
    Course getCourseByName(String name);
    

    //Course Details 
    void addLecturer(CourseDetail courseDetail, Lecturer lecturer);
    CourseDetail createCourseDetail(Course course, LocalDate start, LocalDate end);
    CourseDetail findExactCourseDetail(Course course, LocalDate start, LocalDate end);

    
    
    //StudentCourses 
    StudentCourse addStudentToCourseDetail(CourseDetail courseDetail, Student student, double gpa);


}
