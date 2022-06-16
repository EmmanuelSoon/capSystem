package team2.capSystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class CourseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_batch_id")
    private int id;

    private LocalDate startDate;
    private LocalDate endDate;
    
    @ManyToMany(mappedBy = "courses")
    private List<Lecturer> lecturers = new ArrayList<>();


    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<StudentCourse> student_course = new ArrayList<>();

    @ManyToOne 
    private Course course;

    public CourseDetail(LocalDate startDate, LocalDate endDate, Course course) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.course = course;
    }


    //Ulility Methods 
    public void addLecturer(Lecturer lecturer){
        this.lecturers.add(lecturer);
        lecturer.getCourses().add(this);
    }

    public void removeLecturer(Lecturer lecturer){
        this.lecturers.remove(lecturer);
        lecturer.getCourses().remove(this);
    }
}
