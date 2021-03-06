package team2.capSystem.repo;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team2.capSystem.model.*;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {
    Boolean existsBy();

    //select * from coursedetail join studentcourse on coursedetail.id = studentcourse.coursebatchid where studetncourse.student id = id
    @Query("Select sc from StudentCourse sc where sc.student.studentId = :id")
    public List<StudentCourse> findSCByStudentId(@Param("id") Integer id);

    @Query("Select sc from StudentCourse sc where sc.student.studentId = :studentId AND sc.course.id = :courseId")
    public StudentCourse findCourseByCourseIdStudentId(int courseId, int studentId);
    
    public List<StudentCourse> findByCourse(CourseDetail cd);
    
    @Transactional
	@Modifying
	@Query(value = "update student_course set gpa = :selectedGPA where student_student_id = :studentId and course_batch_id = :coursebatchID", nativeQuery = true)
	public void updateStudentCourseGPA(@Param("coursebatchID") int coursebatchID, @Param("studentId") int studentId,
			@Param("selectedGPA") double selectedGPA);

    
    
}


