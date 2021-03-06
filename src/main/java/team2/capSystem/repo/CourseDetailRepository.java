package team2.capSystem.repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team2.capSystem.model.*;

public interface CourseDetailRepository extends JpaRepository<CourseDetail, Integer> {
    Boolean existsBy();

    List<CourseDetail> findByStartDateAfter(LocalDate date);

    @Query("SELECT cd FROM CourseDetail cd WHERE cd.course = :course AND cd.startDate= :start AND cd.endDate = :end")
    CourseDetail findByCourseNameAndTime(Course course, LocalDate start, LocalDate end);

    List<CourseDetail> findByCourseNameLike(String searchString);

    List<CourseDetail> findByStartDateAfterAndEndDateBefore(LocalDate start, LocalDate end);

    List<CourseDetail> findByEndDateBefore(LocalDate date);

}
