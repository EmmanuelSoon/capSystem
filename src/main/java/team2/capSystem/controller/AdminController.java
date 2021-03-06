package team2.capSystem.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import net.minidev.json.JSONObject;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import team2.capSystem.helper.batchCreator;
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

    @GetMapping(value = "/course/batch/{id}")
    public CourseDetail getCourseDetail(@PathVariable int id){
        return courseService.findCourseDetailById(id);
    }

    @GetMapping(value = "/lecturer/course/{id}")
    public List<CourseDetail> getCoursesForLecturer(@PathVariable int id){
        List<CourseDetail> cdList = lecturerService.findCoursesByLecturerId(id);
        // return courseService.convertCoursesToJson(cdList);
        return cdList.stream().distinct().collect(Collectors.toList());
    }

    @GetMapping(value = "/student/course/{id}/new")
    public List<CourseDetail> getAvailableCoursesForStudent(@PathVariable int id){
        return studentService.findAvailableCoursesByStudentId(id);
    }

    @GetMapping(value = "/lecturer/course/{id}/new")
    public List<CourseDetail> getAvailableCoursesForLecturer(@PathVariable int id){
        return lecturerService.findAvailableCoursesByLecturerId(id);
    }

    @GetMapping("/course")
    public List<Course> getCourses(){
        return courseService.getAllCourses();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/coursedetails/{courseId}")
    public List<CourseDetail> getCourseDetails(@PathVariable int courseId) {
        Course c = courseService.findCourseById(courseId);
        if (c != null)
            return courseService.findAllCourseDetailsByCourseId(courseId);
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
        try{
            Admin user = adminService.findAdminByUsername(admin.getUsername());
            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            String password = encoder.encode(admin.getPassword());

            if (user != null && encoder.matches(admin.getPassword(), user.getPassword())){
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("token", user.getName());
                return new ResponseEntity<>(jsonObj, HttpStatus.ACCEPTED);
            }
            else {                
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("status", "fail");
                return new ResponseEntity<>(jsonObj, HttpStatus.EXPECTATION_FAILED);
            }
        }
    catch (Exception e){
        return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value ="/student/verify/{id}")
    public ResponseEntity verifyStudentPassword(@RequestBody Student student, @PathVariable int id){
        try{
            Student currStudent = studentService.findStudentById(id);
            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            String password = encoder.encode(student.getPassword());

            if(currStudent != null && encoder.matches(student.getPassword(), currStudent.getPassword())){
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("status", true);
                return new ResponseEntity<>(jsonObj, HttpStatus.ACCEPTED);
            }
            else {
                throw new Exception();
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PostMapping(value ="/lecturer/verify/{id}")
    public ResponseEntity verifyLecturerPassword(@RequestBody Lecturer lecturer, @PathVariable int id){
        try{
            Lecturer currLecturer = lecturerService.findLecturerById(id);
            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            String password = encoder.encode(lecturer.getPassword());

            if(currLecturer != null && encoder.matches(lecturer.getPassword(), currLecturer.getPassword())){
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("status", true);
                return new ResponseEntity<>(jsonObj, HttpStatus.ACCEPTED);
            }
            else {
                throw new Exception();
            }
        }
        catch (Exception e){
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
            lecturerService.addNewLecturer(lecturer);
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
            boolean check = studentService.addCourseDetailToStudent(currStudent, cd);
            if(check){
                return new ResponseEntity<>(studentService.findAvailableCoursesByStudentId(currStudent.getStudentId()), HttpStatus.CREATED);
            }
            else {
                throw new Exception();
            }
        }
        catch (Exception e){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("message", "Item couldnt be added");
            return ResponseEntity.badRequest().body(jsonObj);
        }
    }

    @PostMapping(value="/lecturer/course/{username}/{id}")
    public ResponseEntity AddLecturerToCourse(@PathVariable int id,@PathVariable String username){
        try {
            Lecturer currLecturer = lecturerService.findByUsername(username);
            CourseDetail cd = courseService.findCourseDetailById(id);
            lecturerService.addCourseDetailToLecturer(currLecturer, cd);
            return new ResponseEntity<>(lecturerService.findAvailableCoursesByLecturerId(currLecturer.getLecturerId()), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PostMapping(value="/course/batch/{id}")
    public ResponseEntity AddCourseDetailToCourse(@RequestBody batchCreator cd, @PathVariable int id){
        try {
            Course c = courseService.findCourseById(id);
            if(c == null) throw new RuntimeException();
            CourseDetail newcd = new CourseDetail(LocalDate.parse(cd.getStartDate()), LocalDate.parse(cd.getEndDate()), c);
            newcd.setMaxSize(cd.getSize());
            c.getCourseDetails().add(newcd);
            courseService.saveCourse(c);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
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
            
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(student.getPassword());

            currStudent.setActive(student.getActive());
            currStudent.setEmail(student.getEmail());
            currStudent.setName(student.getName());
            currStudent.setUsername(student.getUsername());
            currStudent.setPassword(encodedPassword);
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
            
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(lecturer.getPassword());

            currLecturer.setActive(lecturer.getActive());
            currLecturer.setEmail(lecturer.getEmail());
            currLecturer.setName(lecturer.getName());
            currLecturer.setUsername(lecturer.getUsername());
            currLecturer.setPassword(encodedPassword);
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
            courseService.updateCourseDetails(course);
            return ResponseEntity.ok(courseService.findCourseById(id));

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
            courseService.deleteCourseById(id);
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
            boolean check = lecturerService.removeLecturerFromCourseDetail(cd, currLecturer);
            if (check){
                return ResponseEntity.ok(lecturerService.findCoursesByLecturerId(currLecturer.getLecturerId()));
            }
            else {
                throw new Exception();
            }
        }
        catch (Exception e){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("message", "Item couldnt be deleted");
            return ResponseEntity.badRequest().body(jsonObj);
        }
    }

    @DeleteMapping(value="/course/batch/{id}")
    public ResponseEntity deleteCourseDetail(@PathVariable int id){
        try{
            courseService.deleteCourseDetailById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Item couldnt be deleted");
        }
    }

}
