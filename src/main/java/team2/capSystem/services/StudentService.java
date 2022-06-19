package team2.capSystem.services;

import java.util.List;

import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.*;

public interface StudentService {
	boolean tableExist();

	boolean scTableExist();
	
	void createStudent(String username, String password, String name, String email);
	
	Student findStudentByUsername(String username);
	
	Student getStudent(User u);

	StudentCourse AddCourseDetailToStudent(Student student, CourseDetail courseDetail, double gpa);

	List<Student> getAllStudents();

	Student saveStudent(Student student);

	Student findStudentById(int id);

	void deleteStudentById(int id);
	
	List<StudentCourse> getStudentCourseBySession(userSessionDetails usd);

	List<CourseDetail> getStudentAvailCourses(userSessionDetails usd, String keyword, String startDate, String endDate);

	void studentEnrollCourse(userSessionDetails usd, int courseDetailId);

	List<StudentCourse> findCoursesByStudentId(int id);

	Student getStudentProfile(userSessionDetails usd);

	List<StudentCourseJson> convertSCToJson(List<StudentCourse> scList);

	StudentCourse findCourseByCourseIdStudentId(int courseId, int studentId);

	void removeStudentCourse(StudentCourse sc);

	
}