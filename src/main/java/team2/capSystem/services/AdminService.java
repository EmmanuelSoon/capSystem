package team2.capSystem.services;

import team2.capSystem.model.*;

public interface AdminService {
		
	Admin findAdminByUsername(String email);
	
	Admin getAdmin(User u);

}
