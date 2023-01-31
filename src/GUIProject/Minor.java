package GUIProject;

public class Minor 
{
	private String name;
	private String department;
	
	public Minor(String _name, String _department)
	{
		this.name = _name;
		this.department = _department;
	}
	
	public Minor() {}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getDepartment() 
	{
		return department;
	}
	
	public void setDepartment(String department) 
	{
		this.department = department;
	}

	@Override
	public String toString() 
	{
		return "Minor [name=" + name + ", department=" + department + "] \n";
	}	
}
