package GUIProject;

import java.util.ArrayList;
import java.util.Arrays;

public class Course {
	private String name;
	private String number;
	private int credits;

	

	private  String department;
	private  Instructor instructor;
	public ArrayList<Student> students = new ArrayList<Student>();
	public ArrayList<Grades> grades = new ArrayList<Grades>();

	public void addGrade(Grades temp) {
		grades.add(temp);
	}

	public void addStudent(Student temp) {
		students.add(temp);
	}

	public Course() {
		super();
	}

	public Course(String name, String number, int credits) 
	{
		super();
		this.name = name;
		this.number = number;
		this.credits = credits;
		setInstructor(new Instructor()); //TBA
	}

	// public Course(String name, int number, String department, String instructor,
	// ArrayList<Student> _stds, ArrayList<Grades> _grds)
	// {
	// super();
	// this.name = name;
	// this.number = number;
	// this.department = department;
	// this.instructor = instructor;
	// students = _stds;
	// grades = _grds;
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public int getCredits() {
		return this.credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	public float average() {
		float average = 0;
		for (int i = 0; i < grades.size(); i++) {
			average += grades.get(i).getGrade();
		}
		average = average / grades.size();
		return average;
	}

	public float stdDeviation() {
		float std = 0;
		for (int i = 0; i < grades.size(); i++) {
			std += Math.pow(grades.get(i).getGrade() - average(), 2);
		}
		return (float) Math.sqrt(std / grades.size());
	}

	public float maxgrade() {
		float max = 0;
		for (int i = 0; i < grades.size(); i++) {
			if (grades.get(i).getGrade() >= max) {
				max = grades.get(i).getGrade();
			}
		}
		return max;
	}

	public float mingrade() {
		float min = 100;
		for (int i = 0; i < grades.size(); i++) {
			if (grades.get(i).getGrade() <= min) {
				min = grades.get(i).getGrade();
			}
		}
		return min;
	}

	@Override
	public String toString() {
		
		return this.name + this.credits + this.number;
	}

}
