package team2.capSystem.services;

import java.util.List;

import team2.capSystem.exceptions.AfterTwoWeekUnenrollmentException;
import team2.capSystem.exceptions.ClassFullException;
import team2.capSystem.exceptions.ClassStartedException;
import team2.capSystem.exceptions.CourseEndedException;
import team2.capSystem.exceptions.GpaExistException;
import team2.capSystem.helper.courseDetailSearchQuery;
import team2.capSystem.helper.userChangePassword;
import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.*;

public interface StudentService {
	boolean tableExist();

	boolean scTableExist();
	
	void createStudent(String username, String password, String name, String email);
	
	Student getStudent(int studentId);
	
	Student findStudentByUsername(String username);
	
	Student getStudent(User u);

	boolean addCourseDetailToStudent(Student student, CourseDetail courseDetail);

	List<Student> getAllStudents();

	Student saveStudent(Student student);

	Student findStudentById(int id);

	List<StudentCourse> findStudentCoursesFinish(int id, String keyword);

	List<StudentCourse> findStudentCoursesOngoing(int id, String keyword);

	void deleteStudentById(int id);
	
	List<StudentCourse> getStudentCourseBySession(userSessionDetails usd);

	List<CourseDetail> getStudentAvailCourses(userSessionDetails usd, courseDetailSearchQuery search);

	void studentEnrollCourse(userSessionDetails usd, int courseDetailId) throws ClassStartedException, ClassFullException;

	void studentUnenrollCourse(int studcourseId, userSessionDetails usd) throws CourseEndedException, GpaExistException, AfterTwoWeekUnenrollmentException;

	List<StudentCourse> findCoursesByStudentId(int id);

	public List<StudentCourse> getClassList(int courseDetailId);

	public List<Lecturer> getLecturerList(int courseDetailId);

	Student getStudentProfile(userSessionDetails usd);

	List<StudentCourseJson> convertSCToJson(List<StudentCourse> scList);

	StudentCourse findCourseByCourseIdStudentId(int courseId, int studentId);

	void removeStudentCourse(StudentCourse sc);

	List<CourseDetail> findAvailableCoursesByStudentId(int id);

	Double getAverageGPA(int id);

	public Student setStudentPassword(int id, userChangePassword userpass);
	
	void updateStudentCourseGPA(int coursebatchID, int studentId, double selectedGPA);
}

