package team2.capSystem.services;

import java.util.ArrayList;
import java.util.List;

import team2.capSystem.helper.lecturerCourseStudentSearch;
import team2.capSystem.helper.lecturerCoursesHelper;
import team2.capSystem.helper.nominalRoll;
import team2.capSystem.helper.studentTranscript;
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

	// controller
	List<StudentCourse> getSCList(CourseDetail cd);

	List<StudentCourse> getCourseListTakenByStudent(int id);

	List<CourseDetail> findCoursesByLecturerId(int id);

	List<CourseDetail> getUpcomingCoursesByLecturer(Lecturer lecturer);

	List<CourseDetail> getCompletedCoursesByLecturer(Lecturer lecturer);

	List<CourseDetail> getStartedCourse(Lecturer lecturer);

	List<CourseDetail> getOnGoingCourseByLecturer(Lecturer lecturer);
	
	
	CourseDetail getCourseDetailByBatchIdByLecturer(int batchId, Lecturer lecturer);
	
	StudentCourse getSCByBatchId(int batchId, List<StudentCourse> scList);


	lecturerCoursesHelper createLecturerCoursesHelper(CourseDetail cd, Course course);

	nominalRoll createNominalRoll(Student student, StudentCourse sc);

	studentTranscript createStudentTransciptRec(StudentCourse sc);

	// -----

	boolean removeLecturerFromCourseDetail(CourseDetail cd, Lecturer lecturer);

	Lecturer getLecturerProfile(userSessionDetails usd);

	Lecturer addCourseDetailToLecturer(Lecturer lecturer, CourseDetail cd);

	List<CourseDetail> findAvailableCoursesByLecturerId(int id);
}