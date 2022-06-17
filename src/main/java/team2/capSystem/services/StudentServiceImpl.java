package team2.capSystem.services;


import java.util.List;

import javax.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;


import team2.capSystem.model.*;
import team2.capSystem.repo.*;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentCourseRepository scRepository;

	@Resource
	private StudentRepository studentRepository;

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
			student.setActive(false);
			student.getCourses().clear();
			studentRepository.save(student);
		}
		else{
			throw new NullPointerException();
		}
	}


	public void addCourseDetailToStudent(Student s, CourseDetail c) {
		s.getCourses().add(new StudentCourse(s, c));
		studentRepository.save(s);
	}

}
