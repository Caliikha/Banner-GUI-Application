package GUIProject;

import java.util.ArrayList;

public class Instructor extends Person
{
    String department;
    ArrayList<Course> courses = new ArrayList<Course>();
    //ArrayList<Grades> grades = new ArrayList<Grades>();

    //public ArrayList<Grades> getGrades()  {return grades;}
    public ArrayList<Course> getCourses() {return courses;}

    //public void setGrades(ArrayList<Grades> grades)   {this.grades = grades;}
    public void setCourses(ArrayList<Course> courses) {this.courses = courses;}
    
    Instructor()
    {
        super("TBA",-1,""); //TBA INS
        setEmail("");
        department = "";
    }

    Instructor(String _name, String _email, int _ID, String department, String password)
    {
        super(_name, _ID, password);
        setEmail(_email);
        this.department = department;
    }

    public void change_name(String new_name) 
	{
		name = new_name;
	}
    public void change_pass(String new_pass)
    {
        password = new_pass;
    }

    public void addCourse(Course new_course) {
        courses.add(new_course);
    }

    public int get_num_courses() {
        return courses.size();
    }

    public String toString() {
        return "Name: " + name + ", ID: " + ID + ", Username: " + email + ", Department: " + department;
    }
}   
 // s S