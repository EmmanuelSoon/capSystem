package team2.capSystem.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Admin extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int staffId;
    @Builder
    public Admin(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
