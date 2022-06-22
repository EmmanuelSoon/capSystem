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
    private LecturerService lectService;

    @Autowired
    private CourseService courseService;

    @RequestMapping("/student-dashboard")
    public String showDashboard(HttpSession session){
        if (!checkUser(session)){
            return "forward:/logout";
        }
        return "students/student-dashboard";
    }

    @RequestMapping(path = "/course-history/")
    public String showCourseHistory(HttpSession session, Model model, @ModelAttribute("courseDetailSearchQuery") courseDetailSearchQuery search){
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
        if (!checkUser(session)){
            return "forward:/logout";
        }
        userSessionDetails usd = getUsd(session);
        List<CourseDetail> enrollCourses = studService.getStudentAvailCourses(usd, search);
        model.addAttribute("enrollCourses", enrollCourses);

        return "students/student-enroll-course";

    }
    
    @RequestMapping("/enrollCourse/" )
    public String enrollCourse(@RequestParam("cdId") int id, HttpSession session, RedirectAttributes redirAttrs) {
        if (!checkUser(session)){
            return "forward:/logout";
        }
        userSessionDetails usd = getUsd(session);
        try {
            studService.studentEnrollCourse(usd,id);
        } catch (ClassFullException e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/student/enrollCourse/";
        } 
        catch(ClassStartedException e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/student/enrollCourse/";
        }
    	redirAttrs.addFlashAttribute("Success", "Everything went just fine.");
        return "redirect:/student/course-history/";
    }

    @RequestMapping("/unenrollCourse/" )
    public String unenrollCourse(@RequestParam("cdId") int id, Model model, HttpSession session) {
        if (!checkUser(session)){
            return "forward:/logout";
        }
        userSessionDetails usd = getUsd(session);
        try {
            studService.studentUnenrollCourse(id, usd);
            return "redirect:/student/course-history/";
        }
        catch (AfterTwoWeekUnenrollmentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/student/handleExceptionPage";
        }
        catch (CourseEndedException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/student/handleExceptionPage";
        }
        catch (GpaExistException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/student/handleExceptionPage";
        }
    }

    @RequestMapping("/profile")
    public String displayStudentProfile(Model model, HttpSession session){
        if (!checkUser(session)){
            return "forward:/logout";
        }
        userSessionDetails usd = getUsd(session);
        Student student = studService.getStudentProfile(usd);
        model.addAttribute("student", student);
        return "students/student-profile";
    }

    @RequestMapping("/editProfile")
    public String editStudentProfile(Model model, HttpSession session){
        if (!checkUser(session)){
            return "forward:/logout";
        }
        userSessionDetails usd = getUsd(session);
        Student student = studService.getStudentProfile(usd);
        model.addAttribute("student", student);
        return "students/student-updateProfile";
    }

    @RequestMapping("/updatedProfile")
    public String updatedStudentProfile(HttpSession session, @ModelAttribute("student") @Valid Student student, BindingResult bindingresult){
        if (!checkUser(session)){
            return "forward:/logout";
        }
        
        if(bindingresult.hasErrors()) {
            return "students/student-updateProfile";
        }
        studService.saveStudent(student);
        return "students/student-profile";


    }
    

    @RequestMapping(value="change-password")
    public String ChangePassword(HttpSession session, @ModelAttribute("userChangePassword") @Valid userChangePassword userPass, BindingResult bindingresult,Model model){
        if (bindingresult.hasErrors()){
            return "students/password-change";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userSessionDetails usd = getUsd(session);
        if(encoder.matches(userPass.getOldPassword(), usd.getUser().getPassword())){
            
            Student student = studService.setStudentPassword(usd.getUserId(), userPass);
 
            userSessionDetails p = new userSessionDetails(student, student.getStudentId(), "student");
            session.setAttribute("userSessionDetails", p);

            return "forward:/student/profile";
        
        }
        else {
        	model.addAttribute("message", "Incorrect Password!");
            return "students/password-change";
        }
    	
        
    }

    @RequestMapping("/exception")
    public String exceptionPage(Model model){
        model.addAttribute("error", "testing the error page");
        return "students/handleExceptionPage";
    }
        
    //helper functions
    
    private boolean checkUser(HttpSession session){
        userSessionDetails usd = getUsd(session);
        if (usd != null && usd.getUserRole().equals("student")){
            return true;
        } 
        return false;
    }

    private userSessionDetails getUsd(HttpSession session){
        return (userSessionDetails)session.getAttribute("userSessionDetails");
    }


}
