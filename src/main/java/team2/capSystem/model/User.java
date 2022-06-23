package team2.capSystem.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
@Data
public class User {
	@Column(unique = true)
    @NotEmpty(message = "Username cannot be empty.")
    @Size(min=3, max=16, message="Needs to be within 3 to 15 characters.")
    protected String username;
    @NotEmpty(message = "Password cannot be empty.")
    @Size(min=3, message="Needs to be more than 3 characters.")
    protected String password;
    @Size(min=3, max=16, message="Needs to be within 3 to 15 characters.")
    protected String name;
    @Email(message="Invalid email")
    protected String email;
    protected Boolean active = true;
       
    
    public User(String username, String password, String name, String email) {
        this.username = username;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        this.password = encodedPassword;
        this.name = name;
        this.email = email;
    }


}
