package team2.capSystem.helper;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
public class lecturerCoursesTaught {

	private int courseBatchId;
	private int courseId;
	private String courseName;
	private String courseDescription;
	private LocalDate startDate;
	private LocalDate endDate;

	public lecturerCoursesTaught(int courseBatchId, int courseId, String courseName, String courseDescription,
			LocalDate startDate, LocalDate endDate) {
		this.courseBatchId = courseBatchId;
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.startDate = startDate;
		this.endDate = endDate;

	}
}
