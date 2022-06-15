package team2.capSystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team2.capSystem.model.Course;
import team2.capSystem.repo.CourseRepository;

@SpringBootTest
class CapSystemApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private CourseRepository crepo;
}
