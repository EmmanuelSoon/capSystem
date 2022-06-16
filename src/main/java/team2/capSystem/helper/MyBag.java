package team2.capSystem.helper;

import lombok.Data;
import lombok.NoArgsConstructor;
import team2.capSystem.model.User;

@Data @NoArgsConstructor
public class MyBag {
	private User user;
	
	public MyBag(User user) {
		super();
		this.user = user;
	}

}