package team2.capSystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.time.LocalDate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.*;

import org.hibernate.resource.beans.container.internal.CdiBeanContainerExtendedAccessImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.*;

import team2.capSystem.exceptions.RequestException;
import team2.capSystem.exceptions.TestException;
import team2.capSystem.helper.courseDetailSearchQuery;
import team2.capSystem.helper.userChangePassword;
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

	public boolean tableExist() {
		return studentRepository.existsBy();
	}

	public boolean scTableExist() {
		return scRepository.existsBy();
	}

	public void createStudent(String username, String password, String name, String email) {
		studentRepository.save(new Student(username, password, name, email));
	};

	public Student findStudentByUsername(String username) {
		return studentRepository.findStudentByUsername(username);
	}

	public Student getStudent(User u) {
		return studentRepository.findStudentByUsernameAndPassword(u.getUsername(), u.getPassword());
	}

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	};

	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}

	public Student findStudentById(int id) {
		return studentRepository.findById(id).get();
	}

	public void deleteStudentById(int id) {
		Student student = studentRepository.findById(id).get();
		if (student != null) {
			List<StudentCourse> scList = student.getCourses();
			for (StudentCourse sc : scList) {
				CourseDetail cd = cdRepository.getReferenceById(sc.getCourse().getId());
				cd.getStudent_course().remove(sc);
				cdRepository.save(cd);
			}
			student.getCourses().clear();
			studentRepository.save(student);
			studentRepository.delete(student);
		} else {
			throw new NullPointerException();
		}
	}

	public StudentCourse addCourseDetailToStudent(Student s, CourseDetail c) {
		StudentCourse sc = new StudentCourse(s, c);
		s.getCourses().add(sc);
		studentRepository.save(s);
		return sc;
	}

	// Student controller methods

	public List<StudentCourse> findCoursesByStudentId(int id) {
		return scRepository.findSCByStudentId(id);
	}

	public List<StudentCourse> findStudentCoursesFinish(int id, String keyword) {
		List<StudentCourse> courseList = findCoursesByStudentId(id);
		if (keyword != null && keyword != ""){
			Predicate<StudentCourse> nameHas = x -> x.getCourse().getCourse().getName().toLowerCase().contains(keyword);
			Predicate<StudentCourse> descHas = x -> x.getCourse().getCourse().getDescription().toLowerCase().contains(keyword);
			courseList = courseList.stream()
			.filter(nameHas.or(descHas))
			.filter(x -> x.getCourse().getEndDate().isBefore(LocalDate.now()))
			.collect(Collectors.toList());
		}
		else{
			courseList = courseList.stream().filter(x -> x.getCourse().getEndDate().isBefore(LocalDate.now())).collect(Collectors.toList());
		}
		return courseList;

	}
	
	public List<StudentCourse> findStudentCoursesOngoing(int id, String keyword) {
		List<StudentCourse> courseList = findCoursesByStudentId(id);
		if (keyword != null && keyword != ""){
			Predicate<StudentCourse> nameHas = x -> x.getCourse().getCourse().getName().toLowerCase().contains(keyword);
			Predicate<StudentCourse> descHas = x -> x.getCourse().getCourse().getDescription().toLowerCase().contains(keyword);
			courseList = courseList.stream()
			.filter(nameHas.or(descHas))
			.filter(x -> x.getCourse().getEndDate().isAfter(LocalDate.now()))
			.collect(Collectors.toList());
		}
		else{
			courseList = courseList.stream().filter(x -> x.getCourse().getEndDate().isAfter(LocalDate.now())).collect(Collectors.toList());
		}
		return courseList;
	}
	public Double getAverageGPA(int id) {
		List<StudentCourse> courseList = findCoursesByStudentId(id);
		courseList = courseList.stream().filter(x -> x.getGpa() != -1).collect(Collectors.toList());
		Double averageGPA = courseList.stream().mapToDouble(x -> x.getGpa()).average().getAsDouble();
		return averageGPA;
	}


	public List<StudentCourse> getStudentCourseBySession(userSessionDetails usd) {
		List<StudentCourse> studentCourseList = scRepository.findSCByStudentId(usd.getUserId());
		return studentCourseList;
	}

	public List<CourseDetail> getStudentAvailCourses(userSessionDetails usd, courseDetailSearchQuery search) {
		List<StudentCourse> takenCourse = findCoursesByStudentId(usd.getUserId());
		List<CourseDetail> courseList = new ArrayList<CourseDetail>();
		List<CourseDetail> availCourse = new ArrayList<CourseDetail>();

		if (!search.startNullOrEmpty()) {
			if (!search.endNullOrEmpty()) {
				courseList = cdRepository.findByStartDateAfterAndEndDateBefore(LocalDate.parse(search.getStartDate()), LocalDate.parse(search.getEndDate()));
			} else if (search.endNullOrEmpty()) {
				courseList = cdRepository.findByStartDateAfter(LocalDate.parse(search.getStartDate()));
			}
		} else if (!search.endNullOrEmpty()) {
			courseList = cdRepository.findByEndDateBefore(LocalDate.parse(search.getEndDate()));
		} else {
			courseList = cdRepository.findByStartDateAfter(LocalDate.now());
		}

		for (StudentCourse sc : takenCourse) {
			courseList = courseList.stream()
					.filter(x -> x.getCourse().getCourseId() != sc.getCourse().getCourse().getCourseId())
					.collect(Collectors.toList());
		}
		String keyword = search.getKeyword();
		if (keyword != null && keyword != "") {
			for (CourseDetail course : courseList) {
				if (course.getCourse().getName().toLowerCase().contains(keyword.toLowerCase())
						|| course.getCourse().getDescription().toLowerCase().contains(keyword.toLowerCase())) {
					availCourse.add(course);
				}
			}
		} else {
			availCourse = courseList;
		}

		return availCourse;
	}

	public void studentEnrollCourse(userSessionDetails usd, int courseDetailId) {
		// add check for date. do not allow enrollment if start date has passed
		Student student = getStudent(usd.getUser());
		CourseDetail cd = cdRepository.findById(courseDetailId).get();
		List<StudentCourse> enrolled = scRepository.findByCourse(cd);
		if (cd.getMaxSize() > enrolled.size() && cd.getStartDate().isAfter(LocalDate.now())) {
			addCourseDetailToStudent(student, cd);
		} else {
			throw new RequestException("Unable to enroll, class is full");
		}
	}

	public void studentUnenrollCourse(int studcourseId, userSessionDetails usd){
		int studentId = usd.getUserId();
		StudentCourse sc = scRepository.findCourseByCourseIdStudentId(studcourseId, studentId);
		//try catch here?
		removeStudentCourse(sc);
	}

	public List<StudentCourse> getClassList(int courseDetailId){
		CourseDetail cd = cdRepository.findById(courseDetailId).get();
		List<StudentCourse> scList = scRepository.findByCourse(cd);
		return scList;
	}

	public List<Lecturer> getLecturerList(int courseDetailId){
		CourseDetail cd = cdRepository.findById(courseDetailId).get();
		return cd.getLecturers();
	}


	public Student getStudentProfile(userSessionDetails usd) {
		return getStudent(usd.getUser());
	}

	public Student setStudentPassword(int id, userChangePassword userpass) {
		Student student = studentRepository.findById(id).get();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String setPass = encoder.encode(userpass.getNewPassword());
		student.setPassword(setPass);
		studentRepository.save(student);
		return student;
	}

	public List<StudentCourseJson> convertSCToJson(List<StudentCourse> scList) {
		List<StudentCourseJson> scJsonList = new ArrayList<StudentCourseJson>();

		for (StudentCourse sc : scList) {
			int studentId = sc.getStudent().getStudentId();
			int courseDetailId = sc.getCourse().getId();
			String courseName = sc.getCourse().getCourse().getName();
			LocalDate startDate = sc.getCourse().getStartDate();
			LocalDate endDate = sc.getCourse().getEndDate();
			double gpa = sc.getGpa();
			StudentCourseJson scJson = new StudentCourseJson(studentId, courseDetailId, courseName, startDate, endDate,
					gpa);
			scJsonList.add(scJson);
		}

		return scJsonList;
	};

	public StudentCourse findCourseByCourseIdStudentId(int courseId, int studentId) {
		return scRepository.findCourseByCourseIdStudentId(courseId, studentId);
	};

	public void removeStudentCourse(StudentCourse sc) {
		Student student = studentRepository.getReferenceById(sc.getStudent().getStudentId());
		CourseDetail cd = cdRepository.getReferenceById(sc.getCourse().getId());
		student.getCourses().remove(sc);
		cd.getStudent_course().remove(sc);
		studentRepository.save(student);
		cdRepository.save(cd);
		scRepository.delete(sc);
	};

	public List<CourseDetail> findAvailableCoursesByStudentId(int id) {
		List<CourseDetail> cdList = cdRepository.findByStartDateAfter(LocalDate.now());
		List<CourseDetail> results = new ArrayList<CourseDetail>();
		Student currStudent = studentRepository.getReferenceById(id);
		List<Course> CoursesDoneBefore = new ArrayList<Course>();
		for (StudentCourse sc : currStudent.getCourses()) {
			CoursesDoneBefore.add(sc.getCourse().getCourse());
		}

		for (CourseDetail cd : cdList) {
			if (!CoursesDoneBefore.contains(cd.getCourse())) {
				results.add(cd);
			}
		}
		return results;
	};

	public void updateStudentCourseGPA(int coursebatchID, int studentId, double selectedGPA) {

		scRepository.updateStudentCourseGPA(coursebatchID, studentId, selectedGPA);

	};

}
