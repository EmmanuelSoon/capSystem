package team2.capSystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Student extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentCourse> courses = new ArrayList<>();

    public Student(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
