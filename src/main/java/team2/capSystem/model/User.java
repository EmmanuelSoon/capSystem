package team2.capSystem.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.*;

@MappedSuperclass
@NoArgsConstructor
@Data
public class User {
	@Column(unique = true)
    protected String username;
    protected String password;
    protected String name;
    protected String email;
    protected Boolean active = true;

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }


}
