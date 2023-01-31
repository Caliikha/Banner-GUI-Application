package GUIProject;

import javax.swing.JOptionPane;

public class CourseRegistrationException extends Exception {
    String reason;

    CourseRegistrationException(String r)
    {
        reason = r;
    }

    public void errorPopUp()
    {
        JOptionPane.showMessageDialog(null, reason, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
}
