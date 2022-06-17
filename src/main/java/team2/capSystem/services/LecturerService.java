package team2.capSystem.services;

import team2.capSystem.model.*;

public interface LecturerService {
	
	boolean tableExist();

	void createLecturer(String username, String password, String name, String email);

	Lecturer findLecturerByEmail(String email);
	
	Lecturer getLecturer(User u);

	Lecturer findByUsername(String username);

	void deleteLecturer(Lecturer lecturer);
	void saveLecturer(Lecturer lecturer);

}