package team2.capSystem.services;


import javax.annotation.*;
import org.springframework.stereotype.*;

import team2.capSystem.model.Admin;
import team2.capSystem.repo.*;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Resource
	private AdminRepository adminRepository;


	@Override
	public Admin findAdminByUsername(String username) {
		return adminRepository.findAdminByUsername(username);
	}

}
