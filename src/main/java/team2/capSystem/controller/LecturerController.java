package team2.capSystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team2.capSystem.helper.lecturerCourseStudentSearch;
import team2.capSystem.helper.lecturerCoursesTaught;
import team2.capSystem.helper.nominalRoll;
import team2.capSystem.helper.studentTranscript;
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

	@RequestMapping(value = "/dashboard")
	public String displayDashboard(Model model, HttpSession session) {
		try {
			if (!isUserLecturer(session))
				return "forward:/logout";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "/lecturer/lecturer-dashboard";
	}

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
	public String updatedStudentProfile(@ModelAttribute("lecturer") @Valid Lecturer lecturer,
			BindingResult bindingresult) {
		try {
			if (bindingresult.hasErrors()) {
				return "students/lecturer-updateProfile";
			}
			lecturerService.saveLecturer(lecturer);
			return "students/lecturer-profile";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "lecturer/lecturer-profile";

	}

	@RequestMapping(value = "/course-taught")
	public String viewCoursetaught(HttpSession session, Model model,
			@ModelAttribute("lecturerCourseStudentSearch") lecturerCourseStudentSearch keyword) {

		try {
			if (!isUserLecturer(session))
				return "forward:/logout";

			ArrayList<lecturerCoursesTaught> lectCrsTght = new ArrayList<lecturerCoursesTaught>();
			userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
			Lecturer lecturer = lecturerService.findLecturerById(user.getUserId());
			List<CourseDetail> courseDetail = lecturer.getCourses().stream().distinct().collect(Collectors.toList());

			for (CourseDetail crsdtl : courseDetail) {
				Course course = crsdtl.getCourse();
				lecturerCoursesTaught lectght = lecturerCoursesTaught.builder().courseBatchId(crsdtl.getId())
						.courseId(course.getCourseId()).courseName(course.getName())
						.courseDescription(course.getDescription()).startDate(crsdtl.getStartDate())
						.endDate(crsdtl.getEndDate()).build();
				{
					if (keyword.keywordNullOrEmpty()) {
						lectCrsTght.add(lectght);
					} else if (!keyword.keywordNullOrEmpty()
							&& lectght.getCourseName().toLowerCase().contains(keyword.getKeyword().toLowerCase())) {
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
	public String viewCourseEnrol(HttpSession session, Model model, @PathVariable int course_batch_id,
			@PathVariable int courseId,
			@ModelAttribute("lecturerCourseStudentSearch") lecturerCourseStudentSearch keyword) {

		try {
			if (!isUserLecturer(session))
				return "forward:/logout";

			ArrayList<nominalRoll> nomRoll = new ArrayList<nominalRoll>();
			userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
			Lecturer lecturer = lecturerService.findLecturerById(user.getUserId());
			CourseDetail cd = lecturer.getCourses().stream().filter(x -> x.getId() == course_batch_id).findFirst()
					.get();

			List<StudentCourse> scList = lecturerService.getSCList(cd);
			if (scList.isEmpty()) {
				model.addAttribute("nominalRoll", "NoData");
			} else {
				for (StudentCourse sc : scList) {
					Student student = sc.getStudent();
					nominalRoll singleRecNominalRoll = nominalRoll.builder().courseBatchId(sc.getCourse().getId())
							.studentId(student.getStudentId()).studentName(student.getName())
							.studentEmail(student.getEmail()).build();
					{
						if (keyword.keywordNullOrEmpty()) {
							nomRoll.add(singleRecNominalRoll);
						} else if (!keyword.keywordNullOrEmpty() && (singleRecNominalRoll.getStudentName().toLowerCase()
								.contains(keyword.getKeyword().toLowerCase()))) {
							nomRoll.add(singleRecNominalRoll);
						}
					}
				}
				model.addAttribute("nominalRoll", nomRoll);
				model.addAttribute("courseId", courseId);
				model.addAttribute("course_batch_id", course_batch_id);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "/lecturer/lecturer-view-enrolment";
	}

	@RequestMapping(value = "/student-performance/grading/{courseBatchId}/{student_id}")
	public String gradeCourse(HttpSession session, Model model, @PathVariable int courseBatchId,
			@PathVariable int student_id) {

		try {
			if (!isUserLecturer(session))
				return "forward:/logout";

			List<StudentCourse> scList = lecturerService.getCourseListTakenByStudent(student_id);
			StudentCourse sc = scList.stream().filter(x -> x.getCourse().getId() == courseBatchId).findFirst().get();
			model.addAttribute("gradeStudent", sc);
			model.addAttribute("courseBatchId", courseBatchId);
			model.addAttribute("student_id", student_id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "/lecturer/lecturer-grade-course";
	}

	@RequestMapping(value = "/student-performance/updateGrading/{courseBatchId}/{student_id}")
	public String updateStudentCourseGPA(HttpSession session, Model model, @PathVariable int courseBatchId,
			@PathVariable int student_id, @RequestParam("gpa") double gpa) {

		try {
			if (!isUserLecturer(session))
				return "forward:/logout";

			studentService.updateStudentCourseGPA(courseBatchId, student_id, gpa);
			List<StudentCourse> scList = lecturerService.getCourseListTakenByStudent(student_id);
			StudentCourse sc = scList.stream().filter(x -> x.getCourse().getId() == courseBatchId).findFirst().get();
			model.addAttribute("gradeStudent", sc);
			model.addAttribute("courseBatchId", courseBatchId);
			model.addAttribute("student_id", student_id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "/lecturer/lecturer-grade-course";
	}

	@RequestMapping(value = "/student-performance/{student_id}")
	public String viewStudentPerformance(Model model, @PathVariable int student_id, HttpSession session) {

		try {
			if (!isUserLecturer(session))
				return "forward:/logout";

			ArrayList<studentTranscript> studTS = new ArrayList<studentTranscript>();
			List<StudentCourse> scList = lecturerService.getCourseListTakenByStudent(student_id);

			if (scList.isEmpty()) {
				model.addAttribute("studentTranscript", "NoData");
			} else {
				for (StudentCourse sc : scList) {
					studentTranscript singleModRec = studentTranscript.builder().courseBatchId(sc.getId())
							.courseName(sc.getCourse().getCourse().getName())
							.dateOfCompletion(sc.getCourse().getEndDate()).gpa(sc.getGpa()).build();
					studTS.add(singleModRec);
				}
				model.addAttribute("studentTranscript", studTS);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "/lecturer/lecturer-view-performance";
	}

	private Boolean isUserLecturer(HttpSession session) {
		userSessionDetails user = (userSessionDetails) session.getAttribute("userSessionDetails");
		if (user != null && user.getUserRole().equals("lecturer")) {
			return true;
		}

		return false;
	}
}
