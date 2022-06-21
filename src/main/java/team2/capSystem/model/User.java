package team2.capSystem.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
<<<<<<< Updated upstream
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
=======
>>>>>>> Stashed changes
import javax.validation.constraints.NotEmpty;

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
    protected String username;
	@NotEmpty(message = "Password cannot be empty.")
    protected String password;
    protected String name;
    @Email
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
