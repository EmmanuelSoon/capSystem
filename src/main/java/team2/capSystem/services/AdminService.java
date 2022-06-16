package team2.capSystem.services;

import team2.capSystem.model.*;

public interface AdminService {
		
	void createAdmin(String username, String password, String name, String email);

	Admin findAdminByUsername(String email);
	
	Admin getAdmin(User u);

	boolean tableExist();
}

