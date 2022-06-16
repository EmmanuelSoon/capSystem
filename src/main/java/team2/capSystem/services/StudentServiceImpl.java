package team2.capSystem.services;


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


}
