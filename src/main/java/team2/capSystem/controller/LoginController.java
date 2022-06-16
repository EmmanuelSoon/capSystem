package team2.capSystem.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import team2.capSystem.model.*;
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
	public String login(@ModelAttribute("user") User user, HttpSession session,
			@RequestParam(name = "LoginAs") String LoginAs) {
		System.out.println(LoginAs);

		switch (LoginAs) {
		case "admin":
			return redirectToAdminDashBoard(user, session);
		case "lecturer":
			return redirectToLecturerDashBoard(user, session);
		case "student":
			return redirectToStudentDashBoard(user, session);
		default:
			return "login";
		}
	}

	private String redirectToAdminDashBoard(User user, HttpSession session) {

		Admin admin = adminService.findAdminByUsername(user.getUsername());

		if (admin != null) {
			if (admin.getUsername().equals(user.getUsername()) && admin.getPassword().equals(user.getPassword())) {
				session.setAttribute("userRole", "admin");
				session.setAttribute("userID", admin.getStaffId());
				return "AdminDashboard";
			}
		}
		return "login";

	}

	private String redirectToLecturerDashBoard(User user, HttpSession session) {

		Lecturer lecturer = lecturerService.findLecturerByUsername(user.getUsername());

		if (lecturer != null) {
			if (lecturer.getUsername().equals(user.getUsername()) && lecturer.getPassword().equals(user.getPassword())) {
				session.setAttribute("userRole", "admin");
				session.setAttribute("userID", lecturer.getLecturerId());
				return "LecturerDashboard";
			}
		}
		return "login";

	}

	private String redirectToStudentDashBoard(User user, HttpSession session) {

		Student student = studentService.findStudentByUsername(user.getUsername());

		if (student != null) {
			if (student.getUsername().equals(user.getUsername()) && student.getPassword().equals(user.getPassword())) {
				session.setAttribute("userRole", "admin");
				session.setAttribute("userID", student.getStudentId());
				return "StudentDashboard";
			}
		}
		return "login";

	}
}
