package team2.capSystem.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.*;

import team2.capSystem.helper.*;
import team2.capSystem.model.*;
import team2.capSystem.repo.*;

@Service
public class LecturerServiceImpl implements LecturerService {

	@Resource
	private LecturerRepository lecturerRepository;

	@Resource
	private CourseDetailRepository cdRepository;

	@Resource
	private StudentCourseRepository scRepository;

//	@Override
//	public Lecturer findLecturerByUsername(String username) {
//		return lecturerRepository.findLecturerByUsername(username);
//	}

	public boolean tableExist() {
		return lecturerRepository.existsBy();
	}

	public void createLecturer(String username, String password, String name, String email) {
		lecturerRepository.save(new Lecturer(username, password, name, email));
	};

	public Lecturer getLecturer(User u) {
		return lecturerRepository.findLecturerByUsernameAndPassword(u.getUsername(), u.getPassword());
	}

	public Lecturer findByUsername(String username) {
		return lecturerRepository.findByUsername(username);
	};


	public List<Lecturer> getAllLecturers() {
		return lecturerRepository.findAll();
	}

	public Lecturer saveLecturer(Lecturer lecturer) {
		return lecturerRepository.save(lecturer);
	}

	public Lecturer findLecturerById(int id) {
		return lecturerRepository.findById(id).get();
	};

	public void deleteLecturerById(int id) {
		Lecturer lecturer = lecturerRepository.findById(id).get();
		if (lecturer != null) {
			lecturerRepository.delete(lecturer);
		} else {
			throw new NullPointerException();
		}
	};

	public void delete(Lecturer l) {
		l.getCourses().clear();
		l.setActive(false);
		lecturerRepository.save(l);
	}

	// Lecturer Controller Methods------
	public List<StudentCourse> getSCList(CourseDetail cd) {
		return scRepository.findByCourse(cd);
	}

	public List<StudentCourse> getCourseListTakenByStudent(int id) {
		return scRepository.findSCByStudentId(id);
	}

	public List<CourseDetail> findCoursesByLecturerId(int id) {
		Lecturer lecturer = lecturerRepository.getReferenceById(id);
		return lecturer.getCourses();
	};

	public Lecturer getLecturerProfile(userSessionDetails usd) {
		return getLecturer(usd.getUser());
	}

	// streams filters
	public List<CourseDetail> getUpcomingCoursesByLecturer(Lecturer lecturer) {
		return lecturer.getCourses().stream().filter(x -> x.getStartDate().isAfter(LocalDate.now())).distinct()
				.collect(Collectors.toList());
	}

	public List<CourseDetail> getCompletedCoursesByLecturer(Lecturer lecturer) {
		return lecturer.getCourses().stream().filter(x -> x.getEndDate().isBefore(LocalDate.now())).distinct()
				.collect(Collectors.toList());
	}

	public List<CourseDetail> getStartedCourse(Lecturer lecturer) {
		return lecturer.getCourses().stream().filter(x -> x.getStartDate().isBefore(LocalDate.now())).distinct()
				.collect(Collectors.toList());
	}

	public List<CourseDetail> getOnGoingCourseByLecturer(Lecturer lecturer) {
		return getStartedCourse(lecturer).stream().filter(x -> x.getEndDate().isAfter(LocalDate.now())).distinct()
				.collect(Collectors.toList());
	}
	
	public StudentCourse getSCByBatchId(int batchId, List<StudentCourse> scList) {
		return scList.stream().filter(x -> x.getCourse().getId() == batchId).findFirst().get();
	}


	public CourseDetail getCourseDetailByBatchIdByLecturer(int batchId, Lecturer lecturer) {
		return lecturer.getCourses().stream().filter(x -> x.getId() == batchId).findFirst().get();
	}

	
	// lecturerCoursesHelper builder
	public lecturerCoursesHelper createLecturerCoursesHelper(CourseDetail cd, Course course) {
		return lecturerCoursesHelper.builder().courseBatchId(cd.getId()).courseId(course.getCourseId())
				.courseName(course.getName()).courseDescription(course.getDescription()).startDate(cd.getStartDate())
				.endDate(cd.getEndDate()).build();
	}

	// nominalRoll builder
	public nominalRoll createNominalRoll(Student student, StudentCourse sc) {
		return nominalRoll.builder().courseBatchId(sc.getCourse().getId())
				.studentId(student.getStudentId()).studentName(student.getName())
				.studentEmail(student.getEmail()).gpa(sc.getGpa()).build();
	}

	public studentTranscript createStudentTransciptRec(StudentCourse sc) {
		return studentTranscript.builder()
				.courseBatchId(sc.getCourse().getId())
				.courseName(sc.getCourse().getCourse().getName())
				.dateOfCompletion(sc.getCourse().getEndDate()).gpa(sc.getGpa()).build();
	}
	
	

	// -------------------------

	public boolean removeLecturerFromCourseDetail(CourseDetail cd, Lecturer lecturer) {
		if (cd.getLecturers().size() > 1) {
			lecturer.getCourses().remove(cd);
			lecturerRepository.save(lecturer);
			return true;
		} else {
			return false;
		}

	};

	public Lecturer addCourseDetailToLecturer(Lecturer lecturer, CourseDetail cd) {
		lecturer.getCourses().add(cd);
		lecturerRepository.save(lecturer);
		return lecturerRepository.findByUsername(lecturer.getUsername());
	};

	public List<CourseDetail> findAvailableCoursesByLecturerId(int id) {
		List<CourseDetail> cdList = cdRepository.findByStartDateAfter(LocalDate.now());
		List<CourseDetail> results = new ArrayList<CourseDetail>();
		Lecturer currLecturer = lecturerRepository.getReferenceById(id);

		for (CourseDetail cd : cdList) {
			if (!currLecturer.getCourses().contains(cd)) {
				results.add(cd);
			}
		}
		return results;
	};

	public Lecturer setLecturerPassword(int id, userChangePassword userpass) {
		Lecturer lecturer= lecturerRepository.findById(id).get();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String setPass = encoder.encode(userpass.getNewPassword());
		lecturer.setPassword(setPass);
		lecturerRepository.save(lecturer);
		return lecturer;
	}

}
