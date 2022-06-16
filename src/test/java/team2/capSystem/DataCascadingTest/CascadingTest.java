package team2.capSystem.DataCascadingTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
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
@Import(CourseServiceImpl.class)
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

    // Testing Data Cascading between Course and CourseDetails( one to many)
    @Test
    @Order(1)
    public void Test1() {

        Course c =  new Course("testname", "testdesc");
        LocalDate start = LocalDate.now();
        for (int i = 1; i <= 3; i++) {
            c.Add(new CourseDetail(start, start.plusMonths(i), c));
        }

        int initialSize = cdRepo.findAll().size();

        cRepo.save(c);
        Course retrievedCourse = cRepo.findCourseByName("testname");
        Assertions.assertNotNull(retrievedCourse);

        List<CourseDetail> list = cdRepo.findAll();
        Assertions.assertEquals(initialSize+3, list.size());
    }


    // Testing Data Cascading between Student, StudentCourse
    @Test
    @Order(2)
    public void Test2() {
/*        //Create 3 test courseDetail with start and end date
        Course c =  new Course("testcourse", "testdesc");
        Student std = new Student("testStudent", "testStudent", "testStudent", "testEmail");
        LocalDate start = LocalDate.now();
        List<CourseDetail> courseDetailList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            CourseDetail cd = new CourseDetail(start, start.plusMonths(i), c);
            c.Add(cd);
            courseDetailList.add(cd);
        }
        cRepo.save(c);
        //create 3 nos of  StduentCourse Records based on the courseDetail created
        for (CourseDetail cd : courseDetailList) {
            std.add(new StudentCourse(std, cd, 2.0));
        }
        sRepo.save(std);
        Student testStudent = sRepo.findStudentByEmail("testEmail");
        Assertions.assertNotNull(testStudent);
        int studentId = testStudent.getStudentId();
        List<StudentCourse> list = scRepo.findSCByStudentId(studentId);

        Assertions.assertEquals(3, list.size());

        for (StudentCourse sc : list) {
            Assertions.assertEquals(2.0, sc.getGpa());
        }



        sRepo.delete(std);
        list = scRepo.findSCByStudentId(studentId);
        Assertions.assertEquals(0, list.size());*/
    }

    @Test
    @Order(3)
    public void Test3() {


    }
    @Test
    @Order(4)
    public void Test4() {

    }

    @Test
    @Order(5)
    public void Test5() {
        Course c =  new Course("testcourse", "testdesc");
        cRepo.save(c);
        Lecturer testlect = new Lecturer("test", "test", "test", "test@gmail.com");
        lRepo.save(testlect);

        CourseDetail testc = courseService.createCourseDetail(c, LocalDate.of(2021, 06, 15), LocalDate.of(2022, 06, 15));
        courseService.addLecturer(testc, testlect);

        testlect = lRepo.findLecturerByName("test");


        Assertions.assertEquals(testlect.getCourses().get(0), testc);

    }

    @Test
	@Order(6)
	public void FindCourseDetailTest() {
		Course courseFind = cRepo.findCourseByName("Java Programming");
		CourseDetail cd = new CourseDetail(LocalDate.of(2021, 1, 01), LocalDate.of(2021, 12, 30), courseFind);
		cdRepo.save(cd);
		CourseDetail course = cdRepo.findByCourseNameAndTime(courseFind, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 12, 30));
		Assertions.assertNotNull(course);
	}

}
