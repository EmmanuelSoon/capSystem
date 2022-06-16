package team2.capSystem.services;


import javax.annotation.*;
import org.springframework.stereotype.*;

import team2.capSystem.model.Lecturer;
import team2.capSystem.repo.*;

@Service
public class LecturerServiceImpl implements LecturerService {
	
	@Resource
	private LecturerRepository lecturerRepository;


	@Override
	public Lecturer findLecturerByUsername(String username) {
		return lecturerRepository.findLecturerByUsername(username);
	}

}
