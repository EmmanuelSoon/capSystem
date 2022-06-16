package team2.capSystem.services;


import javax.annotation.*;
import org.springframework.stereotype.*;

import team2.capSystem.model.Lecturer;
import team2.capSystem.model.User;
import team2.capSystem.repo.*;

@Service
public class LecturerServiceImpl implements LecturerService {
	
	@Resource
	private LecturerRepository lecturerRepository;


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


	@Override
	public Lecturer getLecturer(User u) {
		return lecturerRepository.findLecturerByUsernameAndPassword(u.getUsername(), u.getPassword());
	}

	public Lecturer findByUsername(String username){
		return lecturerRepository.findByUsername(username);
	};


	@Override
	public Lecturer findLecturerByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
