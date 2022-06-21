package team2.capSystem.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import team2.capSystem.helper.userSessionDetails;
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

	public Lecturer findLecturerByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteLecturer(Lecturer lecturer) {
		// List<CourseDetail> cdList = lecturer.getCourses();
		// for (CourseDetail cd : cdList){
		// cd.removeLecturer(lecturer);
		// cdRepository.save(cd);
		// }
		// lecturerRepository.delete(lecturer);
		lecturer.setActive(false);
		lecturerRepository.save(lecturer);
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

	// for the controller
	public List<StudentCourse> getSCList(CourseDetail cd) {
		return scRepository.findByCourse(cd);
	}

	public List<StudentCourse> getCourseListTakenByStudent(int id) {
		return scRepository.findSCByStudentId(id);
	}

	public List<CourseDetail> findCoursesByLecturerId(int id) {
		Lecturer lecturer = lecturerRepository.getReferenceById(id);
		System.out.println(lecturer.getCourses().size());
		return lecturer.getCourses();
	};

	public boolean removeLecturerFromCourseDetail(CourseDetail cd, Lecturer lecturer) {
		if(lecturer.getCourses().size() > 1){
			lecturer.getCourses().remove(cd);
			lecturerRepository.save(lecturer);
			return true;
		}
		else {
			return false;
		}

	};

	public Lecturer addCourseDetailToLecturer(Lecturer lecturer, CourseDetail cd){
		lecturer.getCourses().add(cd);
		lecturerRepository.save(lecturer);
		return lecturerRepository.findByUsername(lecturer.getUsername());
	};

	public List<CourseDetail> findAvailableCoursesByLecturerId(int id){
		List<CourseDetail> cdList = cdRepository.findByStartDateAfter(LocalDate.now());
		List<CourseDetail> results = new ArrayList<CourseDetail>();
		Lecturer currLecturer = lecturerRepository.getReferenceById(id);
		
		for(CourseDetail cd: cdList){
			if(!currLecturer.getCourses().contains(cd)){
				results.add(cd);
			}
		}
		return results;
	};

}
