package GUIProject;

public class Employee extends Person {
	String position;
	String department;
	
	public Employee(String _name, int _ID, String _department, String _position, String _password) {
		super(_name, _ID, _password);
		this.email = _name + "@aus.edu";
		this.position = _position;
		this.department = _department;
	}
	
	public Employee() {super();} //creates a generic employee
	
	

	public String getName() {
		return name;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException { //clones an employee
		Person copy = (Employee)super.clone();
		return copy;
	}
	
	@Override
	public String toString() {
		return "name=" + name + ", ID=" + ID + ", position=" + position + ", email=" + email + ", department="
				+ department + "]";
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	
	
}
