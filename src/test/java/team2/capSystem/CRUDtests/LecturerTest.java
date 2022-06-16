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

import team2.capSystem.model.Lecturer;
import team2.capSystem.repo.LecturerRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LecturerTest {
    @Autowired
    private LecturerRepository lecturerRepo;
    
    @Test
    @Order(1)
    @Rollback(false)
    public void createLecturerTest() {
    	Lecturer lecturer = Lecturer.builder()
    			.username("lecturer1")
    			.password("password1")
    			.name("lecturer1")
    			.email("lecturer1@gmail.com")
    			.build();
    lecturerRepo.save(lecturer);
    assertThat(lecturer).isNotNull();
    assertThat(lecturer.getLecturerId()).isGreaterThan(0);
    }
    
    @Test
    @Order(2)
    public void findLecturerByNameTest() {
    	Lecturer lecturer = Lecturer.builder()
    			.username("lecturer2")
    			.password("password2")
    			.name("lecturer2")
    			.email("lecturer2@gmail.com")
    			.build();
    lecturerRepo.saveAndFlush(lecturer);
    Lecturer lecturerFind = lecturerRepo.findLecturerByName("lecturer2");
    assertThat(lecturerFind.getName()).isEqualTo("lecturer2");
    }
    
    @Test
    @Order(3)
    public void listLecturerTest() {
    	List<Lecturer> lecturerList = (List<Lecturer>) lecturerRepo.findAll();
    	assertThat(lecturerList).size().isGreaterThan(0);    	
    }
    
    @Test
    @Order(4)
    public void editLecturerTest() {
    	Lecturer lecturer = Lecturer.builder()
    			.username("lecturer2")
    			.password("password2")
    			.name("lecturer2")
    			.email("lecturer2@gmail.com")
    			.build();
        lecturerRepo.saveAndFlush(lecturer);
        Lecturer lecturerFind = lecturerRepo.findLecturerByName("lecturer2");
        lecturerFind.setPassword("changedpassword");
        Lecturer editedLecturer = lecturerRepo.findLecturerByName("lecturer2");
        assertThat(editedLecturer.getPassword()).isEqualTo("changedpassword");
    }
    
    @Test
    @Order(5)
    @Rollback(false)
    public void deleteLecturerTest() {
    	Lecturer lecturerFind = lecturerRepo.findLecturerByName("lecturer1");
    	lecturerRepo.deleteById(lecturerFind.getLecturerId());
    	Lecturer deletedLecturer = lecturerRepo.findLecturerByName("lecturer1");
    	assertThat(deletedLecturer).isNull();
    	
    }
}
