package team2.capSystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team2.capSystem.model.*;

public interface CourseRepository extends JpaRepository<Course, Integer> {

  Boolean existsBy();

  @Query("SELECT c FROM Course c WHERE c.name like :name")
  Course findCourseByName(@Param("name") String name);
  

}
