package team2.capSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team2.capSystem.model.*;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
	
	Boolean existsBy();
	
	@Query("SELECT l from Lecturer l WHERE l.username = :username")
	Lecturer findLecturerByUsername(@Param("username") String username);

}
