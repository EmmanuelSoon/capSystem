package team2.capSystem.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class Admin extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int staffId;
    public Admin(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
