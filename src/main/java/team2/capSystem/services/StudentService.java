package team2.capSystem.services;

import team2.capSystem.model.*;

public interface StudentService {
		
	Student findStudentByUsername(String email);
	
	Student getStudent(User u);

}