package team2.capSystem.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.Admin;
import team2.capSystem.model.Lecturer;
import team2.capSystem.model.Student;
import team2.capSystem.model.User;
import team2.capSystem.services.*;

@Controller
public class LoginController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private LecturerService lecturerService;

	@RequestMapping(value = "/")
	public String standard(Model model, HttpSession session) {
		if (session.getAttribute("validated") != null || session.getAttribute("admvalidated") != null) {
			return "logout";
		}
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping("/login/authenticate")
	public String login(@ModelAttribute("user") User user, HttpSession session, @RequestParam("LoginAs") String role) {
		
		switch(role) {
		case "admin":
			Admin adm = adminService.getAdmin(user);
			if(adm != null) {
				userSessionDetails p = new userSessionDetails(adm, adm.getStaffId(), role);
				session.setAttribute("userSessionDetails", p);
				return "forward:/admin/dashboard";
			}
			break;
			
		case "lecturer":
			Lecturer lec = lecturerService.getLecturer(user);
			if(lec != null) {
				userSessionDetails p = new userSessionDetails(lec, lec.getLecturerId(), role);
				session.setAttribute("userSessionDetails", p);
				return "forward:/lecturer/dashboard";
			}
			break;
			
		case "student":
			Student stu = studentService.getStudent(user);
			if(stu != null) {
				userSessionDetails p = new userSessionDetails(stu, stu.getStudentId(), role);
				session.setAttribute("userSessionDetails", p);
				return "forward:/student/student-dashboard";
			}
			break;
			
		default: return "Login";
		}
		
		return "Login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "logout";
	}
}
