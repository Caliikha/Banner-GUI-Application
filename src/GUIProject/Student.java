package GUIProject;

import java.util.ArrayList;

public class Student extends Person {
    private String major;
    private float GPA;
    private int NumMinors; // TODO(presentation)[remove minors, unrelated to program, make sure that nothing else is using them if removed]
    private String gender;
    private ArrayList<Minor> minors = new ArrayList<Minor>(); // TODO(presentation)[remove minors, unrelated to program, make sure that nothing else is using them if removed]
    public ArrayList<Course> courses = new ArrayList<Course>();
    public ArrayList<Grades> grades = new ArrayList<Grades>();

    public Grades get_grades(int i) {
        return grades.get(i);
    }

    public Course get_courses(int i) {
        return courses.get(i);
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    public int get_num_courses() {
        return courses.size();
    }

    public void setCourses(ArrayList<Course> _courses) {
        courses = _courses;
    }

    public Student(String _name, int _ID, String _gender, String _major, String _password) {
        super(_name, _ID, _password);
        this.major = _major;
        this.gender = _gender;
        this.email = _gender.charAt(0) + "000" + _ID + "@aus.edu";
    }

    public Student(String _name, int _ID, String _gender, String _major, float _GPA, int _NM) { // TODO(presentation)
                                                                                                //[constructor that should not be used, it has "minors" in it which is not part of the final program. use above constructor instead]
        super(_name, _ID, _major);
        this.email = _gender.charAt(0) + "000" + _ID + "@aus.edu";
        this.major = _major;
        this.GPA = _GPA;
        this.NumMinors = _NM;
        this.gender = _gender;
    }

    public Student() {
        super();
    }

    public String get_name() {
        return name;
    }

    public int get_ID() {
        return ID;
    }

    public String get_email() {
        return email;
    }

    public String get_major() {
        return major;
    }

    public float get_GPA() {
        return GPA;
    }

    public int get_noMinors() {// TODO(presentation)[remove minors, unrelated to program, make sure that nothing else is using them if removed]
        return NumMinors;
    }

    public String get_gender() {
        return gender;
    }

    public ArrayList<Minor> getMinors() {// TODO(presentation)[remove minors, unrelated to program, make sure that nothing else is using them if removed]
        return minors;
    }

    public void setMinors(ArrayList<Minor> minors) {// TODO(presentation)[remove minors, unrelated to program, make sure that nothing else is using them if removed]
        this.minors = minors;
    }

    public void addMinor(Minor temp) { // TODO(presentation)[remove minors, unrelated to program, make sure that nothing else is using them if removed]
        minors.add(temp);
    }

    public void change_name(String new_name) {
        name = new_name;
    }

    public void set_name(String _name) {
        name = _name;
    }

    public void set_ID(int _ID) {
        ID = _ID;
    }

    public void set_email(String _email) {
        email = _email;
    }

    public void set_major(String _major) {
        major = _major;
    }

    public void set_GPA(float _GPA) {
        GPA = _GPA;
    }

    public void set_noMinors(int _NM) {// TODO(presentation)[remove minors, unrelated to program, make sure that nothing else is using them if removed]
        NumMinors = _NM;
    }

    public void set_gender(String _gender) {
        gender = _gender;
    }

    public void set_grade(float grade) {
        Grades grad = new Grades(grade);
        grades.add(grad);
    }

    public String toString() { // TODO(presentation)[remove minors, unrelated to program, make sure that nothing else is using them if removed]
        return "Name: " + name + ", ID: " + ID + ", Gender: " + gender + ", email: " + email + ", major: " + major
                + ", Minors Number: " + NumMinors + "\n" + "GPA: " + GPA;
    }
}