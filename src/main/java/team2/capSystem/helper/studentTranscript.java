package team2.capSystem.helper;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor	
@AllArgsConstructor
public class studentTranscript {
	private int courseBatchId;
	private String courseName;
	private LocalDate dateOfCompletion;
	private double gpa;
}
