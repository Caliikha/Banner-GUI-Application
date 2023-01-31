package GUIProject;

import javax.swing.JOptionPane;

public class IncorrectPasswordException extends Exception {
    
    public void errorPopUp()
    {
        JOptionPane.showMessageDialog(null, "Incorrect password! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
