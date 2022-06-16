package team2.capSystem.services;


import javax.annotation.*;
import org.springframework.stereotype.*;

import team2.capSystem.model.Student;
import team2.capSystem.repo.*;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Resource
	private StudentRepository studentRepository;


	@Override
	public Student findStudentByEmail(String email) {
		return studentRepository.findStudentByEmail(email);
	}

}
