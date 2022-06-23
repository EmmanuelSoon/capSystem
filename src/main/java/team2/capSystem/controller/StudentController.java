package team2.capSystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import team2.capSystem.exceptions.*;
import team2.capSystem.exceptions.RequestException;
import team2.capSystem.helper.courseDetailSearchQuery;
import team2.capSystem.helper.userChangePassword;
import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.Lecturer;
import team2.capSystem.model.Student;
import team2.capSystem.model.StudentCourse;
import team2.capSystem.repo.CourseDetailRepository;
import team2.capSystem.repo.StudentCourseRepository;
import team2.capSystem.repo.StudentRepository;
import team2.capSystem.services.CourseService;
import team2.capSystem.services.LecturerService;
import team2.capSystem.services.StudentService;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(path = "/student")
public class StudentController {
    
    @Autowired 
    private StudentService studService;

    @Autowired
    private CourseService courseService;

    @RequestMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model){
        String rtn = checkSession(session);
        if (rtn != ""){
            return rtn;
        }
        userSessionDetails usd = getUsd(session);
        List<StudentCourse> current = studService.findStudentCoursesOngoing(usd.getUserId(), "");
        List<StudentCourse> hist = studService.findStudentCoursesFinish(usd.getUserId(), "");
        model.addAttribute("currentCount", current.size());
        model.addAttribute("finishedCount", hist.size());
        return "students/student-dashboard";
    }

    @RequestMapping(path = "/course-history/")
    public String showCourseHistory(HttpSession session, Model model, @ModelAttribute("courseDetailSearchQuery") courseDetailSearchQuery search){
        String rtn = checkSession(session);
        if (rtn != ""){
            return rtn;
        }
        userSessionDetails usd = getUsd(session);
        List<StudentCourse> current = studService.findStudentCoursesOngoing(usd.getUserId(), search.getKeyword());
        List<StudentCourse> hist = studService.findStudentCoursesFinish(usd.getUserId(), search.getKeyword());
        String getAverageGPA= String.format("%.2f", studService.getAverageGPA(usd.getUserId()));
        Student student =studService.findStudentById(usd.getUserId());

        model.addAttribute("currStudent", student);
        model.addAttribute("studCourse", current);
        model.addAttribute("studHist", hist);
        model.addAttribute("avgGPA", getAverageGPA);
        return "students/student-course";

    }

    @RequestMapping(path = "/view-classlist/{id}")
    public String showClassList(@PathVariable("id") Integer id, HttpSession session, Model model){
        String rtn = checkSession(session);
        if (rtn != ""){
            return rtn;
        }
        List<StudentCourse> classList = studService.getClassList(id);
        List<Lecturer> lectList = studService.getLecturerList(id);
        CourseDetail cd = courseService.findCourseDetailById(id);

        model.addAttribute("courseDetails", cd);
        model.addAttribute("classlist", classList);
        model.addAttribute("lecturerlist", lectList);
        
        return "students/student-course-details";
    }



    @RequestMapping(path = "/enroll*")
    public String showAvailbleCourses(HttpSession session, Model model, @ModelAttribute("courseDetailSearchQuery") courseDetailSearchQuery search){
        String rtn = checkSession(session);
        if (rtn != ""){
            return rtn;
        }
        userSessionDetails usd = getUsd(session);
        List<CourseDetail> enrollCourses = studService.getStudentAvailCourses(usd, search);
        model.addAttribute("enrollCourses", enrollCourses);

        return "students/student-enroll-course";

    }
    
    @RequestMapping("/enrollCourse/" )
    public String enrollCourse(@RequestParam("cdId") int id, HttpSession session, RedirectAttributes redirAttrs) {
        String rtn = checkSession(session);
        if (rtn != ""){
            return rtn;
        }
        userSessionDetails usd = getUsd(session);
        try {
            studService.studentEnrollCourse(usd,id);
            redirAttrs.addFlashAttribute("success", "Course Enrollment successful!");
            return "redirect:/student/course-history/";
        } catch (ClassFullException e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());
        } 
        catch(ClassStartedException e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/student/enroll/";
    }

    @RequestMapping("/unenrollCourse/" )
    public String unenrollCourse(@RequestParam("cdId") int id, HttpSession session, RedirectAttributes redirAttrs) {
        String rtn = checkSession(session);
        if (rtn != ""){
            return rtn;
        }
        userSessionDetails usd = getUsd(session);
        try {
            studService.studentUnenrollCourse(id, usd);
            redirAttrs.addFlashAttribute("success", "Unenrolled from course successfully");
        }
        catch (AfterTwoWeekUnenrollmentException e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());
        }
        catch (CourseEndedException e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());
 
        }
        catch (GpaExistException e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());

        }
        return "redirect:/student/course-history/";
    }

    @RequestMapping("/profile")
    public String displayStudentProfile(Model model, HttpSession session){
        String rtn = checkSession(session);
        if (rtn != ""){
            return rtn;
        }
        userSessionDetails usd = getUsd(session);
        Student student = studService.getStudentProfile(usd);
        model.addAttribute("student", student);
        return "students/student-profile";
    }

    @RequestMapping("/editProfile")
    public String editStudentProfile(Model model, HttpSession session){
        String rtn = checkSession(session);
        if (rtn != ""){
            return rtn;
        }
        userSessionDetails usd = getUsd(session);
        Student student = studService.getStudentProfile(usd);
        model.addAttribute("student", student);
        return "students/student-updateProfile";
    }

    @RequestMapping("/updatedProfile")
    public String updatedStudentProfile(HttpSession session, @ModelAttribute("student") @Valid Student student, BindingResult bindingresult, RedirectAttributes redirAttr,Model model){
        String rtn = checkSession(session);
        if (rtn != ""){
            return rtn;
        }
        if(student.getName()==null || student.getName()=="") {
        	if(student.getEmail()=="" || student.getEmail()=="") {
        		model.addAttribute("nameErrorMessage", "Name cannot be empty");
                model.addAttribute("emailErrorMessage", "Email cannot be empty");
                return "students/student-updateProfile";
            }
            model.addAttribute("nameErrorMessage", "Name cannot be empty");
            return "students/student-updateProfile";
        }
        if(student.getEmail()=="" || student.getEmail()=="") {
        	if(student.getName()==null || student.getName()=="") {
	            model.addAttribute("emailErrorMessage", "Email cannot be empty");
	            model.addAttribute("nameErrorMessage", "Name cannot be empty");
            return "students/student-updateProfile";
        	}
        	model.addAttribute("emailErrorMessage", "Email cannot be empty");
        	 return "students/student-updateProfile";
        }
        if(bindingresult.hasErrors()) {
            return "students/student-updateProfile";
        }
        studService.saveStudent(student);
        redirAttr.addFlashAttribute("success","Changes saved successfully!");
        return "redirect:/student/profile";
    }
    
    @GetMapping(value="change-password")
    public String ChangePassword(HttpSession session,Model model){
        userChangePassword ucp=new userChangePassword();
        model.addAttribute("userChangePassword", ucp);
        return "students/password-change";	
    }

    @RequestMapping(value="update-password")
    public String ChangePassword(HttpSession session, @ModelAttribute("userChangePassword") @Valid userChangePassword userPass, BindingResult bindingresult,Model model, RedirectAttributes redirAttr){
        if (bindingresult.hasErrors()){
            return "students/password-change";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userSessionDetails usd = getUsd(session);
        if(encoder.matches(userPass.getOldPassword(), usd.getUser().getPassword())){
            
            Student student = studService.setStudentPassword(usd.getUserId(), userPass);
 
            userSessionDetails p = new userSessionDetails(student, student.getStudentId(), "student");
            session.setAttribute("userSessionDetails", p);

            redirAttr.addFlashAttribute("success","Password saved successfully!");
            return "redirect:/student/profile";
        
        }
        else {
        	model.addAttribute("message", "Incorrect Password!");
            return "students/password-change";
        }
    	
        
    }
        
    //helper functions
    
    private userSessionDetails getUsd(HttpSession session){
        return (userSessionDetails)session.getAttribute("userSessionDetails");
    }

    private String checkSession(HttpSession session){
        userSessionDetails usd = (userSessionDetails)session.getAttribute("userSessionDetails");
        if(!usd.getUserRole().equals("student")){
            return "redirect:/" +  usd.getUserRole() + "/dashboard";
        }
        return "";
    }
}
