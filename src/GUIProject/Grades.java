package GUIProject;

public class Grades 
{
	private String letter_grade;
	private float grade;
	
	public Grades(float _grade) 
	{
		this.grade = _grade;
		this.m_update_letter(_grade);
	}
	
	public String getLetter_grade()
	{
		return letter_grade;
	}
	
	public float getGrade()
	{
		return grade;
	}
	
	private void m_update_letter(float grade) { //method to convert numeric letter to letter grade
		switch (((int)Math.floor(grade) % 100)/10) {
		case 10:
			this.letter_grade = "A";
			break;
		case 9:
			this.letter_grade = "A";
			break;
		case 8:
			this.letter_grade = "B";
			break;
		case 7:
			this.letter_grade = "C";
			break;
		case 6:
			this.letter_grade = "D";
			break;
		default:
			this.letter_grade = "F";
		}
		
		
	}
	
	public void setGrade(float grade) 
	{
		if (grade - Math.floor(grade) >= 0.5) {
			this.grade = (float)Math.ceil(grade);
		}
		else { this.grade = (float)Math.floor(grade); }
		this.m_update_letter(grade);
	}

	@Override
	public String toString() {
		return "Letter Grade =" + letter_grade + ", Numeric value =" + grade + "\n";
	}
	
	
}
