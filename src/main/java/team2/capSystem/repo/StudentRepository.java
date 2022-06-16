package team2.capSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team2.capSystem.model.*;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	Boolean existsBy();
	
	@Query("SELECT s from Student s WHERE s.name = :name")
	Student findStudentByName(@Param("name") String name);
	
	@Query("SELECT s from Student s WHERE s.email = :email")
	Student findStudentByEmail(@Param("email") String email);

	<List>Student findByUsername(String username);
}
