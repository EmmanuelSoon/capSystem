package team2.capSystem.services;


import java.util.List;
import javax.annotation.*;
import org.springframework.stereotype.*;

import team2.capSystem.model.*;
import team2.capSystem.repo.*;

@Service
public class LecturerServiceImpl implements LecturerService {
	
	@Resource
	private LecturerRepository lecturerRepository;

	@Resource
	private CourseDetailRepository cdRepository;



//	@Override
//	public Lecturer findLecturerByUsername(String username) {
//		return lecturerRepository.findLecturerByUsername(username);
//	}

	public boolean tableExist(){
		return lecturerRepository.existsBy();
	}
	public void createLecturer(String username, String password, String name, String email){
		lecturerRepository.save(new Lecturer(username, password, name, email));
	};

	public Lecturer getLecturer(User u) {
		return lecturerRepository.findLecturerByUsernameAndPassword(u.getUsername(), u.getPassword());
	}

	public Lecturer findByUsername(String username){
		return lecturerRepository.findByUsername(username);
	};

	public Lecturer findLecturerByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteLecturer(Lecturer lecturer){
		List<CourseDetail> cdList = lecturer.getCourses();
		for (CourseDetail cd : cdList){
			cd.removeLecturer(lecturer);
			cdRepository.save(cd);
		}
		lecturerRepository.delete(lecturer);
	};


}
