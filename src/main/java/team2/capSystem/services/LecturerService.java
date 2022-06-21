package team2.capSystem.services;

import java.util.List;

import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.*;

public interface LecturerService {

	boolean tableExist();

	void createLecturer(String username, String password, String name, String email);

	Lecturer findLecturerByEmail(String email);

	Lecturer getLecturer(User u);

	Lecturer findByUsername(String username);

	void deleteLecturer(Lecturer lecturer);

	List<Lecturer> getAllLecturers();

	Lecturer saveLecturer(Lecturer lecturer);

	Lecturer findLecturerById(int id);

	void deleteLecturerById(int id);

	
	List<StudentCourse> getSCList(CourseDetail cd);

	List<StudentCourse> getCourseListTakenByStudent(int id);
	
	List<CourseDetail> findCoursesByLecturerId(int id);
	
	Lecturer getLecturerProfile(userSessionDetails usd);
	
	void removeLecturerFromCourseDetail(CourseDetail cd, Lecturer lecturer);
	
	Lecturer addCourseDetailToLecturer(Lecturer lecturer, CourseDetail cd);

	List<CourseDetail> findAvailableCoursesByLecturerId(int id);
}