package team2.capSystem.helper;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class nominalRoll {

	private Integer studentId;
	private String studentName;
	private String studentEmail;
	private Double gpa;
	

}
