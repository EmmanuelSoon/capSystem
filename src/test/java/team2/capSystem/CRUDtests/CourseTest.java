package team2.capSystem.CRUDtests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import team2.capSystem.model.Course;
import team2.capSystem.repo.CourseRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CourseTest {
	@Autowired
	private CourseRepository courseRepo;

	@Test
	@Order(1)
	@Rollback(false)
	public void createCourseTest() {

		Course course = Course.builder()
				.name("Core Spring Training")
				.description("This course offers hands-on experience with Spring and its major features.")
				.build();

		courseRepo.save(course);

		System.out.println("Course ID:" + course.getCourseId());
		assertThat(course).isNotNull();
		assertThat(course.getCourseId()).isGreaterThan(0);

	}

	@Test
	@Order(2)
	public void findCourseByNameTest() {
		Course course = Course.builder()
				.name("Core Spring Training")
				.description("This course offers hands-on experience with Spring and its major features.")
				.build();

		courseRepo.saveAndFlush(course);
		Course courseFind = courseRepo.findCourseByName("Core Spring Training");
		assertThat(courseFind.getName()).isEqualTo("Core Spring Training");

	}

	@Test
	@Order(3)
	public void listCourseTest() {
		List<Course> courseList = (List<Course>) courseRepo.findAll();
		assertThat(courseList).size().isGreaterThan(0);
	}

	@Test
	@Order(4)
	public void editCourseTest() {
		Course course = Course
				.builder()
				.name("Core Spring Training")
				.description("This course offers hands-on experience with Spring and its major features.")
				.build();

		courseRepo.saveAndFlush(course);
		Course courseFind = courseRepo.findCourseByName("Core Spring Training");
		courseFind.setDescription("updated description");
		courseRepo.saveAndFlush(courseFind);
		Course editedCourse = courseRepo.findCourseByName("Core Spring Training");
		assertThat(editedCourse.getDescription()).isEqualTo("updated description");

	}

	@Test
	@Order(5)
	@Rollback(false)
	public void deleteCourseTest() {
		Course courseFind = courseRepo.findCourseByName("Core Spring Training");
		courseRepo.deleteById(courseFind.getCourseId());
		Course deletedCourse = courseRepo.findCourseByName("Core Spring Training");
		assertThat(deletedCourse).isNull();

	}
	
}
