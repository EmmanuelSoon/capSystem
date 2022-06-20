package team2.capSystem.helper;

import java.time.LocalDate;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class lecturerCoursesTaught {

	private int courseBatchId;
	private int courseId;
	private String courseName;
	private String courseDescription;
	private LocalDate startDate;
	private LocalDate endDate;

}
