package team2.capSystem.DataCascadingTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.w3c.dom.ls.LSInput;
import team2.capSystem.model.Course;
import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.Lecturer;
import team2.capSystem.model.Student;
import team2.capSystem.model.StudentCourse;
import team2.capSystem.repo.*;
import team2.capSystem.services.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Import({CourseServiceImpl.class, LecturerServiceImpl.class, SeedDBServiceImpl.class, AdminServiceImpl.class, StudentServiceImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CascadingTest {

    @Autowired
    CourseRepository cRepo;
    @Autowired
    CourseDetailRepository cdRepo;
    @Autowired
    LecturerRepository lRepo;
    @Autowired
    StudentRepository sRepo;

    @Autowired
    StudentCourseRepository scRepo;

    @Autowired
	CourseServiceImpl courseService;

    @Autowired
    LecturerServiceImpl lecturerService;

    @Autowired
    StudentServiceImpl studentService;

    // Testing Data Cascading between Course and CourseDetails( one to many)
    @Test
    @Order(1)
    public void createTest() {

        Course c =  new Course("testname", "testdesc");
        LocalDate start = LocalDate.now();
        for (int i = 1; i <= 3; i++) {
            c.Add(new CourseDetail(start, start.plusMonths(i), c));
        }

        int initialSize = courseService.getAllCourseDetails().size();

        courseService.saveCourse(c);
        Course retrievedCourse = courseService.getCourseByName("testname");

        Assertions.assertNotNull(retrievedCourse);

        int updatedSize = courseService.getAllCourseDetails().size();
        Assertions.assertEquals(initialSize+3, updatedSize);
    }


    // Testing Data Cascading between Student, StudentCourse
    @Test
    @Order(2)
    public void updateTest() {

        studentService.createStudent("testsusername", "testpassword", "testname", "testemail@gmail.com");
        Student s = studentService.findStudentByUsername("testsusername");

        Assertions.assertNotNull(s);
        Assertions.assertEquals("testsusername", s.getUsername());

        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String password = encoder.encode("testpassword");
        Assertions.assertTrue(encoder.matches( "testpassword", s.getPassword()));

        s.setUsername("teststd-updated");
        studentService.saveStudent(s);

        Student after = studentService.findStudentByUsername("teststd-updated");
        Assertions.assertEquals("teststd-updated", after.getUsername());
        Assertions.assertEquals("testname", after.getName());
        Assertions.assertEquals("testemail@gmail.com", after.getEmail());
        Assertions.assertNull(studentService.findStudentByUsername("teststd"));
    }

    @Test
    @Order(3)
    public void deleteTest1() {
        //lecturer and course
        Lecturer l = new Lecturer("testlecture", "testpassword", "testname", "test@gmail.com");
        Course c =  new Course("testname", "testdesc");
        CourseDetail cd1 = new CourseDetail(LocalDate.of(2023, 1, 01), LocalDate.of(2023, 12, 30), c);
        CourseDetail cd2 = new CourseDetail(LocalDate.of(2024, 1, 01), LocalDate.of(2024, 12, 30), c);
        List<CourseDetail> list = new ArrayList<>();
        list.add(cd1);
        list.add(cd2);
        c.setCourseDetails(list);
        l.setCourses(list);
        lecturerService.addNewLecturer(l);
        courseService.saveCourse(c);

        Assertions.assertNotNull(lecturerService.findByUsername("testlecture"));
        Assertions.assertNotNull(courseService.getCourseByName("testname"));
        Assertions.assertNotNull(courseService.findExactCourseDetail(c, cd1.getStartDate(), cd1.getEndDate()));
        Assertions.assertNotNull(courseService.findExactCourseDetail(c, cd2.getStartDate(), cd2.getEndDate()));
        Assertions.assertEquals(2, lecturerService.findByUsername("testlecture").getCourses().size());


        lecturerService.delete(lecturerService.findByUsername("testlecture"));
        lecturerService.deleteLecturerById(lecturerService.findByUsername("testlecture").getLecturerId());
        Assertions.assertNull(lecturerService.findByUsername("testlecture"));
        /*Assertions.assertEquals(false, lecturerService.findByUsername("testlecture").getActive());*/

        courseService.deleteCourseById(courseService.getCourseByName("testname").getCourseId());
        Assertions.assertNull(courseService.getCourseByName("testname"));
        Assertions.assertNull(courseService.findExactCourseDetail(c, cd1.getStartDate(), cd1.getEndDate()));
        Assertions.assertNull(courseService.findExactCourseDetail(c, cd2.getStartDate(), cd2.getEndDate()));
    }
    @Test
    @Order(4)
    public void deleteTest2() {
        //Delete Stduent and stduentcourse
        Course c =  new Course("testname", "testdesc");
        CourseDetail cd = new CourseDetail(LocalDate.of(2021, 1, 01), LocalDate.of(2021, 12, 30), c);
        c.Add(cd);
        courseService.saveCourse(c);
        Student s = new Student("teststd", "testpw", "testname", "testemail@gmail.com");
        s.getCourses().add(new StudentCourse(s, cd, 5.0));
        studentService.saveStudent(s);
        Assertions.assertNotNull(studentService.findStudentByUsername("teststd"));
        Assertions.assertNotNull(courseService.getCourseByName("testname"));
        Assertions.assertEquals(1, courseService.getCourseByName("testname").getCourseDetails().size());
        Assertions.assertNotNull(courseService.findExactCourseDetail(c, cd.getStartDate(), cd.getEndDate()));
        Assertions.assertEquals(1, studentService.findStudentByUsername("teststd").getCourses().size());

        studentService.deleteStudentById(studentService.findStudentByUsername("teststd").getStudentId());
        Assertions.assertNull(studentService.findStudentByUsername("teststd"));
        Assertions.assertEquals(0, courseService.getCourseByName("testname").getCourseDetails().get(0).getStudent_course().size());
    }

/*    @Test
    @Order(5)
    public void Test5() {
        Course c =  new Course("testcourse", "testdesc");
        cRepo.save(c);
        Lecturer testlect = new Lecturer("test", "test", "test", "test@gmail.com");
        lRepo.save(testlect);

        CourseDetail testc = courseService.createCourseDetail(c, LocalDate.of(2021, 06, 15), LocalDate.of(2022, 06, 15));
        courseService.addLecturer(testc, testlect);

        testlect = lRepo.findLecturerByName("test");


        //Assertions.assertEquals(testlect.getCourses().get(0), testc);

    }*/

    @Test
	@Order(6)
	public void FindCourseDetailTest() {
		Course courseFind = cRepo.findCourseByName("Java Programming");
		CourseDetail cd = new CourseDetail(LocalDate.of(2021, 1, 01), LocalDate.of(2021, 12, 30), courseFind);
		cdRepo.save(cd);
		CourseDetail course = cdRepo.findByCourseNameAndTime(courseFind, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 12, 30));
		Assertions.assertNotNull(course);
	}

    
    @Test
	@Order(7)
	public void DeleteLecturerTest() {
        lecturerService.createLecturer("test", "test", "test", "test@gmail.com");
        Lecturer liufan = lecturerService.findByUsername("liufan");
        Lecturer tin = lecturerService.findByUsername("tin");
        Lecturer test = lecturerService.findByUsername("test");
        
        
        Course cookingCourse = courseService.getCourseByName("Cooking with Pork");
        CourseDetail cook1 = courseService.createCourseDetail(cookingCourse, LocalDate.of(2024, 06, 20), LocalDate.of(2025, 06, 15));
/*
        System.out.println(courseService.findExactCourseDetail(cookingCourse, LocalDate.of(2024, 06, 20), LocalDate.of(2025, 06, 15)).getLecturers().size());
*/
        courseService.addLecturer(cook1, liufan);
        courseService.addLecturer(cook1, tin);
        courseService.addLecturer(cook1, test);
/*
        System.out.println(courseService.findExactCourseDetail(cookingCourse, LocalDate.of(2024, 06, 20), LocalDate.of(2025, 06, 15)).getLecturers().size());
*/
        lecturerService.deleteLecturerById(test.getLecturerId());
        Assertions.assertNull(lecturerService.findByUsername("test"));

		CourseDetail course = cdRepo.findByCourseNameAndTime(cookingCourse, LocalDate.of(2024, 06, 20), LocalDate.of(2025, 06, 15));
        Assertions.assertEquals(course.getLecturers().size(), 2);

	}
}
