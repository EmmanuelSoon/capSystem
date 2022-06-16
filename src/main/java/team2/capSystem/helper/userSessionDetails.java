package team2.capSystem.helper;

import team2.capSystem.model.User;
import lombok.*;

@Data
@NoArgsConstructor
public class userSessionDetails {
	private User user;
	private int userId;
	private String userRole;

	public userSessionDetails(User user, int userId, String userRole) {
		super();
		this.user = user;
		this.userId = userId;
		this.userRole = userRole;
	}
}