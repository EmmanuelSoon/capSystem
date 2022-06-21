package team2.capSystem.helper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class userChangePassword {
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
