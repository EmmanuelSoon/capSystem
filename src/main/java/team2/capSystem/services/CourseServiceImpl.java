package team2.capSystem.services;

import java.time.LocalDate;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team2.capSystem.model.*;
import team2.capSystem.repo.*;

@Service
public class CourseServiceImpl implements CourseService {
    
    
	@Autowired 
	StudentRepository studentRepository;
	
	@Autowired 
	LecturerRepository lecturerRepository;
	
	@Autowired 
	CourseRepository courseRepository;

	@Autowired
	StudentCourseRepository scRepository;

	@Autowired
	CourseDetailRepository cdRepository;



    public void createCourse(String name, String desc){

    };

    public void updateCourse(Course course, String change){

    };

    public void getCourse(int id){

     };

    public void deleteCourse(int id){

    }

    public void addLecturer(CourseDetail courseDetail, Lecturer lecturer){
        lecturer.addCourseDetail(courseDetail);
        lecturerRepository.save(lecturer);
        cdRepository.save(courseDetail);
    };


    public CourseDetail createCourseDetail(Course course, LocalDate start, LocalDate end){
        CourseDetail cd = new CourseDetail(start, end, course);
        cdRepository.save(cd);
        return cd;
    };

    



}
