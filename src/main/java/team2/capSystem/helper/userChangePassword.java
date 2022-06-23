package team2.capSystem.helper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class userChangePassword {
    @NotEmpty(message="Must not be empty.")
    private String oldPassword;
    @NotEmpty(message="Must not be empty.")
    @Size(min=3, message="Needs to be more than 3 characters.")
    private String newPassword;
}
