package team2.capSystem.repo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import team2.capSystem.model.*;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
	Boolean existsBy();
	
	@Query("SELECT a from Admin a WHERE a.username = :username")
	Admin findAdminByUsername(@Param("username") String username);
  
	@Query("SELECT a from Admin a WHERE a.name = :name")
	Admin findAdminByName(@Param("name") String name);
		
	@Query("SELECT a from Admin a WHERE a.email = :email")
	Admin findAdminByEmail(@Param("email") String email);
	
	@Query("FROM Admin a WHERE a.username= :username AND a.password= :password AND a.active= 1")
	Admin findAdminByUsernameAndPassword(@Param("username") String u, @Param("password") String p);
}

