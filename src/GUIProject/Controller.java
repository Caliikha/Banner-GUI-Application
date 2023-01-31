package GUIProject;

import java.io.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.StringTokenizer;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class Controller {
    private static int loginTries = 0;


    public static void findUser(LoginFrame frame, String user, String pass) 
    {
        try {
        boolean isFound = false;
        for (int i = 0; i < Database.students.size(); i++) {
            try{
                synchronized (Database.students.get(i)) //makes sure that the student is only being accessed by one user at a time
                 {
            if (user.equals(Database.students.get(i).email)) {
                if (pass.equals(Database.students.get(i).password)) {
                    System.out.println(Database.students.get(i).name + " is a student. Successful login!");
                    isFound = true;
                    frame.setVisible(false);
                    MainFrame fr = new MainFrame(Database.students.get(i));
                    fr.setVisible(true);
                }
                else { throw new IncorrectPasswordException();}
             }}}
              catch(IncorrectPasswordException e)
                {
                    e.errorPopUp();
                     loginTries++;
                     if(loginTries==3) System.exit(-1);
                }

                
            
        }

        for(int i = 0; i<Database.instructors.size();i++)
        {
            try {
                synchronized(Database.instructors.get(i)) //makes sure that the instructor is only being accessed by one user at a time
                {
            if (user.equals(Database.instructors.get(i).email)) {
                if (pass.equals(Database.instructors.get(i).password)) {
                    System.out.println(Database.instructors.get(i).name + " is an instructor. Successful login!");
                    isFound = true;
                    frame.setVisible(false);
                    MainFrame fr = new MainFrame(Database.instructors.get(i));
                    fr.setVisible(true);
                } else {
                    throw new IncorrectPasswordException();
                }}}
            }
            catch (IncorrectPasswordException e)
            {
                e.errorPopUp();
                loginTries++;
                if(loginTries==3) System.exit(-1);
            }
        }

        for(int i = 0; i<Database.admins.size();i++)
        {
            try {
                synchronized(Database.admins.get(i)) //makes sure that the admin is only being accessed by one user at a time
                 { 
            if (user.equals(Database.admins.get(i).email)) {
                if (pass.equals(Database.admins.get(i).password)) {
                    System.out.println(Database.admins.get(i).name + " is an admin. Successful login!");
                    isFound = true;
                    frame.setVisible(false);
                    MainFrame fr = new MainFrame(Database.admins.get(i));
                    fr.setVisible(true);
                } else {
                    throw new IncorrectPasswordException();
                    
                }
            }}}
            catch (IncorrectPasswordException e)
            {
                e.errorPopUp();
                loginTries++;
                if(loginTries==3) System.exit(-1);
            }
        }
        if(!isFound) {throw new UserNotFoundException();}
    }
        catch(UserNotFoundException e)
        {
            e.errorPopUp();
            loginTries++;
            if(loginTries==3) System.exit(-1);
        }

        
        
    }

    public static void addCourse_Std(Student s, Course c)
    {
        try {
            synchronized (s.courses) {
                    if(s.courses.size()>5)
                    {
                        throw new CourseRegistrationException("Cannot have more than 5 courses!");
                    }
            }
            synchronized (c.students) {
                    if (c.students.size() > 20)
                    {                
                        throw new CourseRegistrationException("Course is full! (more than 20 students)");
                    }
            }
            synchronized (s.courses) {
                    s.addCourse(c);
            }
            synchronized (c.students) {
                    c.addStudent(s);
            }
                    Grades g = new Grades(0);
            synchronized (s.grades) {
                    s.grades.add(g);
            }
            synchronized (c.grades) {
                    c.grades.add(g);
            }
        }
        catch(CourseRegistrationException e)
        {
            e.errorPopUp();
        }
    }
    public static void saveStdInfo(Student s)
    {
        try
        {
            PrintWriter os = new PrintWriter(new FileOutputStream("StudentInfo.txt"));
            synchronized (s) {
                os.println("Name: " + s.name);
                os.println("ID: " + s.ID);
                os.println("Major: " + s.get_major());
            }
            os.println("Semester: Spring 2022");
            os.println("Courses\tName\tNumber\tCredits\tGrade");
            
            synchronized (s) {
                for(int i = 0;i<s.courses.size();i++)
                {
                    os.print((i+1) + "\t" +  s.courses.get(i).getName() + "\t" + s.courses.get(i).getNumber() + "\t" + 
                    s.courses.get(i).getCredits() + "\t");
                    if(s.grades.size()!=0)
                        {
                        os.print(s.grades.get(i).getGrade()); 
                        }
                        os.println();
                }
                os.println("GPA: " + calcGPA(s));
            }
            os.close();
            JOptionPane.showMessageDialog(null, "Saved in StudentInfo.txt.", "Success",
            JOptionPane.INFORMATION_MESSAGE);
            }
        catch (FileNotFoundException ef)
        {
            JOptionPane.showMessageDialog(null, "Error creating the file!", "Error",
            JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    public static int calcGPA (Student s)
    {
        
        int sum = 0;
        int sumOfCredits = 0;
        synchronized (s) {
            if(s.grades.size()!=0)
            {
                for(int i = 0; i<s.get_num_courses();i++)
                {
                    sumOfCredits += s.courses.get(i).getCredits();
                    sum += (s.courses.get(i).getCredits() * s.get_grades(i).getGrade());
                }
            }
        }
      
        if(sumOfCredits ==0) return 0;
        return (sum / sumOfCredits);
    }

    public static void removeCourse(Student s, String name)
    {
        try {
        boolean isFound = false;
        synchronized (s) {
            for(int i = 0; i<s.courses.size();i++)
            {
                if(s.courses.get(i).getName().equals(name)) 
                {
                    Course crs = s.courses.get(i);
                    s.courses.remove(i);
                    if(s.grades.size()!=0) {
                    s.grades.remove(i); }

                    for(int j = 0; j<crs.students.size();j++)
                    {
                        if(crs.students.get(j).ID == s.ID)
                        {
                            crs.students.remove(j);
                            if(s.grades.size()!=0) {
                            crs.grades.remove(j);  }
                        }
                    }
                    isFound = true;

                }
            }
        }
        if(!isFound)
        {            
            throw new CourseRegistrationException("Course not found!");
        }}
        catch (CourseRegistrationException e)
        {
            e.errorPopUp();
        }
    }

    public static boolean isFound(Course c) {
        synchronized (Database.courses) {
            for (int i = 0; i < Database.courses.size(); i++) {
                if (c.getNumber().equals(Database.courses.get(i).getNumber())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isFound(Student s, Course c) {
        synchronized (s.courses) {
            for (int i = 0; i < s.courses.size(); i++) {
                if (c.getNumber().equals(s.courses.get(i).getNumber())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isFound(Instructor ins, Course c) {
        synchronized (ins.courses) {
            for (int i = 0; i < ins.courses.size(); i++) {
                if (c.getNumber().equals(ins.courses.get(i).getNumber())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void readCourseData(Student s, BufferedReader br) { 
        try {
            String line;
            while ((line = br.readLine()) != null) 
            {
                StringTokenizer st = new StringTokenizer(line, ";");
                String course_num = st.nextToken();
                String course_name = st.nextToken();
                int credits = Integer.parseInt(st.nextToken());


                
                Course c = new Course(course_name, course_num, credits);

                if(!(Controller.isFound(c))) //if course is not in database, add the course and add the student
                {
                    synchronized (Database.courses) {
                        Database.courses.add(c);
                    }
                    Controller.addCourse_Std(s, c);

                    System.out.print(course_num + " " + course_name + " " + credits + "\n");
                }
                else if(!(Controller.isFound(s,c))) //if the student is not already registered, register them
                {
                    Course original_course_obj = Controller.findCourse(c.getNumber());
                    Controller.addCourse_Std(s, original_course_obj);
                    System.out.print(course_num + " " + course_name + " " + credits + "\n");
                }
            }
            br.close();
        }
        catch (IOException er) 
                    {
                        JOptionPane.showMessageDialog(null, "Error reading the file!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
    }
    
    public static void changeINSName(Instructor ins, String newName)
    {
        synchronized (ins.name) {
            ins.change_name(newName);
        }
    }

    public static void addINSCourse(Instructor ins) {
        try {
            synchronized (ins.courses) {
                if (ins.courses.size() > 3) { 
                    throw new CourseRegistrationException("You can only add up to 3 courses");
                }
            }
            
            JPanel newPane = new JPanel();
            newPane.setLayout(new GridLayout(4, 2, 0, 0));
            
            JTextField inputArea = new JTextField("");
            inputArea.setVisible(true);
            JLabel label = new JLabel("Please enter the course code: ");
            label.setVisible(true);
            
            
            JTextField name_input = new JTextField("");
            name_input.setVisible(true);
            JLabel label2 = new JLabel("Please enter the name of the course:");
            label2.setVisible(true);


            JTextField credit_input = new JTextField("");
            credit_input.setVisible(true);

            JLabel label3 = new JLabel("Please enter the number of credits:");
            label3.setVisible(true);
            
            newPane.add(label);
            newPane.add(inputArea);
            newPane.add(label2);
            newPane.add(name_input);
            newPane.add(label3);
            newPane.add(credit_input);    
            
            JFrame frm = new JFrame("Add Course"); 
            frm.setBounds(100, 100, 614, 400); 
            frm.add(newPane); 
            frm.setVisible(true);
            
            
            
            JButton confirm = new JButton("Save");
            
            newPane.add(confirm);          


            confirm.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    String input = inputArea.getText();
                    synchronized (Database.courses) {
                        for (int i = 0; i < Database.courses.size(); i++) {
                            if (input.equals(Database.courses.get(i).getNumber())) {
                                ins.addCourse(Database.courses.get(i));
                                Database.courses.get(i).setInstructor(ins);
                                frm.dispose();
                                return;
                            }
                        }
                    }
                    
                    Course new_course = new Course(
                        name_input.getText(), 
                        input,
                        Integer.parseInt(credit_input.getText()));

                    new_course.setInstructor(ins);
                    synchronized (ins.courses) {
                        ins.addCourse(new_course);
                    }
                    synchronized (Database.courses) {
                        Database.courses.add(new_course);
                    }
                    System.out.println(new_course.toString());
                    frm.dispose();
                }
            });

        JButton reset = new JButton("Reset");
        
        newPane.add(reset);          

        reset.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                inputArea.setText("");
                name_input.setText("");
                credit_input.setText("");
            }
        });        
    }
    catch(CourseRegistrationException e)
    {
        e.errorPopUp();
    }   
    }

    

    public static void changePassword(Instructor ins) {
        try {
        String CurrentPass = JOptionPane.showInputDialog("Please enter your current password:");
        if(CurrentPass.equals(ins.password)) {
            String NewPass = JOptionPane.showInputDialog("Please enter your new password:");
            ins.change_pass(NewPass); 
        }
        else {
            throw new IncorrectPasswordException();
        }
    }
    catch(IncorrectPasswordException e)
    {
        e.errorPopUp();
        loginTries++;
        if(loginTries==3) System.exit(-1);
    }
    }

    public static Course findCourse(Instructor ins, String code) {
        synchronized (ins.courses) {
            for (int i = 0; i < ins.courses.size(); i++) {
                if (code.equals(ins.courses.get(i).getNumber())) {
                    return ins.courses.get(i);
                }
            }
        }
        return null;
    }
    
    public static Course findCourse(String code) {
        synchronized (Database.courses) {
            for (int i = 0; i < Database.courses.size(); i++) {
                if (code.equals(Database.courses.get(i).getNumber())) {
                    return Database.courses.get(i);
                }
            }
        }
        return null;
    }

    public static Student findStudent(Course c, int ID) {
        synchronized (c.students) {
            for (int i = 0; i < c.students.size(); i++) {
                if (ID == c.students.get(i).getID()) {
                    return c.students.get(i);
                }
            }
        }
        return null;
    }

    public static void saveCourseInfo(Instructor ins, Course c) {
        try
        {
            PrintWriter os = new PrintWriter(new FileOutputStream("CourseInfo(" + c.getNumber() +  ").txt"));
            synchronized (ins) {
                os.println("Name: " + ins.name);
                os.println("ID: " + ins.ID);
                os.println("Department: " + ins.department);
            }
            os.println("Term: Spring 2022");
            os.println("Course\t" + c.getNumber() +  "\t" + c.getName());
            synchronized (c.students) {
                if(c.students.size()!=0)
                {
                    for(int i = 0;i<c.students.size();i++)
                    {
                        os.println(c.students.get(i).ID + "\t" + c.students.get(i).name + "\t" + c.grades.get(i).getGrade());
                    }
                }
            }
            
            os.close();
            JOptionPane.showMessageDialog(null, "Saved in CourseInfo(" + c.getNumber() +  ").txt", "Success",
            JOptionPane.INFORMATION_MESSAGE);
        }
        catch (FileNotFoundException ef)
        {
            JOptionPane.showMessageDialog(null, "Error creating the file!", "Error",
            JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void editGrades(Course c, Student s) {
        try {
        int key = -1;
        synchronized (c.students) {
            for (int i = 0; i < c.students.size(); i++) {
                synchronized (s) {
                    if (s.ID == c.students.get(i).ID) {
                        key = i;
                        break;
                    }
                }
            }
        }
        if (key == -1) {
            throw new UserNotFoundException();
        }

        Grades key_grade;
        synchronized (c.grades) {
            key_grade = c.grades.get(key);
        }

        String formatting_string;
        synchronized (s) {
            formatting_string = s.name + " (" + s.ID +") :";
        }
        JLabel mainLbl = new JLabel("Please enter a new grade for " + formatting_string); 
        formatting_string = null;
        
        JTextField grade_input = new JTextField();
        synchronized (c.grades.get(key)) {
            grade_input.setText(String.valueOf(c.grades.get(key).getGrade()));
        }
                 
        JButton saveGrade = new JButton("Save Grade");
        JPanel pnl = new JPanel();
        pnl.add(mainLbl);
        pnl.add(grade_input);
        pnl.add(saveGrade);
        pnl.setVisible(true);

        JFrame grades_frame = new JFrame();
        grades_frame.add(pnl);

        saveGrade.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                int new_grade = Integer.parseInt(grade_input.getText());
                synchronized (key_grade) {
                    key_grade.setGrade(new_grade);
                }

                JOptionPane.showMessageDialog(null, "Changed Grade Successfully!", "Success",
            JOptionPane.INFORMATION_MESSAGE);

                
                grades_frame.dispose();
            }
        });
        //change grade
        grades_frame.setVisible(true);
        grades_frame.setBounds(100, 100, 614, 400);
        grades_frame.setTitle("Edit Grade");
        
    }
    catch(UserNotFoundException e)
    {
        e.errorPopUp();
    } 
    }

    public static Student findByID(int ID)
    {
        synchronized (Database.students) {
            for(int i = 0; i<Database.students.size();i++)
            {
                if(ID == Database.students.get(i).ID) return Database.students.get(i);
            }
        }
        return null;
    }
    public static Instructor findByName (String name)
    {
        synchronized (Database.instructors) {
            for(int i = 0; i<Database.instructors.size();i++)
            {
                if(name.toLowerCase().equals(Database.instructors.get(i).name.toLowerCase())) {
                    return Database.instructors.get(i);
                }
            }
        }
        return null;
    }
}