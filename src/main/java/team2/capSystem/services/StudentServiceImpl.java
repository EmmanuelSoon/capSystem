package team2.capSystem.services;


import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import team2.capSystem.exceptions.RequestException;
import team2.capSystem.exceptions.TestException;
import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.*;
import team2.capSystem.repo.*;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentCourseRepository scRepository;

	@Resource
	CourseDetailRepository cdRepository;

	@Resource
	private StudentRepository studentRepository;

	@Resource
	CourseRepository cRepo;

	public boolean tableExist(){
		return studentRepository.existsBy();
	}

	public boolean scTableExist(){
		return scRepository.existsBy();
	}

	 public void createStudent(String username, String password, String name, String email){
		studentRepository.save(new Student(username,password, name, email));
	 };


	public Student findStudentByUsername(String username) {
		return studentRepository.findStudentByUsername(username);
	}
	
	public Student getStudent(User u) {
		return studentRepository.findStudentByUsernameAndPassword(u.getUsername(), u.getPassword());
	}

	public StudentCourse AddCourseDetailToStudent(Student student, CourseDetail courseDetail, double gpa){
		StudentCourse sc = new StudentCourse( student, courseDetail, gpa);
        scRepository.save(sc);
        return sc;
	};

	public List<Student> getAllStudents(){
		return studentRepository.findAll();
	};

	public Student saveStudent(Student student){
		return studentRepository.save(student);
	}

	public Student findStudentById(int id){
		return studentRepository.findById(id).get();
	}

	public void deleteStudentById(int id){
		Student student = studentRepository.findById(id).get();
		if(student != null){
			studentRepository.delete(student);
		}
		else{
			throw new NullPointerException();
		}
	}


	public void addCourseDetailToStudent(Student s, CourseDetail c) {
		s.getCourses().add(new StudentCourse(s, c));
		studentRepository.save(s);
	}

	public List<StudentCourse> findCoursesByStudentId(int id){
		return scRepository.findSCByStudentId(id);
	};


	//Student controller methods

	public List<StudentCourse> getStudentCourseBySession(userSessionDetails usd){
		List<StudentCourse> studentCourseList= scRepository.findSCByStudentId(usd.getUserId());
		studentCourseList = studentCourseList.stream()
				.filter(x->x.getGpa() > 0)
				.collect(Collectors.toList());
		return studentCourseList;
	}

	public List<CourseDetail> getStudentAvailCourses(userSessionDetails usd, String keyword, String startDate, String endDate){
		List<StudentCourse> takenCourse = getStudentCourseBySession(usd);
		List<CourseDetail> courseList = new ArrayList<CourseDetail>();
		List<CourseDetail> availCourse = new ArrayList<CourseDetail>();

		if (startDate != null && endDate == null){
			courseList = cdRepository.findByStartDateAfter(LocalDate.parse(startDate));
		}
		else if(startDate == null && endDate !=null){
			courseList = cdRepository.findByStartDateAfterAndEndDateBefore(LocalDate.parse(startDate), LocalDate.parse(endDate));
		}
		else if (startDate != null && endDate !=null){
			courseList = cdRepository.findByEndDateBefore(LocalDate.parse(endDate));
		}
		else{
			courseList = cdRepository.findByStartDateAfter(LocalDate.now());
		}
		
		for (StudentCourse sc : takenCourse){
			courseList = courseList.stream()
               .filter(x -> x.getCourse().getCourseId() != sc.getCourse().getCourse().getCourseId())
               .collect(Collectors.toList());
		}

		if (keyword != null && keyword != ""){
			for (CourseDetail course: courseList){
				if (course.getCourse().getName().toLowerCase().contains(keyword.toLowerCase()) || course.getCourse().getDescription().toLowerCase().contains(keyword.toLowerCase())){
					availCourse.add(course);
				}
			}
		}
		else{
			availCourse = courseList;
		}

		return availCourse;
	}

	public void studentEnrollCourse(userSessionDetails usd, int courseDetailId){
		//double check the edge cases here
		Student student = getStudent(usd.getUser());
		CourseDetail cd = cdRepository.findById(courseDetailId).get();
		List<StudentCourse> enrolled = scRepository.findByCourse(cd);
		if (cd.getMaxSize() > enrolled.size()){
		 	addCourseDetailToStudent(student, cd);
		}
		else{
			throw new RequestException("Unable to enroll, class is full");
		}
	}

	public Student getStudentProfile(userSessionDetails usd){
		return getStudent(usd.getUser());
	}

	public List<StudentCourseJson> convertSCToJson(List<StudentCourse> scList){
		List<StudentCourseJson> scJsonList = new ArrayList<StudentCourseJson>();

        for (StudentCourse sc : scList){
            int studentId = sc.getStudent().getStudentId();
            int courseDetailId = sc.getCourse().getId();
            String courseName = sc.getCourse().getCourse().getName();
            LocalDate startDate = sc.getCourse().getStartDate();
            LocalDate endDate = sc.getCourse().getEndDate();
            double gpa = sc.getGpa();
            StudentCourseJson scJson = new StudentCourseJson(studentId, courseDetailId, courseName, startDate, endDate, gpa);
            scJsonList.add(scJson);
        }

        return scJsonList;
	};

	public StudentCourse findCourseByCourseIdStudentId(int courseId, int studentId){
		return scRepository.findCourseByCourseIdStudentId(courseId, studentId);
	};

	public void removeStudentCourse(StudentCourse sc){
		Student student = studentRepository.getReferenceById(sc.getStudent().getStudentId());
		CourseDetail cd = cdRepository.getReferenceById(sc.getCourse().getId());
		student.getCourses().remove(sc);
		cd.getStudent_course().remove(sc);
		studentRepository.save(student);
		cdRepository.save(cd);
		scRepository.delete(sc);
	};






}
