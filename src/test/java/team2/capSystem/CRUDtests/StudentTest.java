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

import team2.capSystem.model.Student;
import team2.capSystem.repo.StudentRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class StudentTest {
	@Autowired
	private StudentRepository studentRepo;

	@Test
	@Order(1)
	@Rollback(false)
	public void createStudentTest() {
		Student student = Student.builder()
				.username("student1")
				.password("password1")
				.name("student1")
				.email("student1@gmail.com")
				.build();
		studentRepo.save(student);
		assertThat(student).isNotNull();
		assertThat(student.getStudentId()).isGreaterThan(0);
	}

	@Test
	@Order(2)
	public void findStudentTest() {
		Student student = Student.builder()
				.username("student2")
				.password("password2")
				.name("student2")
				.email("student2@gmail.com")
				.build();
		studentRepo.saveAndFlush(student);
		Student studentFind = studentRepo.findByUsername("student2");
		assertThat(studentFind.getName()).isEqualTo("student2");
	}

	@Test
	@Order(3)
	public void listStudentTest() {
		List<Student> studentList = (List<Student>) studentRepo.findAll();
		assertThat(studentList).size().isGreaterThan(0);
	}

	@Test
    @Order(4)
    public void editStudentTest() {
		Student student = Student.builder()
				.username("student2")
				.password("password2")
				.name("student2")
				.email("student2@gmail.com")
				.build();
		studentRepo.saveAndFlush(student);
		Student studentFind = studentRepo.findStudentByName("student2");
        studentFind.setPassword("changedpassword");
        Student editedStudent = studentRepo.findStudentByName("student2");
        assertThat(editedStudent.getPassword()).isEqualTo("changedpassword");
	}
	
    @Test
    @Order(5)
    @Rollback(false)
    public void deleteStudentTest() {
    	Student studentFind = studentRepo.findStudentByName("student1");
    	studentRepo.deleteById(studentFind.getStudentId());
    	Student deletedStudent = studentRepo.findStudentByName("student1");
    	assertThat(deletedStudent).isNull();
    }
	
}
