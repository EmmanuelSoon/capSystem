package team2.capSystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team2.capSystem.model.*;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {
    Boolean existsBy();

    //select * from coursedetail join studentcourse on coursedetail.id = studentcourse.coursebatchid where studetncourse.student id = id
    @Query("Select sc from StudentCourse sc where sc.student.studentId = :id")
    public List<StudentCourse> findSCByStudentId(@Param("id") Integer id);
}
