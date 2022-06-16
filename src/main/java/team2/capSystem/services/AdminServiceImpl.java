package team2.capSystem.services;


import javax.annotation.*;
import org.springframework.stereotype.*;

import team2.capSystem.model.Admin;
import team2.capSystem.model.User;
import team2.capSystem.repo.*;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Resource
	private AdminRepository adminRepository;

	public boolean tableExist(){
		return adminRepository.existsBy();
	}

	public void createAdmin(String username, String password, String name, String email){
		adminRepository.save(new Admin(username, password, name, email));
	};


	@Override
	public Admin findAdminByUsername(String username) {
		return adminRepository.findAdminByUsername(username);
	}


	@Override
	public Admin getAdmin(User u) {
		return adminRepository.findAdminByUsernameAndPassword(u.getUsername(), u.getPassword());
	}

}
