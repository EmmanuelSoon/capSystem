package team2.capSystem.CRUDtests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import team2.capSystem.model.Course;
import team2.capSystem.repo.CourseRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CourseTest {
	@Autowired
	private CourseRepository crepo;

	@Test
	@Order(1)
	@Rollback(value = false)
	public void createCourse() {

		Course course1 = Course.builder()
				.name("Core Spring Training")
				.description("This course offers hands-on experience with Spring and its major features.")
				.build();

//		Course course2 = Course.builder()
//				.name("Spring Boot Developer Training")
//				.description(
//				"This course offers hands-on experience with Spring Boot and its major features, including auto-configuration, Spring data, Actuator, Spring Boot testing and more.")
//				.build();
//
//		Course course3 = Course.builder()
//				.name("Spring Security Training")
//				.description(
//				"This course offers hands-on experience with the major features of Spring Security, which includes configuration, authentication, authorization, password handling, testing, protecting against security threats, and the OAuth2 support to secure applications.")
//				.build();

		crepo.saveAndFlush(course1);
//		crepo.saveAndFlush(course2);
//		crepo.saveAndFlush(course3);

		System.out.println("Course ID:" + course1.getCourseId());
//		System.out.println("Course ID:" + course2.getCourseId());
//		System.out.println("Course ID:" + course3.getCourseId());

//		Assertions.assertNotNull(course1.getCourseId());
//		Assertions.assertNotNull(course2.getCourseId());
//		Assertions.assertNotNull(course3.getCourseId());
		assertThat(course1.getCourseId()).isGreaterThan(0);
//		assertThat(course2.getCourseId()).isGreaterThan(0);
//		assertThat(course3.getCourseId()).isGreaterThan(0);


	}

	@Test
	@Order(2)
	public void findCourseByName() {
		Course course = Course.builder()
				.name("Core Spring Training")
				.description("This course offers hands-on experience with Spring and its major features.")
				.build();

		Course course1 = crepo.findCourseByName("Core Spring Training");
		assertThat(course1.getName()).isEqualTo("Core Spring Training");

	}

	@Test
	@Order(3)
	@Rollback(value = false)
	public void editCourse() {
		Course course = crepo.findCourseByName("Core Spring Training");
		course.setDescription("updated description");
		crepo.save(course);
//		Assertions.assertEquals(course.getDescription(), "updated description");
		assertThat(course.getDescription()).isEqualTo("updated description");
		
	}

	

	@Test
	@Order(4)
	@Rollback(value = false)
	public void deleteCourse() {
		Course course = crepo.findCourseByName("Core Spring Training");
		crepo.deleteById(course.getCourseId());
		Course deletedCourse = crepo.findCourseByName("Core Spring Training");
//		Assertions.assertNull(course.getCourseId());
		assertThat(deletedCourse).isNull();

	}



}
