package team2.capSystem.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team2.capSystem.model.*;


@Service
public interface CourseService {
    
    boolean tableExist();
    boolean courseDetailTableExist();

    //Courses
    void createCourse(String name, String desc);
    void updateCourse(Course course, String change);
    void getCourse(int id);
    Course getCourseByName(String name);
    void updateCourseDetails(Course course);
    
    List<Course> getAllCourses();
	Course saveCourse(Course course);
	Course findCourseById(int id);
	void deleteCourseById(int id);

    //Course Details 
    void addLecturer(CourseDetail courseDetail, Lecturer lecturer);
    CourseDetail createCourseDetail(Course course, LocalDate start, LocalDate end);
    CourseDetail findExactCourseDetail(Course course, LocalDate start, LocalDate end);
    CourseDetail findCourseDetailById(int id);
    
    
    //StudentCourses 
    StudentCourse addStudentToCourseDetail(CourseDetail courseDetail, Student student, double gpa);

   
	
	//List<StudentCourse> getClassList();

}
