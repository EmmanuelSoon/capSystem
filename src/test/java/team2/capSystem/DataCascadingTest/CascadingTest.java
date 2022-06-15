package team2.capSystem.DataCascadingTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.w3c.dom.ls.LSInput;
import team2.capSystem.model.Course;
import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.Student;
import team2.capSystem.model.StudentCourse;
import team2.capSystem.repo.CourseDetailRepository;
import team2.capSystem.repo.CourseRepository;
import team2.capSystem.repo.LecturerRepository;
import team2.capSystem.repo.StudentRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
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


    // Testing Data Cascading between Course and CourseDetails( one to many)
    @Test
    @Order(1)
    public void Test1() {

        Course c =  new Course("testname", "testdesc");
        LocalDate start = LocalDate.now();
        List<CourseDetail> courseDetailList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            c.Add(new CourseDetail(start, start.plusMonths(i), c));
        }

        int initialSize = cdRepo.findAll().size();

        cRepo.save(c);
        Course retrievedCourse = cRepo.findCourseByName("testname");
        Assertions.assertNotNull(retrievedCourse);

        List<CourseDetail> list = cdRepo.findAll();
        Assertions.assertEquals(initialSize+3, list.size());
        cRepo.delete(c);
        list = cdRepo.findAll();
        Assertions.assertEquals(initialSize, list.size());
    }


    // Testing Data Cascading between Student, StudentCourse
    @Test
    @Order(2)
    public void Test2() {

    }

    @Test
    @Order(3)
    public void Test3() {

    }
    @Test
    @Order(4)
    public void Test4() {

    }

}
