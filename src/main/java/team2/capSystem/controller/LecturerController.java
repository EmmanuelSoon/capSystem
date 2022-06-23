package team2.capSystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import team2.capSystem.helper.lecturerCourseStudentSearch;
import team2.capSystem.helper.lecturerCoursesHelper;
import team2.capSystem.helper.lecturerStudentGrading;
import team2.capSystem.helper.nominalRoll;
import team2.capSystem.helper.studentTranscript;
import team2.capSystem.helper.userChangePassword;
import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.Course;
import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.Lecturer;
import team2.capSystem.model.Student;
import team2.capSystem.model.StudentCourse;
import team2.capSystem.repo.LecturerRepository;
import team2.capSystem.services.LecturerService;
import team2.capSystem.services.StudentService;

@Controller
@RequestMapping("/lecturer")
public class LecturerController {

	@Autowired
	LecturerRepository lRepo;

	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private StudentService studentService;

	@RequestMapping("/profile")
	public String displayLecturerProfile(Model model, HttpSession session) {
		userSessionDetails usd = (userSessionDetails) session.getAttribute("userSessionDetails");
		Lecturer lecturer = lecturerService.getLecturerProfile(usd);
		model.addAttribute("lecturer", lecturer);
		return "lecturer/lecturer-profile";
	}

	@RequestMapping("/editProfile")
	public String editLecturerProfile(Model model, HttpSession session) {
		System.out.print("testing");
		userSessionDetails usd = (userSessionDetails) session.getAttribute("userSessionDetails");
		Lecturer lecturer = lecturerService.getLecturerProfile(usd);
		model.addAttribute("lecturer", lecturer);
		return "lecturer/lecturer-updateProfile";
	}

