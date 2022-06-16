package team2.capSystem.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import team2.capSystem.helper.MyBag;
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
				MyBag p = new MyBag(adm);
				session.setAttribute("profile", p);
				return "index";
			}
			break;
			
		case "lecturer":
			Lecturer lec = lecturerService.getLecturer(user);
			if(lec != null) {
				MyBag p = new MyBag(lec);
				session.setAttribute("profile", p);
				return "index";
			}
			break;
			
		case "student":
			Student stu = studentService.getStudent(user);
			if(stu != null) {
				MyBag p = new MyBag(stu);
				session.setAttribute("profile", p);
				return "index";
			}
			break;
			
		default: return "Login";
		}
		
		return "Login";
		
		
//		if (LoginAs.equals("admin"))
//		{
//			System.out.println("entered if");
//			System.out.println(user);
//			
//			
//			User u = adminService.findAdminByEmail(user.getUsername());
//			
//			System.out.println("Got user");
//			
//			if((u.getEmail().equals(user.getUsername())) && (u.getPassword().equals((user.getPassword()))))
//			{
//				System.out.println("Entered log out if");
//				return "logout";
//			}
//			
//		}
//		else if (LoginAs.equals("lecturer"))
//		{
//			System.out.println("entered lecturer if");
//			System.out.println(user);
//			
//			User u = lecturerService.findLecturerByEmail(user.getUsername());
//			
//			System.out.println("Got lecturer user");
//			
//			if((u.getEmail().equals(user.getUsername())) && (u.getPassword().equals((user.getPassword()))))
//			{
//				System.out.println("Entered lecturer log out if");
//				return "logout";
//			}
//			
//		}
//		else if (LoginAs.equals("student"))
//		{
//			System.out.println("entered student if");
//			System.out.println(user);
//			
//			User u = studentService.findStudentByEmail(user.getUsername());
//			
//			System.out.println("Gotstudent user");
//			
//			if((u.getEmail().equals(user.getUsername())) && (u.getPassword().equals((user.getPassword()))))
//			{
//				System.out.println("Entered stuednt log out if");
//				return "logout";
//			}
//			
//		}

	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "logout";
	}
}
