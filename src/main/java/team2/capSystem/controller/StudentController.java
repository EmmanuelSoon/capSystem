package team2.capSystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.Student;
import team2.capSystem.model.StudentCourse;
import team2.capSystem.repo.CourseDetailRepository;
import team2.capSystem.repo.StudentCourseRepository;
import team2.capSystem.repo.StudentRepository;
import team2.capSystem.services.CourseService;
import team2.capSystem.services.StudentService;

@Controller
@RequestMapping(path = "/student")
public class StudentController {
    
    @Autowired StudentRepository sRepo;
    @Autowired StudentCourseRepository scRepo;
    @Autowired CourseDetailRepository cdRepo;
    
    @Autowired 
    private StudentService studService;
    // @Autowired
    // private CourseService courseService;

    
    @GetMapping("/")
    public String showDashboard(){
        return "students/student-dashboard";
    }

    @RequestMapping(path = "/course-history")
    public String showCourseHistory(HttpSession session, Model model){
        //scRepo.getById(session.getAttribute("studentID")
        //Todo: replace hardcoded student id when session is implemented. 
        List<StudentCourse> reslt =  scRepo.findSCByStudentId(2);
        model.addAttribute("studCourse", reslt);
        return "students/student-course";

    }

    @RequestMapping(path = "/enroll")
    public String showAvailbleCourses(HttpSession session, Model model){

        List<CourseDetail> availCourse = cdRepo.findByStartDateAfter(LocalDate.now());
        List<StudentCourse> takenCourse = scRepo.findSCByStudentId(3);
        List<CourseDetail> enrollCourses = new ArrayList<CourseDetail>();
        for(StudentCourse scourse : takenCourse){
            for(CourseDetail cdCourse : availCourse){
                if (!scourse.getCourse().equals(cdCourse)){
                    enrollCourses.add(cdCourse);
                }

            }
        }
        
        model.addAttribute("enrollCourses", enrollCourses);
        return "students/student-enroll-course";

    }
    
    @GetMapping("/enrollCourse/{courseId}")
    public String enrollCourse(@PathVariable("courseId") Integer courseId) {
    	Optional<Student> stuList = sRepo.findById(3);
    	Optional<CourseDetail> cdetailList=cdRepo.findById(courseId);
    	scRepo.save(new StudentCourse(stuList.get(), cdetailList.get()));
    	return "students/student-course";
    }


}
