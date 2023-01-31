package GUIProject;

import javax.swing.JOptionPane;

public class UserNotFoundException extends Exception {
    public void errorPopUp()
    {
        JOptionPane.showMessageDialog(null, "User not found! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
