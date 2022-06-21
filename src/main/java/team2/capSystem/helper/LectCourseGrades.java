package team2.capSystem.helper;

public enum LectCourseGrades {
	
	SELECTDEFAULT("Select a Grade", -1),
	S("S", 5.0),
	APLUS("A+",4.5),
	A("A", 4.0),
	BPLUS("B+", 3.5),
	B("B", 3.0),
	CPLUS("C+", 2.5),
	C("C", 2.0),
	DPLUS("D+", 1.5),
	D("D", 1.0),
	F("F", 0.0);
	
	private double gradeValue;
	private String gradeName;
	
	LectCourseGrades(String gradeName, double gradeValue) {
		this.gradeName = gradeName;
		this.gradeValue = gradeValue;
	}

	public double getGradeValue() {
		return gradeValue;
	}
	
	public String getGradeName() {
		return gradeName;
	}
	
}



//BLACK("Black"), 
//BLUE("Blue"), 
//RED("Red"), 
//YELLOW("Yellow"), 
//GREEN("Green"),
//ORANGE("Orange"), 
//PURPLE("Purple"), 
//WHITE("White");
//
//private final String displayValue;
//
//private Color(String displayValue) {
//    this.displayValue = displayValue;
//}
//
//public String getDisplayValue() {
//    return displayValue;
//}
