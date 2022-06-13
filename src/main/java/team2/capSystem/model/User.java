package team2.capSystem.model;

import javax.persistence.MappedSuperclass;

import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
public class User {
    protected String username;
    protected String password;
    protected String name;
    protected String email;

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

}
