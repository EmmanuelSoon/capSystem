package team2.capSystem.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import net.minidev.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import team2.capSystem.model.*;
import team2.capSystem.services.*;


@RestController
@RequestMapping(path = "/admin", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private CourseService courseService;
    
    @Autowired
    private StudentService studentService;



    /*----------------/

    Get = Read
    Post = Create
    Put = Update/replace
    delete = delete

    /---------------*/

	// @GetMapping
    // public String displayDashboard() {
    //     return "/admin/adminDashboard";
    // }

    /*-----------------------------------READ FUNCTIONS--------------------------------------*/



    @GetMapping("/student")
    public List<Student> getStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping(value = "/student/{id}")
    public Student getStudent(@PathVariable int id){
        return studentService.findStudentById(id);
    }

    @GetMapping("/lecturer")
    public List<Lecturer> getLecturers(){
        return lecturerService.getAllLecturers();
    }

    @GetMapping(value = "/lecturer/{id}")
    public Lecturer getLecturer(@PathVariable int id){
        return lecturerService.findLecturerById(id);
    }

    @GetMapping(value = "/student/course/{id}")
    public List<StudentCourseJson> getCoursesForStudent(@PathVariable int id){
        List<StudentCourse> scList = studentService.findCoursesByStudentId(id);
        return studentService.convertSCToJson(scList);
    }
    @GetMapping(value = "/course/{id}")
    public Course getCourse(@PathVariable int id){
        return courseService.findCourseById(id);
    }

    @GetMapping(value = "/lecturer/course/{id}")
    public List<CourseDetail> getCoursesForLecturer(@PathVariable int id){
        List<CourseDetail> cdList = lecturerService.findCoursesByLecturerId(id);
        // return courseService.convertCoursesToJson(cdList);
        return cdList;
    }

    @GetMapping(value = "/student/course/{id}/new")
    public List<CourseDetail> getAvailableCoursesForStudent(@PathVariable int id){
        return studentService.findAvailableCoursesByStudentId(id);
    }


    @GetMapping("/course")
    public List<Course> getCourses(){
        return courseService.getAllCourses();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/coursedetails/{courseId}")
    public List<CourseDetail> getCourseDetails(@PathVariable int courseId) {
        Course c = courseService.findCourseById(courseId);
        if (c != null)
            return  c.getCourseDetails();
        else
            return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/coursedetails/batch/{id}")
    public CourseDetail getBatchById(@PathVariable int id) {
        CourseDetail cd = courseService.findCourseDetailById(id);
        return cd;
    }

    /*-----------------------------------CREATE FUNCTIONS--------------------------------------*/

    @PostMapping(value = "/login")
    public ResponseEntity loginSetToken(@RequestBody Admin admin){
        Admin user = adminService.findAdminByUsername(admin.getUsername());
        if (user.getPassword().equalsIgnoreCase(admin.getPassword())){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("token", user.getName());
            return new ResponseEntity<>(jsonObj, HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/student")
    public ResponseEntity createStudent(@RequestBody Student student){
        try {
            studentService.saveStudent(student);
            return new ResponseEntity<>(student, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value ="/lecturer")
    public ResponseEntity createLecturer(@RequestBody Lecturer lecturer){
        try {
            lecturerService.saveLecturer(lecturer);
            return new ResponseEntity<>(lecturer, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    @PostMapping(value ="/course")
    public ResponseEntity createCourse(@RequestBody Course course){
        try {
            courseService.saveCourse(course);
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value="/student/course/{username}/{id}")
    public ResponseEntity AddStudentToCourse(@PathVariable int id,@PathVariable String username){
        try {
            Student currStudent = studentService.findStudentByUsername(username);
            CourseDetail cd = courseService.findCourseDetailById(id);
            studentService.addCourseDetailToStudent(currStudent, cd);
            return new ResponseEntity<>(studentService.findAvailableCoursesByStudentId(currStudent.getStudentId()), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }


    /*-----------------------------------UPDATE FUNCTIONS--------------------------------------*/

    @PutMapping(value="/student/{id}")
    public ResponseEntity updateStudent (@RequestBody Student student, @PathVariable int id){
        
        try {
            Student currStudent = studentService.findStudentById(id);
            if(currStudent == null) throw new RuntimeException();
            
            currStudent.setActive(student.getActive());
            currStudent.setEmail(student.getEmail());
            currStudent.setName(student.getName());
            currStudent.setUsername(student.getUsername());
            if (student.getActive() == true){
                currStudent.setActive(true);
            }
            else {
                currStudent.setActive(false);
            }
            studentService.saveStudent(currStudent);
            
            return ResponseEntity.ok(currStudent);

        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping(value="/lecturer/{id}")
    public ResponseEntity updateLecturer (@RequestBody Lecturer lecturer, @PathVariable int id){
        
        try {
            Lecturer currLecturer = lecturerService.findLecturerById(id);
            if(currLecturer == null) throw new RuntimeException();
            
            currLecturer.setActive(lecturer.getActive());
            currLecturer.setEmail(lecturer.getEmail());
            currLecturer.setName(lecturer.getName());
            currLecturer.setUsername(lecturer.getUsername());
            currLecturer.setActive(lecturer.getActive());
            lecturerService.saveLecturer(lecturer);
            
            return ResponseEntity.ok(currLecturer);

        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping(value="/course/{id}")
    public ResponseEntity updateCourse (@RequestBody Course course, @PathVariable int id){
        
        try {
            Course currCourse = courseService.findCourseById(id);
            if(currCourse == null) throw new RuntimeException();
            
            currCourse.setName(course.getName());
            currCourse.setDescription(course.getDescription());
            courseService.saveCourse(course);
            
            return ResponseEntity.ok(currCourse);

        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }



    /*-----------------------------------DELETE FUNCTIONS--------------------------------------*/

    @DeleteMapping(value="/student/{id}")
    public ResponseEntity deleteStudent(@PathVariable int id){
        try{
            studentService.deleteStudentById(id);
            return ResponseEntity.ok(studentService.getAllStudents());
        } 
        catch (Exception e){
            return ResponseEntity.badRequest().body("Item couldnt be deleted");
        }
    }

    @DeleteMapping(value="/lecturer/{id}")
    public ResponseEntity deleteLecturer(@PathVariable int id){
        try{
            lecturerService.deleteLecturerById(id);
            return ResponseEntity.ok(lecturerService.getAllLecturers());
        } 
        catch (Exception e){
            return ResponseEntity.badRequest().body("Item couldnt be deleted");
        }
    }

    @DeleteMapping(value="/course/{id}")
    public ResponseEntity deleteCourse(@PathVariable int id){
        try{
            courseService.deleteCourse(id);
            return ResponseEntity.ok().build();
        } 
        catch (Exception e){
            return ResponseEntity.badRequest().body("Item couldnt be deleted");
        }
    }

    @DeleteMapping(value = "/student/course/{username}/{id}")
    public ResponseEntity deleteEnrolment(@PathVariable int id, @PathVariable String username){
        try{
            Student currStudent = studentService.findStudentByUsername(username);
            StudentCourse sc = studentService.findCourseByCourseIdStudentId(id, currStudent.getStudentId());
            studentService.removeStudentCourse(sc);
            List<StudentCourse> scList = studentService.findCoursesByStudentId(currStudent.getStudentId());
            return ResponseEntity.ok(studentService.convertSCToJson(scList));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Item couldnt be deleted");
        }
    }

    @DeleteMapping (value = "/lecturer/course/{username}/{id}")
    public ResponseEntity deleteCourseForLecturer(@PathVariable int id, @PathVariable String username){
        try{
            Lecturer currLecturer = lecturerService.findByUsername(username);
            CourseDetail cd = courseService.findCourseDetailById(id);
            lecturerService.removeLecturerFromCourseDetail(cd, currLecturer);
            return ResponseEntity.ok(lecturerService.findCoursesByLecturerId(currLecturer.getLecturerId()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Item couldnt be deleted");
        }
    }



}