	@RequestMapping("/updatedProfile")
	public String updatedLecturerProfile(@ModelAttribute("lecturer") @Valid Lecturer lecturer,
			BindingResult bindingresult, HttpSession session) {
		try {
			if(!isUserLecturer(session))
				return "error";
			if (bindingresult.hasErrors()) {
				return "lecturer/lecturer-updateProfile";
			}
			lecturerService.saveLecturer(lecturer);
			return "lecturer/lecturer-profile";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "lecturer/lecturer-profile";

	}

	@RequestMapping(value = "/dashboard")
	public String displayDashboard(HttpSession session, Model model) {
		try {
			if (!isUserLecturer(session))
				return "error";
			userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
			Lecturer lecturer = lecturerService.findLecturerById(user.getUserId());
			List<CourseDetail> courseDetailUpcoming = lecturerService.getUpcomingCoursesByLecturer(lecturer);
			List<CourseDetail> courseDetailOnging = lecturerService.getOnGoingCourseByLecturer(lecturer);

			model.addAttribute("Upcoming", courseDetailUpcoming.size());
			model.addAttribute("Ongoing", courseDetailOnging.size());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "/lecturer/lecturer-dashboard";
	}

	@RequestMapping(value = "/course-upcoming")
	public String viewCourseUpcoming(Model model, HttpSession session,
			@ModelAttribute("lecturerCourseStudentSearch") lecturerCourseStudentSearch keyword) {
		try {
			if (!isUserLecturer(session))
				return "error";
			ArrayList<lecturerCoursesHelper> lectCrsUpcoming = new ArrayList<lecturerCoursesHelper>();
			userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
			Lecturer lecturer = lecturerService.findLecturerById(user.getUserId());
			List<CourseDetail> courseDetail = lecturerService.getUpcomingCoursesByLecturer(lecturer);
			for (CourseDetail crsdtl : courseDetail) {
				Course course = crsdtl.getCourse();
				lecturerCoursesHelper lectCrs = lecturerService.createLecturerCoursesHelper(crsdtl, course);
				{
					if (keyword.keywordNullOrEmpty()) {
						lectCrsUpcoming.add(lectCrs);
					} else if (!keyword.keywordNullOrEmpty()
							&& lectCrs.getCourseName().toLowerCase().contains(keyword.getKeyword().toLowerCase())) {
						lectCrsUpcoming.add(lectCrs);
					}
				}
			}
			model.addAttribute("lecturerCourse", lectCrsUpcoming);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "/lecturer/lecturer-course-upcoming";
	}

	@RequestMapping(value = "/course-ongoing")
	public String viewCourseOngoing(HttpSession session, Model model,
			@ModelAttribute("lecturerCourseStudentSearch") lecturerCourseStudentSearch keyword) {

		try {
			if (!isUserLecturer(session))
				return "error";

			ArrayList<lecturerCoursesHelper> lectCrsOngoing = new ArrayList<lecturerCoursesHelper>();
			userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
			Lecturer lecturer = lecturerService.findLecturerById(user.getUserId());
			List<CourseDetail> courseDetail = lecturerService.getOnGoingCourseByLecturer(lecturer);

			for (CourseDetail crsdtl : courseDetail) {
				Course course = crsdtl.getCourse();
				lecturerCoursesHelper lectOn = lecturerService.createLecturerCoursesHelper(crsdtl, course);
				{
					if (keyword.keywordNullOrEmpty()) {
						lectCrsOngoing.add(lectOn);
					} else if (!keyword.keywordNullOrEmpty()
							&& lectOn.getCourseName().toLowerCase().contains(keyword.getKeyword().toLowerCase())) {
						lectCrsOngoing.add(lectOn);
					}

				}
			}
			model.addAttribute("lecturerCourse", lectCrsOngoing);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "/lecturer/lecturer-course-ongoing";
	}

	@RequestMapping(value = "/course-taught")
	public String viewCourseTaught(HttpSession session, Model model,
			@ModelAttribute("lecturerCourseStudentSearch") lecturerCourseStudentSearch keyword) {

		try {
			if (!isUserLecturer(session))
				return "error";

			ArrayList<lecturerCoursesHelper> lectCrsTght = new ArrayList<lecturerCoursesHelper>();
			userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
			Lecturer lecturer = lecturerService.findLecturerById(user.getUserId());
			List<CourseDetail> courseDetail = lecturerService.getCompletedCoursesByLecturer(lecturer);

			for (CourseDetail crsdtl : courseDetail) {
				Course course = crsdtl.getCourse();
				lecturerCoursesHelper lecTght = lecturerService.createLecturerCoursesHelper(crsdtl, course);
				{
					if (keyword.keywordNullOrEmpty()) {
						lectCrsTght.add(lecTght);
					} else if (!keyword.keywordNullOrEmpty()
							&& lecTght.getCourseName().toLowerCase().contains(keyword.getKeyword().toLowerCase())) {
						lectCrsTght.add(lecTght);
					}
				}
			}
			model.addAttribute("lecturerCourse", lectCrsTght);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "/lecturer/lecturer-course-taught";
	}

	@RequestMapping(value = "/class-list/{courseId}/{course_batch_id}")
	public String viewCourseEnrol(HttpSession session, Model model, @PathVariable int course_batch_id,
			@PathVariable int courseId,
			@ModelAttribute("lecturerCourseStudentSearch") lecturerCourseStudentSearch keyword) {

		try {
			if (!isUserLecturer(session))
				return "error";

			ArrayList<nominalRoll> nomRoll = new ArrayList<nominalRoll>();
			userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
			Lecturer lecturer = lecturerService.findLecturerById(user.getUserId());
			CourseDetail cd = lecturerService.getCourseDetailByBatchIdByLecturer(course_batch_id, lecturer);

			List<StudentCourse> scList = lecturerService.getSCList(cd);

			if (scList.isEmpty()) {
				model.addAttribute("nominalRoll", "NoData");
			} else {
				for (StudentCourse sc : scList) {
					Student student = sc.getStudent();
					nominalRoll singleRecNominalRoll = lecturerService.createNominalRoll(student, sc);
					{
						if (keyword.keywordNullOrEmpty()) {
							nomRoll.add(singleRecNominalRoll);
						} else if (!keyword.keywordNullOrEmpty() && (singleRecNominalRoll.getStudentName().toLowerCase()
								.contains(keyword.getKeyword().toLowerCase()))) {
							nomRoll.add(singleRecNominalRoll);
						}
					}
				}

				StudentCourse sc = lecturerService.getSCByBatchId(course_batch_id, scList);

				if (sc.getCourse().getEndDate().isBefore(LocalDate.now()))
					model.addAttribute("courseOver", "courseOver");
				if (sc.getCourse().getStartDate().isAfter(LocalDate.now()))
					model.addAttribute("courseOver", "courseNew");
				model.addAttribute("studCount", nomRoll.size());
				model.addAttribute("nominalRoll", nomRoll);
				model.addAttribute("courseId", courseId);
				model.addAttribute("courseName", cd.getCourse().getName());
				model.addAttribute("course_batch_id", course_batch_id);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "/lecturer/lecturer-view-nominalroll";
	}

	@RequestMapping(value = "/student-performance/grading/{courseId}/{courseBatchId}/{student_id}")
	public String gradeCourse(HttpSession session, Model model, @PathVariable int courseBatchId,
			@PathVariable int student_id, @PathVariable int courseId) {

		try {
			if (!isUserLecturer(session))
				return "error";

			List<StudentCourse> scList = lecturerService.getCourseListTakenByStudent(student_id);
			StudentCourse sc = lecturerService.getSCByBatchId(courseBatchId, scList);
			lecturerStudentGrading lectStudentGrading = new lecturerStudentGrading(sc.getName(), sc.getCourse().getId(),
					sc.getCourse().getCourse().getName());
			model.addAttribute("gradeStudent", sc);
			model.addAttribute("gpa", sc.getGpa());
			model.addAttribute("courseBatchId", courseBatchId);
			model.addAttribute("student_id", student_id);
			model.addAttribute("courseId", courseId);
			model.addAttribute("lecturerStudentGrading", lectStudentGrading);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "/lecturer/lecturer-grade-course";
	}

	@RequestMapping(value = "/student-performance/updateGrading/{courseId}/{courseBatchId}/{student_id}")
	public String updateStudentCourseGPA(HttpSession session, Model model, @PathVariable int courseId,
			@PathVariable int courseBatchId, @PathVariable int student_id, @RequestParam("gpa") double gpa,
			RedirectAttributes redirattr) {

		try {
			if (!isUserLecturer(session))
				return "error";

			studentService.updateStudentCourseGPA(courseBatchId, student_id, gpa);
			List<StudentCourse> scList = lecturerService.getCourseListTakenByStudent(student_id);
			StudentCourse sc = lecturerService.getSCByBatchId(courseBatchId, scList);
			lecturerStudentGrading lectStudentGrading = new lecturerStudentGrading(sc.getName(), sc.getCourse().getId(),
					sc.getCourse().getCourse().getName());
			model.addAttribute("gradeStudent", sc);
			model.addAttribute("gpa", sc.getGpa());
			model.addAttribute("courseBatchId", courseBatchId);
			model.addAttribute("student_id", student_id);
			model.addAttribute("courseId", courseId);
			model.addAttribute("lecturerStudentGrading", lectStudentGrading);
			redirattr.addFlashAttribute("success", "GPA successfully updated!");
		} catch (Exception e) {
			redirattr.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/lecturer/class-list/" + courseId + "/" + courseBatchId;
	}

	@RequestMapping(value = "/student-performance/{student_id}")
	public String viewStudentPerformance(Model model, @PathVariable int student_id, HttpSession session) {

		try {
			if (!isUserLecturer(session))
				return "error";

			ArrayList<studentTranscript> studTS = new ArrayList<studentTranscript>();
			List<StudentCourse> scList = lecturerService.getCourseListTakenByStudent(student_id);
			String getAverageGPA = String.format("%.2f", studentService.getAverageGPA(student_id));
			String studentName = studentService.getStudent(student_id).getName();

			if (scList.isEmpty()) {
				model.addAttribute("studentTranscript", "NoData");
			} else {
				for (StudentCourse sc : scList) {
					studentTranscript singleModRec = lecturerService.createStudentTransciptRec(sc);
					studTS.add(singleModRec);
				}
				model.addAttribute("studentId", student_id);
				model.addAttribute("studentName", studentName);
				model.addAttribute("avgGPA", getAverageGPA);
				model.addAttribute("studentTranscript", studTS);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "/lecturer/lecturer-view-performance";
	}

	@RequestMapping(value = "change-password")
	public String ChangePassword(HttpSession session,
			@ModelAttribute("userChangePassword") @Valid userChangePassword userPass, BindingResult bindingresult,
			Model model) {

		try {
			if (!isUserLecturer(session))
				return "error";

			if (bindingresult.hasErrors()) {
				// model.addAttribute("testing", "this is testing");
				return "lecturer/lecturer-password-change";
			}

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userSessionDetails usd = (userSessionDetails) session.getAttribute("userSessionDetails");
			if (encoder.matches(userPass.getOldPassword(), usd.getUser().getPassword())) {
				// try
				Lecturer lecturer = lecturerService.setLecturerPassword(usd.getUserId(), userPass);

				userSessionDetails p = new userSessionDetails(lecturer, lecturer.getLecturerId(), "lecturer");
				session.setAttribute("userSessionDetails", p);
				// catch
			} else {
				model.addAttribute("message", "Incorrect Password!");
				return "lecturer/lecturer-password-change";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "forward:/lecturer/profile";
	}

	private Boolean isUserLecturer(HttpSession session) {
		userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
		if (user != null && user.getUserRole().equals("lecturer")) {
			return true;
		}

		return false;
	}
}
