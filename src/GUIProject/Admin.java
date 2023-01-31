package GUIProject;

public class Admin extends Person 
{    
    Admin()
    {
        super();
        setEmail("");
    }

    public Admin(String name, String email, int ID, String password)
    {
        super(name, ID, password);
        setEmail(email);
    }
}