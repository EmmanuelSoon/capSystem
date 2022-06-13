package team2.capSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import team2.capSystem.model.*;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    
}
