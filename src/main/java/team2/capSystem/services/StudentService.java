package team2.capSystem.services;

import team2.capSystem.model.*;

public interface StudentService {
	boolean tableExist();

	boolean scTableExist();
	
	void createStudent(String username, String password, String name, String email);
	
	Student findStudentByUsername(String email);
	
	Student getStudent(User u);

	StudentCourse AddCourseDetailToStudent(Student student, CourseDetail courseDetail, double gpa);
	void saveStudent(Student updatedStd);
}