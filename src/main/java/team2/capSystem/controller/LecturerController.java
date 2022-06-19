package team2.capSystem.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import team2.capSystem.helper.lecturerCoursesTaught;
import team2.capSystem.helper.nominalRoll;
import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.Course;
import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.Lecturer;
import team2.capSystem.model.Student;
import team2.capSystem.model.StudentCourse;
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
		return "/lecturer/lecturer-dashboard";
	}

	@RequestMapping(value = "/course-taught")
	public String viewCoursetaught(HttpSession session, Model model, String keyword) {

		try {
			ArrayList<lecturerCoursesTaught> lectCrsTght = new ArrayList<lecturerCoursesTaught>();
			userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
			Lecturer lecturer = lecturerService.findLecturerById(user.getUserId());
			List<CourseDetail> courseDetail = lecturer.getCourses();

			for (CourseDetail crsdtl : courseDetail) {
				Course course = crsdtl.getCourse();
				lecturerCoursesTaught lectght = new lecturerCoursesTaught(crsdtl.getId(), course.getCourseId(),
						course.getName(), course.getDescription(), crsdtl.getStartDate(), crsdtl.getEndDate());

				{
					if (keyword == null || keyword == "") {
						lectCrsTght.add(lectght);
					} else if (keyword != null && keyword != "" && lectght.getCourseName().toLowerCase().contains(keyword.toLowerCase())) {
						lectCrsTght.add(lectght);
					}
				}
			}
			model.addAttribute("lecturerCourseTaught", lectCrsTght);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "/lecturer/lecturer-course-taught";
	}

	@RequestMapping(value = "/course-enrolment/{courseId}/{course_batch_id}")
	public String viewCourseEnrol(HttpSession session, Model model) {
	
		ArrayList<nominalRoll> nomRoll =new ArrayList<nominalRoll>();
		userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
		Lecturer lecturer = lecturerService.findLecturerById(user.getUserId());
		//to replace with proper filter
		CourseDetail cd = lecturer.getCourses().get(0);
				
		
		List<StudentCourse> scList = lecturerService.getSCList(cd);
		if (scList.isEmpty()) {
			model.addAttribute("nominalRoll", "NoData");
		}
		else {
			for (StudentCourse sc : scList) {
				Student student = sc.getStudent();
				nominalRoll singleRecNominalRoll = nominalRoll.builder()
						.studentName(student.getName())
						.studentEmail(student.getEmail())
						.build();
				nomRoll.add(singleRecNominalRoll);
			}
			model.addAttribute("nominalRoll",nomRoll);
		}
		
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
