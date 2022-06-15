package team2.capSystem.services;

import java.util.ArrayList;

import javax.annotation.*;
import org.springframework.stereotype.*;

import team2.capSystem.model.Admin;
import team2.capSystem.repo.*;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Resource
	private AdminRepository adminRepository;


	@Override
	public Admin findAdminByEmail(String email) {
		return adminRepository.findAdminByEmail(email);
	}

}
