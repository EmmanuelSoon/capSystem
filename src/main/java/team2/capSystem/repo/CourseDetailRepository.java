package team2.capSystem.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team2.capSystem.model.*;

public interface CourseDetailRepository extends JpaRepository<CourseDetail, Integer> {
    Boolean existsBy();

    //get available courses from date
    @Query("Select cd from CourseDetail cd where cd.startDate >:nowDate ")
    List<CourseDetail> getCourseAvail(@Param("nowDate") Date nowDate);
}
