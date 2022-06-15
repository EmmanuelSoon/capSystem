package team2.capSystem.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team2.capSystem.model.*;

public interface CourseDetailRepository extends JpaRepository<CourseDetail, Integer> {
    Boolean existsBy();

    List<CourseDetail> findByStartDateAfter(LocalDate date);
}
