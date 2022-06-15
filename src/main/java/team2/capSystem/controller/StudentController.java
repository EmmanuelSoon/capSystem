package team2.capSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team2.capSystem.repo.StudentRepository;

@Controller
@RequestMapping("/student")
public class StudentController {
    
    @Autowired StudentRepository sRepo;
    
    @GetMapping("/")
    public String showDashboard(){
        return "student-dashboard";
    }

    @RequestMapping("/course-history")
    public String showCourseHistory(){
        return "student-course";
    }

    @RequestMapping("/enroll")
    public String showAvailbleCourses(){
        return "student-enroll-course";
    }



}
