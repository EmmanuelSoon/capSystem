package team2.capSystem.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import team2.capSystem.helper.lecturerCoursesTaught;
import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.Course;
import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.Lecturer;
import team2.capSystem.repo.LecturerRepository;
import team2.capSystem.services.LecturerService;

@Controller
@RequestMapping("/lecturer")
public class LecturerController {

	@Autowired
	LecturerRepository lRepo;

	@Autowired
	private LecturerService lecturerService;

	// @Autowired
	// private CourseService courseService;

	@RequestMapping(value = "/dashboard")
	public String displayDashboard(Model model) {
		lecturerCoursesTaught lectCrsTght = new lecturerCoursesTaught();
		model.addAttribute("lecturerCoursesTaught", lectCrsTght);
		return "/lecturer/lecturer-dashboard";
	}

	@RequestMapping(value = "/course-taught")
	public String viewCoursetaught(HttpSession session, Model model) {

		ArrayList<lecturerCoursesTaught> lectCrsTght = new ArrayList<lecturerCoursesTaught>();
		userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
		Lecturer lecturer = (Lecturer) user.getUser();
		List<CourseDetail> courseDetail = lecturer.getCourses();

		for (CourseDetail crsdtl : courseDetail) {
			Course course = crsdtl.getCourse();
			lecturerCoursesTaught lectght = new lecturerCoursesTaught(crsdtl.getId(), course.getCourseId(),
					course.getName(), course.getDescription(), crsdtl.getStartDate(), crsdtl.getEndDate());
			lectCrsTght.add(lectght);
		}
		model.addAttribute("lecturerCourseTaught", lectCrsTght);
		return "/lecturer/lecturer-course-taught";
	}

	@RequestMapping(value = "/course-enrolment/{courseId}/{course_batch_id}")
	public String viewCourseEnrol() {
		return "/lecturer/lecturer-view-enrolment";
	}

	@RequestMapping(value = "/grading")
	public String gradecourse() {
		return "/lecturer/lecturer-grade-course";
	}

	@RequestMapping(value = "/student-performance")
	public String viewStudentPerformance() {
		return "/lecturer/lecturer-course-taught";
	}

}
