package team2.capSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team2.capSystem.model.*;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
	
	Boolean existsBy();
	
	@Query("SELECT l from Lecturer l WHERE l.username = :username")
	Lecturer findLecturerByUsername(@Param("username") String username);
  
	@Query("SELECT l from Lecturer l WHERE l.email = :email")
	Lecturer findLecturerByEmail(@Param("email") String email);
	
	@Query("SELECT l from Lecturer l WHERE l.name = :name")
	Lecturer findLecturerByName(@Param("name") String name);

	Lecturer findByUsername(String username);
	
	@Query("FROM Lecturer l WHERE l.username= :username AND l.password= :password AND l.active=1")
	Lecturer findLecturerByUsernameAndPassword(@Param("username") String u, @Param("password") String p);

}
