package team2.capSystem.services;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public boolean tableExist(){
		return courseRepository.existsBy();
	}

    public boolean courseDetailTableExist(){
		return cdRepository.existsBy();
	}

    public void createCourse(String name, String desc){
        courseRepository.save(new Course(name, desc));
    };

    public void updateCourse(Course course, String change){

    };

    public void getCourse(int id){

     };


    public Course getCourseByName(String name){
        return courseRepository.findCourseByName(name);
    };


    public void addLecturer(CourseDetail courseDetail, Lecturer lecturer){
        String username = lecturer.getUsername();
        Lecturer newLecturer = lecturerRepository.findByUsername(username);
        newLecturer.addCourseDetail(courseDetail);
        //cdRepository.save(courseDetail);
        lecturerRepository.save(newLecturer);
    };


    public CourseDetail createCourseDetail(Course course, LocalDate start, LocalDate end){
        CourseDetail cd = new CourseDetail(start, end, course);
        cdRepository.save(cd);
        return cd;
    };

    public CourseDetail findExactCourseDetail(Course course, LocalDate start, LocalDate end){
        return cdRepository.findByCourseNameAndTime(course, start, end);
    };


    public StudentCourse addStudentToCourseDetail(CourseDetail courseDetail, Student student, double gpa){
        CourseDetail cd = cdRepository.findById(courseDetail.getId()).get();
        Student currStudent = studentRepository.findByUsername(student.getUsername());
        StudentCourse sc = new StudentCourse(currStudent, courseDetail, gpa);
        scRepository.save(sc);
        return sc;
    };

    public List<CourseDetail> getAllCourseDetails() {
        return cdRepository.findAll();
    }

	public List<Course> getAllCourses(){
		return courseRepository.findAll();
	}

	public Course saveCourse(Course Course){
		return courseRepository.save(Course);
	}

	public Course findCourseById(int id){
		return courseRepository.findById(id).get();
	};

	public void deleteCourseById(int id){
		Course course = courseRepository.findById(id).get();
		if(course != null){
            List<CourseDetail> cdList = course.getCourseDetails();
            List<StudentCourse> scList = new ArrayList<StudentCourse>();

            for (CourseDetail cd : cdList){
                for(StudentCourse sc : cd.getStudent_course()){
                    scList.add(sc);
                }
            }

            for (StudentCourse sc : scList){
                Student student = studentRepository.getReferenceById(sc.getStudent().getStudentId());
                CourseDetail cd = cdRepository.getReferenceById(sc.getCourse().getId());
                student.getCourses().remove(sc);
                cd.getStudent_course().remove(sc);
                studentRepository.save(student);
                cdRepository.save(cd);
                scRepository.delete(sc);
            }
            for (CourseDetail cd: cdList){
                for (Lecturer lecturer : cd.getLecturers()){
                    lecturer.getCourses().remove(cd);
                    lecturerRepository.save(lecturer);
                }
            }
            course.getCourseDetails().clear();
            courseRepository.save(course);
			courseRepository.delete(course);
		}
		else{
			throw new NullPointerException();
		}
	};


    public CourseDetail findCourseDetailById(int id){

        return cdRepository.findById(id).get();
    };

    public void updateCourseDetails(Course course){
        Course currCourse = courseRepository.findById(course.getCourseId()).get();
        currCourse.setDescription(course.getDescription());
        currCourse.setName(course.getName());

        for(CourseDetail cd : currCourse.getCourseDetails()){
            cd.setCourse(currCourse);
            cdRepository.save(cd);
        }
        courseRepository.save(currCourse);
    };


    public List<CourseDetail> findAllCourseDetailsByCourseId(int id){
        return cdRepository.findAll().stream().filter(course -> course.getCourse().getCourseId() == id).collect(Collectors.toList());
    }

}
