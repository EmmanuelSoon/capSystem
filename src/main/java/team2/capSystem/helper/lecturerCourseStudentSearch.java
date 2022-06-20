package team2.capSystem.helper;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class lecturerCourseStudentSearch {

	private String keyword;

	public boolean keywordNullOrEmpty() {
		if (this.keyword == null || this.keyword == "") {
			return true;
		}

		return false;
	}

}
