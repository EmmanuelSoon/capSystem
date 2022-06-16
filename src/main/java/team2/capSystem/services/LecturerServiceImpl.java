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


	@Override
	public Lecturer findLecturerByEmail(String email) {
		return lecturerRepository.findLecturerByEmail(email);
	}


	@Override
	public Lecturer getLecturer(User u) {
		return lecturerRepository.findLecturerByUsernameAndPassword(u.getUsername(), u.getPassword());
	}

}
