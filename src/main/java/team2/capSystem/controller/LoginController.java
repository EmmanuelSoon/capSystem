package team2.capSystem.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.Lecturer;
import team2.capSystem.model.Student;
import team2.capSystem.model.User;
import team2.capSystem.services.LecturerService;
import team2.capSystem.services.StudentService;

@Controller
public class LoginController {

	//@Autowired
	//private AdminService adminService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private LecturerService lecturerService;

	@RequestMapping(value = "/")
	public String standard(Model model, HttpSession session) {
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping("/login/authenticate")
	public String login(@ModelAttribute("user") @Valid User user, BindingResult bindingresult, HttpSession session,
			@RequestParam("LoginAs") String role)  {
	
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String password = encoder.encode(user.getPassword());

		if(bindingresult.hasErrors())
		{
			return "error";
		}
		switch (role) {
		case "lecturer":
			Lecturer lec = lecturerService.findByUsername(user.getUsername());
			if (lec != null && encoder.matches(user.getPassword(), lec.getPassword()) && user.getActive()==true) {
				userSessionDetails p = new userSessionDetails(lec, lec.getLecturerId(), role);
				session.setAttribute("userSessionDetails", p);
				return "forward:/lecturer/dashboard";
			}
			
			break;

		case "student":
			Student stu = studentService.findStudentByUsername(user.getUsername());
			if (stu != null && encoder.matches(user.getPassword(), stu.getPassword()) && user.getActive()==true) {
				userSessionDetails p = new userSessionDetails(stu, stu.getStudentId(), role);
				session.setAttribute("userSessionDetails", p);
				return "forward:/student/student-dashboard";
			}
			break;

		default:			
			throw new UsernameNotFoundException("Incorrect username and password");
		}

		return "Login";
	}
	
	@RequestMapping(value = "/redirect/admin")
	public ModelAndView RedirectToAdminController() {
		String ReactAppUrl = "http://localhost:3000/admin/";
	    return new ModelAndView("redirect:" + ReactAppUrl);
	}

	@RequestMapping("/logout")
	public String logout(Model model, HttpSession session) {
		session.invalidate();
		model.addAttribute("user", new User());
		return "Login";
	}
}