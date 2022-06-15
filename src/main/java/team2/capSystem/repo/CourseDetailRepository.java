package team2.capSystem.repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import team2.capSystem.model.*;

public interface CourseDetailRepository extends JpaRepository<CourseDetail, Integer> {
    Boolean existsBy();

    List<CourseDetail> findByStartDateAfter(LocalDate date);


}
