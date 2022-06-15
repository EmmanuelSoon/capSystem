package team2.capSystem.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team2.capSystem.model.*;

public interface CourseRepository extends JpaRepository<Course, Integer> {

  Boolean existsBy();

//    @Query("select c from Course c where c.name like :name")
//    List<Course> findCourseByName(@Param("name") String name);

	Course findCourseByName(String name);


}
