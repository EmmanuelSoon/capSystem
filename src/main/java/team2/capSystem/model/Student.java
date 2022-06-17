package team2.capSystem.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
public class Student extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    
    //Parent
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentCourse> courses = new ArrayList<>();

    @Builder
    public Student(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
