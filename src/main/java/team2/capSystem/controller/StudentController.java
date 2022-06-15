package team2.capSystem.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.StudentCourse;
import team2.capSystem.repo.CourseDetailRepository;
import team2.capSystem.repo.StudentCourseRepository;
import team2.capSystem.repo.StudentRepository;

@Controller
@RequestMapping("/student")
public class StudentController {
    
    @Autowired StudentRepository sRepo;
    @Autowired StudentCourseRepository scRepo;
    @Autowired CourseDetailRepository cdRepo;
    
    
    @GetMapping("/")
    public String showDashboard(){
        return "students/student-dashboard";
    }

    @RequestMapping("/course-history")
    public String showCourseHistory(HttpSession session, Model model){
        //scRepo.getById(session.getAttribute("studentID")
        //Todo: replace hardcoded student id when session is implemented. 
        List<StudentCourse> reslt =  scRepo.findSCByStudentId(1);
        model.addAttribute("studCourse", reslt);
        return "students/student-course";

    }

    @RequestMapping("/enroll")
    public String showAvailbleCourses(HttpSession session, Model model){
        //for this implementation we are getting all available courses by date first
        //once we get the service layer up we will then compare with student_course to get 
        //courses the student hasnt' taken yet


        List<CourseDetail> availCourse = cdRepo.getCourseAvail(new Date());
        model.addAttribute("availCourses", availCourse);
        return "students/student-enroll-course";

    }


}
