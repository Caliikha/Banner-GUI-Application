package GUIProject;

abstract public class Person implements Cloneable {
	String name;
	String email;
	int ID;
	String password;
	public Person() {}
	public Person(String _name, int _ID, String password) 
	{
		this.name = _name;
		this.ID = _ID;
		this.password = password;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public int getID() 
	{
		return ID;
	}
	
	public void setID(int iD) 
	{
		ID = iD;
	}
	
	
}