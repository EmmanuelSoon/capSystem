package team2.capSystem.services;


import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

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

	public List<CourseDetail> getStudentAvailCourses(userSessionDetails usd){
		List<StudentCourse> takenCourse = getStudentCourseBySession(usd);
		List<CourseDetail> availCourse = cdRepository.findByStartDateAfter(LocalDate.now());
		
		for (StudentCourse sc : takenCourse){
			availCourse = availCourse.stream()
               .filter(x -> x.getCourse().getCourseId() != sc.getCourse().getCourse().getCourseId())
               .collect(Collectors.toList());
		}

		return availCourse;
	}

	public String studentEnrollCourse(userSessionDetails usd, int courseDetailId){
		//add check for class size limit
		Student student = getStudent(usd.getUser());
		CourseDetail cd = cdRepository.findById(courseDetailId).get();
		List<StudentCourse> enrolled = scRepository.findByCourse(cd);
		if (cd.getMaxSize() > enrolled.size()){
			addCourseDetailToStudent(student, cd);
			return "Enrollment succesful";
		}
		return "Enrollment not succesful";

	}





}
