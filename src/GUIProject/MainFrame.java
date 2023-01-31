package GUIProject;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.StringTokenizer;

public class MainFrame extends JFrame 
{

    private JPanel contentPane;

    private LoginFrame relogFrame;

    /**
     * Launch the application.
     */
    // public static void main(String[] args) {
    // EventQueue.invokeLater(new Runnable() {
    // public void run() {
    // try {
    // MainFrame frame = new MainFrame();
    // frame.setVisible(true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    // });
    // }

    public MainFrame(Student s) 
    {
        

        MainFrame this_frame = this;
        setTitle("Student Banner");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 614, 400);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu File = new JMenu("File");
        menuBar.add(File);

        JMenuItem ShowData = new JMenuItem("Show Student Data");
        ShowData.addActionListener(new ActionListener() // ActionListener for Show Student Data in Menu
        {
            public void actionPerformed(ActionEvent e) 
            {
                String columnNames[] = { "Name", s.name, "ID", String.valueOf(s.ID), "Major", s.get_major() };
                String dataValues[][] = new String [3 + s.get_num_courses()][6];
                dataValues[0][0] = "Semester"; dataValues[0][1] = "Spring 2022";
                dataValues[1][0] = "Courses"; dataValues[1][1] = "Name"; dataValues[1][2] = "Number";
                dataValues[1][3] = "Credits"; dataValues[1][4] = "Grade";
                for(int i = 2;i<dataValues.length - 1;i++)
                {
                    dataValues[i][0] = String.valueOf((i-1));
                    dataValues[i][1] = s.courses.get(i-2).getName();
                    dataValues[i][2] = s.courses.get(i-2).getNumber();
                    dataValues[i][3] = String.valueOf(s.courses.get(i-2).getCredits());
                    if(s.grades.size()!=0)
                    {
                        dataValues[i][4] = String.valueOf(s.get_grades(i-2).getGrade());
                    }
                }
                dataValues[dataValues.length-1][3] = "GPA";
                dataValues[dataValues.length-1][4] = String.valueOf(Controller.calcGPA(s));
                JTable view_results = new JTable(dataValues, columnNames);
				JScrollPane scrollPane = new JScrollPane(view_results);
				JPanel resultsPane = new JPanel();
                resultsPane.add(scrollPane);
                JFrame resultsFrame = new JFrame("Information");
				resultsFrame.getContentPane().add(resultsPane);
				resultsFrame.setVisible(true);
				resultsFrame.setBounds(100, 100, 691, 523);


            }
        });
        File.add(ShowData);

        JMenuItem SaveData = new JMenuItem("Save Student Data");
        SaveData.addActionListener(new ActionListener() // ActionListener for Save Student Data in Menu
        {
            public void actionPerformed(ActionEvent e) 
            {
                Controller.saveStdInfo(s);
            }
        });
        File.add(SaveData);

        JMenuItem AddOtherUser = new JMenuItem("Add other user");
        AddOtherUser.addActionListener(new ActionListener() // ActionListener for Quit in Menu
        {
            public void actionPerformed(ActionEvent e)  {
                
                GUIThread t = new GUIThread();
                

            }
        });
        File.add(AddOtherUser);

        JMenuItem LogOut = new JMenuItem("Log out");
        LogOut.addActionListener(new ActionListener() // ActionListener for Quit in Menu
        {
            public void actionPerformed(ActionEvent e)  {
                
                GUIThread t = new GUIThread();
                this_frame.dispose();

            }
        });
        File.add(LogOut);


        

        JMenuItem Quit = new JMenuItem("Quit");
        Quit.addActionListener(new ActionListener() // ActionListener for Quit in Menu
        {
            public void actionPerformed(ActionEvent e)  {System.exit(0);}
        });
        File.add(Quit);

        JMenu Registrar = new JMenu("Registrar");
        menuBar.add(Registrar);

        JMenuItem AddCourse = new JMenuItem("Add Course");
        AddCourse.addActionListener(new ActionListener() // ActionListener for Add Course in Menu
        {
            public void actionPerformed(ActionEvent e) 
            {
                JFileChooser j = new JFileChooser("");
                int r = j.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) 
                {
                    File f = new File(j.getSelectedFile().getAbsolutePath());
                    try 
                    {
                        FileReader fr = new FileReader(f);
                        BufferedReader br = new BufferedReader(fr);

                        Controller.readCourseData(s, br);
                    }catch (FileNotFoundException error) 
                    {
                        JOptionPane.showMessageDialog(null, "Error, cannot find the file!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
        Registrar.add(AddCourse);

        JMenuItem DropCourse = new JMenuItem("Drop Course"); // ActionListener for Drop Course in Menu
        DropCourse.addActionListener(new ActionListener() //TODO(presentation)[remove unused / commented lines of code]
        {
            public void actionPerformed(ActionEvent e) 
            {
                String name_of_crs = JOptionPane.showInputDialog("Enter course's name:");
                // String number_of_crs = JOptionPane.showInputDialog("Enter course's name:");

                Controller.removeCourse(s, name_of_crs);
                
                //Search in database for the Student's course by comparing the name 
                //delete this course
            }
        });
        Registrar.add(DropCourse);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        JLabel MainLabel = new JLabel("Welcome " + s.name + "! Please choose from the following options:");
        MainLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(MainLabel, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel panel_1 = new JPanel();
        panel.add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));
        JButton btnAddCourse = new JButton("Add Course          ");
        btnAddCourse.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                JFileChooser j = new JFileChooser("");
                int r = j.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) 
                {
                    File f = new File(j.getSelectedFile().getAbsolutePath());
                    try 
                    {
                        FileReader fr = new FileReader(f);
                        BufferedReader br = new BufferedReader(fr);

                        Controller.readCourseData(s, br);
                    }catch (FileNotFoundException error) 
                    {
                        JOptionPane.showMessageDialog(null, "Error, cannot find the file!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (IOException er) 
                    {
                        JOptionPane.showMessageDialog(null, "Error reading the file!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnAddCourse.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel_1.add(btnAddCourse, BorderLayout.WEST);

        JLabel AddCourseDesc = new JLabel("This will allow you to add a course (up to 5).");
        AddCourseDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel_1.add(AddCourseDesc, BorderLayout.CENTER);

        JPanel panel_2 = new JPanel();
        panel.add(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        JButton btnDropCourse = new JButton("Drop Course         "); // ActionListener for Drop Course Button
        btnDropCourse.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                String name_of_crs = JOptionPane.showInputDialog("Enter course's name:");
                // String number_of_crs = JOptionPane.showInputDialog("Enter course's name:");

                Controller.removeCourse(s, name_of_crs);
                
                //Search in database for the Student's course by comparing the name 
                //delete this course
            }
        });
        btnDropCourse.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel_2.add(btnDropCourse, BorderLayout.WEST);

        JLabel DropCourseDesc = new JLabel("This will allow you to drop a course.");
        DropCourseDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel_2.add(DropCourseDesc);

        JPanel panel_3 = new JPanel();
        panel.add(panel_3);
        panel_3.setLayout(new BorderLayout(0, 0));

        JButton btnShowStudentInfo = new JButton("Show Student Info");
        btnShowStudentInfo.addActionListener(new ActionListener() // ActionListener for Show Student Info Button
        {
            public void actionPerformed(ActionEvent e) 
            {
                String columnNames[] = { "Name", s.name, "ID", String.valueOf(s.ID), "Major", s.get_major() };
                String dataValues[][] = new String [3 + s.get_num_courses()][6];
                dataValues[0][0] = "Semester"; dataValues[0][1] = "Spring 2022";
                dataValues[1][0] = "Courses"; dataValues[1][1] = "Name"; dataValues[1][2] = "Number";
                dataValues[1][3] = "Credits"; dataValues[1][4] = "Grade";
                for(int i = 2;i<dataValues.length - 1;i++)
                {
                    dataValues[i][0] = String.valueOf((i-1));
                    dataValues[i][1] = s.courses.get(i-2).getName();
                    dataValues[i][2] = s.courses.get(i-2).getNumber();
                    dataValues[i][3] = String.valueOf(s.courses.get(i-2).getCredits());
                    if(s.grades.size()!=0)
                    {
                        dataValues[i][4] = String.valueOf(s.get_grades(i-2).getGrade());
                    }
                }
                dataValues[dataValues.length-1][3] = "GPA";
                dataValues[dataValues.length-1][4] = String.valueOf(Controller.calcGPA(s));
                JTable view_results = new JTable(dataValues, columnNames);
                view_results.setRowHeight(50);
                TableColumn column = null;
                for(int i = 0;i<columnNames.length;i++)
                {
                    column = view_results.getColumnModel().getColumn(i);
                    column.setPreferredWidth(500);
                }
				JScrollPane scrollPane = new JScrollPane(view_results);
				JPanel resultsPane = new JPanel();
                resultsPane.add(scrollPane);
                JFrame resultsFrame = new JFrame("Information");
				resultsFrame.getContentPane().add(resultsPane);
				resultsFrame.setVisible(true);
				resultsFrame.setBounds(100, 100, 691, 523);
            }
        });
        
        btnShowStudentInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel_3.add(btnShowStudentInfo, BorderLayout.WEST);
        JLabel ShowInfoDesc = new JLabel("This will allow you to see your information.");
        ShowInfoDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel_3.add(ShowInfoDesc);
        JPanel panel_4 = new JPanel();
        panel.add(panel_4);
        panel_4.setLayout(new BorderLayout(0, 0));
        JButton btnSaveStudentInfo = new JButton("Save Student Info ");
        btnSaveStudentInfo.addActionListener(new ActionListener() // ActionListener for Save Student Info Button
        {
            public void actionPerformed(ActionEvent e) 
            {
                Controller.saveStdInfo(s);
            }
        });

        btnSaveStudentInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel_4.add(btnSaveStudentInfo, BorderLayout.WEST);
        JLabel SaveInfoDesc = new JLabel("This will allow you to save your information in a file.");
        SaveInfoDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel_4.add(SaveInfoDesc);
    }

    public MainFrame (Instructor ins)
    {
        MainFrame this_frame = this;
        setTitle("Instructor Banner");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 616, 403);
        JLabel MainLabel_INS = new JLabel("Welcome " + ins.name + "! Please choose from the following options:");
		MainLabel_INS.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu File_INS = new JMenu("File");
		menuBar.add(File_INS);
		
		JMenuItem Menu_prsInfo = new JMenuItem("View Personal Info");
		Menu_prsInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String columnNames[] = { "Name", ins.name, "ID", String.valueOf(ins.ID), "Department", ins.department };
                String dataValues[][] = new String [3 + ins.get_num_courses()][6];
                dataValues[0][0] = "Term"; dataValues[0][1] = "Spring 2022";
                dataValues[1][0] = "Courses"; 
                dataValues[2][0] = "Name"; dataValues[2][1] = "Number";
                for(int i = 3; i<dataValues.length; i++)
                {
                    dataValues[i][0] = ins.courses.get(i-3).getName();
                    dataValues[i][1] = ins.courses.get(i-3).getNumber();
                }
                
                JTable view_results = new JTable(dataValues, columnNames);
                view_results.setRowHeight(50);
                TableColumn column = null;
                for(int i = 0;i<columnNames.length;i++)
                {
                    column = view_results.getColumnModel().getColumn(i);
                    column.setPreferredWidth(500);
                }
				JScrollPane scrollPane = new JScrollPane(view_results);
				JPanel resultsPane = new JPanel();
                resultsPane.add(scrollPane);
                JFrame resultsFrame = new JFrame("Information");
				resultsFrame.getContentPane().add(resultsPane);
				resultsFrame.setVisible(true);
				resultsFrame.setBounds(100, 100, 691, 523);
			}
		});
		File_INS.add(Menu_prsInfo);
		
		JMenuItem Menu_ViewCrsInfo = new JMenuItem("View Courses Info");
		Menu_ViewCrsInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                String courseChoice = JOptionPane.showInputDialog("Enter the course code to view its information:");
                
                Course c = Controller.findCourse(ins, courseChoice);

                if (c == null) {
                    
                    throw new CourseRegistrationException("Course not found!");
                }
                
                String columnNames[] = { "Name", ins.name, "ID", String.valueOf(ins.ID), "Department", ins.department };
                String dataValues[][] = new String [4 + c.students.size()][6];
                dataValues[0][0] = "Term"; dataValues[0][1] = "Spring 2022";
                dataValues[1][0] = "Course"; dataValues[1][1] = c.getNumber(); dataValues[1][2] = c.getName();
                dataValues[2][0] = "ID"; dataValues[2][1] = "Name"; dataValues[2][2] = "Grade";
                if(c.students.size()!=0)
                {
                    for(int i = 3; i<dataValues.length; i++)
                    {
                        dataValues[i][0] = String.valueOf(c.students.get(i-3).getID());
                        dataValues[i][1] = c.students.get(i-3).getName();
                        dataValues[i][2] = String.valueOf(c.grades.get(i-3).getGrade()); 
                    }
                }
                JTable view_results = new JTable(dataValues, columnNames);
                view_results.setRowHeight(50);
                TableColumn column = null;
                for(int i = 0;i<columnNames.length;i++)
                {
                    column = view_results.getColumnModel().getColumn(i);
                    column.setPreferredWidth(500);
                }
				JScrollPane scrollPane = new JScrollPane(view_results);
				JPanel resultsPane = new JPanel();
                resultsPane.add(scrollPane);
                
                JButton saveInfo = new JButton("Save Information");
                
                
                saveInfo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       Controller.saveCourseInfo(ins, c); 
                    }
                });

                resultsPane.add(saveInfo);
                
                //make button
                JFrame resultsFrame = new JFrame("Information");
				resultsFrame.getContentPane().add(resultsPane);
                
                


				resultsFrame.setVisible(true);
				resultsFrame.setBounds(100, 100, 691, 523);
			}
            catch (CourseRegistrationException err)
            {
                err.errorPopUp();
            }
		}});
		File_INS.add(Menu_ViewCrsInfo);

        JMenuItem AddOtherUser = new JMenuItem("Add other user");
        AddOtherUser.addActionListener(new ActionListener() // ActionListener for Quit in Menu
        {
            public void actionPerformed(ActionEvent e)  {
                
                GUIThread t = new GUIThread();
                

            }
        });
        File_INS.add(AddOtherUser);

        JMenuItem LogOut = new JMenuItem("Log out");
        LogOut.addActionListener(new ActionListener() // ActionListener for Quit in Menu
        {
            public void actionPerformed(ActionEvent e)  {
                
                GUIThread t = new GUIThread();
                this_frame.dispose();

            }
        });
        File_INS.add(LogOut);
		
		JMenuItem Menu_Quit_INS = new JMenuItem("Quit");
		Menu_Quit_INS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		File_INS.add(Menu_Quit_INS);
		
		JMenu CrsSettings_INS = new JMenu("Course Settings");
		menuBar.add(CrsSettings_INS);

        JMenuItem AddCourse = new JMenuItem("Add Course");
        AddCourse.addActionListener(new ActionListener() // ActionListener for Add Course in Menu
        {
            public void actionPerformed(ActionEvent e) 
            {
                Controller.addINSCourse(ins);
            }
        });
        CrsSettings_INS.add(AddCourse);
		
		JMenuItem Menu_EditGrades = new JMenuItem("Edit Grades");
		Menu_EditGrades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {

                JTextField course_option = new JTextField();
                JTextField student_option = new JTextField();
                /*JPanel new_panel = new JPanel();

                new_panel.setPreferredSize(new Dimension(500, 200));

                new_panel.add(new JLabel("Enter course ID: "));
                new_panel.add(course_option);
                
                new_panel.add(new JLabel("Enter student ID:"));
                new_panel.add(student_option);*/
                Object[] info = {"Enter course ID: " , course_option, "Enter student ID:", student_option};
                int selected_option = JOptionPane.showConfirmDialog(null, info, "Please enter a Course ID and Student ID", JOptionPane.OK_CANCEL_OPTION);
                
                if (selected_option == JOptionPane.OK_OPTION) {
                    Course c = Controller.findCourse(ins, course_option.getText());
                    Student s = Controller.findStudent(c, Integer.parseInt(student_option.getText()));
                    if(c==null)
                    {
                        
                        throw new CourseRegistrationException("Cannot find the course!");
                    }
                    if(s==null)
                    {
                        
                        throw new UserNotFoundException();
                    }
                    
                    Controller.editGrades(c, s);
                }
            }
    
        catch (CourseRegistrationException e2)
        {
            e2.errorPopUp();
        }
        catch (UserNotFoundException err)
        {
            err.errorPopUp();
        }
        }}
		);
		CrsSettings_INS.add(Menu_EditGrades);
		
		JMenu PersSettings_INS = new JMenu("Personal Settings");
		menuBar.add(PersSettings_INS);
		
		JMenuItem Menu_ChangeName = new JMenuItem("Change Name");
		Menu_ChangeName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String newName = JOptionPane.showInputDialog("Your current name is " + ins.name
                 + ". What would you like your name to be changed to?");
                Controller.changeINSName(ins, newName);
                MainLabel_INS.setText("Welcome " + ins.name + "! Please choose from the following options:");
				
			}
		});
		PersSettings_INS.add(Menu_ChangeName);
		
		JMenuItem Menu_ChangePass = new JMenuItem("Change Password");
		Menu_ChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO(functional)[put also a JOptionPane]
                Controller.changePassword(ins);
			}
		});
		PersSettings_INS.add(Menu_ChangePass);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		contentPane.add(MainLabel_INS, BorderLayout.NORTH);
		
		JPanel MainPanel_INS = new JPanel();
		contentPane.add(MainPanel_INS, BorderLayout.CENTER);
		MainPanel_INS.setLayout(new BoxLayout(MainPanel_INS, BoxLayout.Y_AXIS));
		
		JPanel panel_1_INS = new JPanel();
		MainPanel_INS.add(panel_1_INS);
		panel_1_INS.setLayout(new BorderLayout(0, 0));
		
		JButton btnViewPersInfo = new JButton("View Personal Info");
		btnViewPersInfo.addActionListener(new ActionListener() { // TODO(functional)[complete this function]
			public void actionPerformed(ActionEvent e) {
                String columnNames[] = { "Name", ins.name, "ID", String.valueOf(ins.ID), "Department", ins.department };
                String dataValues[][] = new String [3 + ins.get_num_courses()][6];
                dataValues[0][0] = "Term"; dataValues[0][1] = "Spring 2022";
                dataValues[1][0] = "Courses"; 
                dataValues[2][0] = "Name"; dataValues[2][1] = "Number";
                for(int i = 3; i<dataValues.length; i++)
                {
                    dataValues[i][0] = ins.courses.get(i-3).getName();
                    dataValues[i][1] = ins.courses.get(i-3).getNumber();
                }
                
                JTable view_results = new JTable(dataValues, columnNames);
                view_results.setRowHeight(50);
                TableColumn column = null;
                for(int i = 0;i<columnNames.length;i++)
                {
                    column = view_results.getColumnModel().getColumn(i);
                    column.setPreferredWidth(500);
                }
				JScrollPane scrollPane = new JScrollPane(view_results);
				JPanel resultsPane = new JPanel();
                resultsPane.add(scrollPane);
                JFrame resultsFrame = new JFrame("Information");
				resultsFrame.getContentPane().add(resultsPane);
				resultsFrame.setVisible(true);
				resultsFrame.setBounds(100, 100, 691, 523);
			}
		});
		
		btnViewPersInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_1_INS.add(btnViewPersInfo, BorderLayout.WEST);
		
		JLabel ViewPersInfoDesc = new JLabel("This will allow you to view your personal information.");
		ViewPersInfoDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_1_INS.add(ViewPersInfoDesc, BorderLayout.CENTER);
		
		JPanel panel_2_INS = new JPanel();
		MainPanel_INS.add(panel_2_INS);
		panel_2_INS.setLayout(new BorderLayout(0, 0));
		
		JButton btnViewCourses_INS = new JButton("View Courses Info ");
		btnViewCourses_INS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                String courseChoice = JOptionPane.showInputDialog("Enter the course code to view its information:");
                
                Course c = Controller.findCourse(ins, courseChoice);

                if (c == null) {
                    throw new CourseRegistrationException("Course not found!");
                }
                
                String columnNames[] = { "Name", ins.name, "ID", String.valueOf(ins.ID), "Department", ins.department };
                String dataValues[][] = new String [3 + c.students.size()][6];
                dataValues[0][0] = "Term"; dataValues[0][1] = "Spring 2022";
                dataValues[1][0] = "Course"; dataValues[1][1] = c.getNumber(); dataValues[1][2] = c.getName();
                dataValues[2][0] = "ID"; dataValues[2][1] = "Name"; dataValues[2][2] = "Grade";
                if(c.students.size()!=0)
                {
                    for(int i = 3; i<dataValues.length; i++)
                    {
                        dataValues[i][0] = String.valueOf(c.students.get(i-3).getID());
                        dataValues[i][1] = c.students.get(i-3).getName();
                        dataValues[i][2] = String.valueOf(c.grades.get(i-3).getGrade()); 
                    }
                }
                JTable view_results = new JTable(dataValues, columnNames);
                view_results.setRowHeight(50);
                TableColumn column = null;
                for(int i = 0;i<columnNames.length;i++)
                {
                    column = view_results.getColumnModel().getColumn(i);
                    column.setPreferredWidth(500);
                }
				JScrollPane scrollPane = new JScrollPane(view_results);
				JPanel resultsPane = new JPanel();
                resultsPane.add(scrollPane);
                
                JButton saveInfo = new JButton("Save Information");
                
                
                saveInfo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       Controller.saveCourseInfo(ins, c); 
                    }
                });

                resultsPane.add(saveInfo);
                
                //make button
                JFrame resultsFrame = new JFrame("Information");
				resultsFrame.getContentPane().add(resultsPane);
                
                


				resultsFrame.setVisible(true);
				resultsFrame.setBounds(100, 100, 691, 523);
            }
            catch(CourseRegistrationException err)
            {
                err.errorPopUp();
            }
        }
		});
		
		btnViewCourses_INS.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2_INS.add(btnViewCourses_INS, BorderLayout.WEST);
		
		JLabel ViewCoursesDesc = new JLabel("This will display information about the courses you teach.");
		ViewCoursesDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2_INS.add(ViewCoursesDesc);

        JPanel panel_6_INS = new JPanel();
		MainPanel_INS.add(panel_6_INS);
		panel_6_INS.setLayout(new BorderLayout(0, 0));
		
		JButton btnAddCourses_INS = new JButton("Add Courses         ");
		btnAddCourses_INS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                Controller.addINSCourse(ins);
			}
		});
		
		btnAddCourses_INS.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_INS.add(btnAddCourses_INS, BorderLayout.WEST);

        JLabel AddCoursesDesc = new JLabel("This will add a course you teach.");
		AddCoursesDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_INS.add(AddCoursesDesc);
		
		JPanel panel_3_INS = new JPanel();
		MainPanel_INS.add(panel_3_INS);
		panel_3_INS.setLayout(new BorderLayout(0, 0));
		
		JButton btnEditGrades = new JButton("Edit Grades           ");
		btnEditGrades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {

                JTextField course_option = new JTextField();
                JTextField student_option = new JTextField();
                /*JPanel new_panel = new JPanel();

                new_panel.setPreferredSize(new Dimension(500, 200));

                new_panel.add(new JLabel("Enter course ID: "));
                new_panel.add(course_option);
                
                new_panel.add(new JLabel("Enter student ID:"));
                new_panel.add(student_option);*/
                Object[] info = {"Enter course ID: " , course_option, "Enter student ID:", student_option};
                int selected_option = JOptionPane.showConfirmDialog(null, info, "Please enter a Course ID and Student ID", JOptionPane.OK_CANCEL_OPTION);
                
                if (selected_option == JOptionPane.OK_OPTION) {
                    Course c = Controller.findCourse(ins, course_option.getText());
                    Student s = Controller.findStudent(c, Integer.parseInt(student_option.getText()));
                    if(c==null)
                    {
                        
                        throw new CourseRegistrationException("Cannot find the course!");
                    }
                    if(s==null)
                    {
                        
                        throw new UserNotFoundException();
                    }
                    
                    Controller.editGrades(c, s);
                }
            }
    
        catch (CourseRegistrationException e2)
        {
            e2.errorPopUp();
        }
        catch (UserNotFoundException err)
        {
            err.errorPopUp();
        }
        }}
		);
		
		btnEditGrades.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_3_INS.add(btnEditGrades, BorderLayout.WEST);
		
		JLabel EditGradesDesc = new JLabel("This will allow you to edit students' grades.");
		EditGradesDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_3_INS.add(EditGradesDesc);
		
		JPanel panel_4_INS = new JPanel();
		MainPanel_INS.add(panel_4_INS);
		panel_4_INS.setLayout(new BorderLayout(0, 0));
		
		JButton btnChangeName = new JButton("Change Name      ");
		btnChangeName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String newName = JOptionPane.showInputDialog("Your current name is " + ins.name
                 + ". What would you like your name to be changed to?");
                Controller.changeINSName(ins, newName);
                MainLabel_INS.setText("Welcome " + ins.name + "! Please choose from the following options:");

			}
		});
		
		btnChangeName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_4_INS.add(btnChangeName, BorderLayout.WEST);
		
		JLabel ChangeNameDesc = new JLabel("This will allow you to change your name.");
		ChangeNameDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_4_INS.add(ChangeNameDesc);
		
		JPanel panel_5_INS = new JPanel();
		MainPanel_INS.add(panel_5_INS);
		panel_5_INS.setLayout(new BorderLayout(0, 0));
		
		JButton btnChangePassword = new JButton("Change Password ");
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                Controller.changePassword(ins);

			}
		});
		btnChangePassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_5_INS.add(btnChangePassword, BorderLayout.WEST);
		
		JLabel ChangePassDesc = new JLabel("This will allow you to change your password.");
		ChangePassDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_5_INS.add(ChangePassDesc, BorderLayout.CENTER);
            
}   
    
        
      public MainFrame(Admin admin)
      {
          MainFrame this_frame = this;
        
                setTitle("Admin Banner");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(100, 100, 616, 402);
                
                JMenuBar menuBar = new JMenuBar();
                setJMenuBar(menuBar);
                
                JMenu File = new JMenu("File");
                menuBar.add(File);

                JMenuItem AddOtherUser = new JMenuItem("Add other user");
                AddOtherUser.addActionListener(new ActionListener() // ActionListener for Quit in Menu
                {
                    public void actionPerformed(ActionEvent e)  {
                        
                        GUIThread t = new GUIThread();
                        

                    }
                });
                File.add(AddOtherUser);

                JMenuItem LogOut = new JMenuItem("Log out");
                LogOut.addActionListener(new ActionListener() // ActionListener for Quit in Menu
                {
                    public void actionPerformed(ActionEvent e)  {
                        
                        GUIThread t = new GUIThread();
                        this_frame.dispose();

                    }
                });
                File.add(LogOut);
                
                JMenuItem Quit = new JMenuItem("Quit");
                Quit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
                File.add(Quit);
                
                JMenu StudentSettings = new JMenu("Student Settings");
                menuBar.add(StudentSettings);
                
                JMenuItem AddCourse = new JMenuItem("Add Course");
                AddCourse.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                        int ID = Integer.parseInt(JOptionPane.showInputDialog("Enter the student's ID:"));
                        Student std = Controller.findByID(ID);
                        if(std ==null)
                        {
                            throw new UserNotFoundException();
                        }


                        JFileChooser j = new JFileChooser("");
                        int r = j.showOpenDialog(null);
                        if (r == JFileChooser.APPROVE_OPTION) 
                        {
                            File f = new File(j.getSelectedFile().getAbsolutePath());
                            try 
                            {
                                FileReader fr = new FileReader(f);
                                BufferedReader br = new BufferedReader(fr);

                                Controller.readCourseData(std, br);
                            }catch (FileNotFoundException error) 
                            {
                                JOptionPane.showMessageDialog(null, "Error, cannot find the file!", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    }
                    catch (UserNotFoundException err)
                    {
                        err.errorPopUp();
                    }
                    }
                });
                StudentSettings.add(AddCourse);
                
                JMenuItem DropCourse = new JMenuItem("Drop Course");
                DropCourse.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            {
                                JTextField id_entry = new JTextField();
                                JTextField name_entry = new JTextField();
    
                                Object[] info = {"Enter student ID: " , id_entry, "Enter course name:", name_entry};
                                int selected_option = JOptionPane.showConfirmDialog(null, info, "Please enter a student ID and Course Name", JOptionPane.OK_CANCEL_OPTION);
                    
                                if (selected_option == JOptionPane.OK_OPTION) {
                                    Student s = Controller.findByID(Integer.parseInt(id_entry.getText()));
                                    if(s == null) {throw new UserNotFoundException();}
    
                                    Controller.removeCourse(s, name_entry.getText());
                                }
                            }
                        } catch (UserNotFoundException err)
                        {
                            err.errorPopUp();
                        }
                    }
                });
                StudentSettings.add(DropCourse);
                
                JMenuItem ShowSTDData = new JMenuItem("Show Student Data");
                ShowSTDData.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                        String id = JOptionPane.showInputDialog("Enter student's ID: ");
                        Student s = Controller.findByID(Integer.parseInt(id));
                        if(s==null)
                        {
                            throw new UserNotFoundException();
                        }
                        
                        
                        String columnNames[] = { "Name", s.name, "ID", String.valueOf(s.ID), "Major", s.get_major() };
                        String dataValues[][] = new String [3 + s.get_num_courses()][6];
                        dataValues[0][0] = "Semester"; dataValues[0][1] = "Spring 2022";
                        dataValues[1][0] = "Courses"; dataValues[1][1] = "Name"; dataValues[1][2] = "Number";
                        dataValues[1][3] = "Credits"; dataValues[1][4] = "Grade";
                        for(int i = 2;i<dataValues.length - 1;i++)
                        {
                            dataValues[i][0] = String.valueOf((i-1));
                            dataValues[i][1] = s.courses.get(i-2).getName();
                            dataValues[i][2] = s.courses.get(i-2).getNumber();
                            dataValues[i][3] = String.valueOf(s.courses.get(i-2).getCredits());
                            if(s.grades.size()!=0)
                            {
                                dataValues[i][4] = String.valueOf(s.get_grades(i-2).getGrade());
                            }
                        }
                        dataValues[dataValues.length-1][3] = "GPA";
                        dataValues[dataValues.length-1][4] = String.valueOf(Controller.calcGPA(s));
                        JTable view_results = new JTable(dataValues, columnNames);
                        view_results.setRowHeight(50);
                        TableColumn column = null;
                        for(int i = 0;i<columnNames.length;i++)
                        {
                            column = view_results.getColumnModel().getColumn(i);
                            column.setPreferredWidth(500);
                        }
                        JScrollPane scrollPane = new JScrollPane(view_results);
                        JPanel resultsPane = new JPanel();
                        resultsPane.add(scrollPane);
                        JFrame resultsFrame = new JFrame("Information");
                        resultsFrame.getContentPane().add(resultsPane);
                        resultsFrame.setVisible(true);
                        resultsFrame.setBounds(100, 100, 691, 523);
                    }
                    catch (UserNotFoundException err)
                    {
                        err.errorPopUp();
                    }
                    }}
                );
                StudentSettings.add(ShowSTDData);
                
                JMenu INS_Settings = new JMenu("Instructor Settings");
                menuBar.add(INS_Settings);
                
                JMenuItem ViewINSInfo = new JMenuItem("View INS Info");
                ViewINSInfo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                        String name = JOptionPane.showInputDialog("Enter instructor's full name:");
                        
                        Instructor ins = Controller.findByName(name);
                        if (ins == null) {
                            throw new UserNotFoundException();
                        }
                        

                        String columnNames[] = { "Name", ins.name, "ID", String.valueOf(ins.ID), "Department", ins.department };
                        String dataValues[][] = new String [3 + ins.get_num_courses()][6];
                        dataValues[0][0] = "Term"; dataValues[0][1] = "Spring 2022";
                        dataValues[1][0] = "Courses"; 
                        dataValues[2][0] = "Name"; dataValues[2][1] = "Number";
                        for(int i = 3; i<dataValues.length; i++)
                        {
                            dataValues[i][0] = ins.courses.get(i-3).getName();
                            dataValues[i][1] = ins.courses.get(i-3).getNumber();
                        }
                        
                        JTable view_results = new JTable(dataValues, columnNames);
                        view_results.setRowHeight(50);
                        TableColumn column = null;
                        for(int i = 0;i<columnNames.length;i++)
                        {
                            column = view_results.getColumnModel().getColumn(i);
                            column.setPreferredWidth(500);
                        }
                        JScrollPane scrollPane = new JScrollPane(view_results);
                        JPanel resultsPane = new JPanel();
                        resultsPane.add(scrollPane);
                        JFrame resultsFrame = new JFrame("Information");
                        resultsFrame.getContentPane().add(resultsPane);
                        resultsFrame.setVisible(true);
                        resultsFrame.setBounds(100, 100, 691, 523);
                    }
                
                catch (UserNotFoundException err)
                {
                    err.errorPopUp();
                }
                }});
                INS_Settings.add(ViewINSInfo);
                
                JMenuItem ViewCrsInfo = new JMenuItem("View Course Info");
                ViewCrsInfo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                        String courseChoice = JOptionPane.showInputDialog("Enter the course code to view its information:");
                
                        Course c = Controller.findCourse(courseChoice);
                        
                        if (c == null) {
                            
                            throw new CourseRegistrationException("Course not found!");
                        }
                        
                        String columnNames[] = { "Name", c.getInstructor().name, 
                                                "ID", String.valueOf(c.getInstructor().ID), 
                                                "Department", c.getInstructor().department };
                        String dataValues[][] = new String [3 + c.students.size()][6];
                        dataValues[0][0] = "Term"; dataValues[0][1] = "Spring 2022";
                        dataValues[1][0] = "Course"; dataValues[1][1] = c.getNumber(); dataValues[1][2] = c.getName();
                        dataValues[2][0] = "ID"; dataValues[2][1] = "Name"; dataValues[2][2] = "Grade";
                        if(c.students.size()!=0)
                        {
                            for(int i = 3; i<dataValues.length; i++)
                            {
                                dataValues[i][0] = String.valueOf(c.students.get(i-3).getID());
                                dataValues[i][1] = c.students.get(i-3).getName();
                                dataValues[i][2] = String.valueOf(c.grades.get(i-3).getGrade()); 
                            }
                        }
                        JTable view_results = new JTable(dataValues, columnNames);
                        view_results.setRowHeight(50);
                        TableColumn column = null;
                        for(int i = 0;i<columnNames.length;i++)
                        {
                            column = view_results.getColumnModel().getColumn(i);
                            column.setPreferredWidth(500);
                        }
                        JScrollPane scrollPane = new JScrollPane(view_results);
                        JPanel resultsPane = new JPanel();
                        resultsPane.add(scrollPane);
                        
                        JButton saveInfo = new JButton("Save Information");
                        
                        
                        saveInfo.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                            Controller.saveCourseInfo(c.getInstructor(), c); 
                            }
                        });

                        resultsPane.add(saveInfo);
                        
                        //make button
                        JFrame resultsFrame = new JFrame("Information");
                        resultsFrame.getContentPane().add(resultsPane);

                        resultsFrame.setVisible(true);
                        resultsFrame.setBounds(100, 100, 691, 523);

                } catch (CourseRegistrationException e2)
                {
                    e2.errorPopUp();
                }
            }});
                INS_Settings.add(ViewCrsInfo);
                
                JMenuItem ChangeName_INS = new JMenuItem("Change INS Name");
                ChangeName_INS.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = JOptionPane.showInputDialog("Enter the instructor's full name:");
                        Instructor ins = Controller.findByName(name);
                 
                        String newName = JOptionPane.showInputDialog("Their current name is " + ins.name
                    + ". What would you like their name to be changed to?");
                        Controller.changeINSName(ins, newName);
                    }
                });
                //INS_Settings.add(ChangeName_INS);

                JMenuItem AddCourse_INS = new JMenuItem("Add Course");
                AddCourse_INS.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                        String name = JOptionPane.showInputDialog("Enter the instructor's full name:");
                        
                        Instructor ins = Controller.findByName(name);

                        if (ins == null) {
                            throw new UserNotFoundException();
                        }
                        
                        Controller.addINSCourse(ins);
                    } catch (UserNotFoundException e2)
                    {
                        e2.errorPopUp();
                    }
                }});
                INS_Settings.add(AddCourse_INS);
                INS_Settings.add(ChangeName_INS);
                
                JMenuItem ChangePass_INS = new JMenuItem("Change INS Password");
                ChangePass_INS.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                        String name = JOptionPane.showInputDialog("Enter the instructor's full name:");
                        Instructor ins = Controller.findByName(name);
                        if(ins == null)
                        {
                            throw new UserNotFoundException();
                        }
                        String pass = JOptionPane.showInputDialog("Enter the instructor's new password:");
                        ins.password = pass;
                    } catch (UserNotFoundException e2)
                    {
                        e2.errorPopUp();
                    }
                }});
                INS_Settings.add(ChangePass_INS);
                
                contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                contentPane.setLayout(new BorderLayout(0, 0));
                setContentPane(contentPane);
                
                JLabel MainLabel = new JLabel("Welcome " + admin.name + "! Please choose from the following student options:");
                MainLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
                contentPane.add(MainLabel, BorderLayout.NORTH);
                
                JPanel panel = new JPanel();
                contentPane.add(panel, BorderLayout.CENTER);
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                
                JPanel panel_1 = new JPanel();
                panel.add(panel_1);
                panel_1.setLayout(new BorderLayout(0, 0));
                
                JButton btnAddCourse = new JButton("Add Course          ");
                btnAddCourse.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                        int ID = Integer.parseInt(JOptionPane.showInputDialog("Enter the student's ID:"));
                        Student std = Controller.findByID(ID);
                        if(std ==null)
                        {
                            throw new UserNotFoundException();
                        }


                        JFileChooser j = new JFileChooser("");
                        int r = j.showOpenDialog(null);
                        if (r == JFileChooser.APPROVE_OPTION) 
                        {
                            File f = new File(j.getSelectedFile().getAbsolutePath());
                            try 
                            {
                                FileReader fr = new FileReader(f);
                                BufferedReader br = new BufferedReader(fr);

                                Controller.readCourseData(std, br);
                            }catch (FileNotFoundException error) 
                            {
                                JOptionPane.showMessageDialog(null, "Error, cannot find the file!", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    } catch (UserNotFoundException e2)
                    {
                        e2.errorPopUp();
                    }
                }});
                
                        
                btnAddCourse.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_1.add(btnAddCourse, BorderLayout.WEST);
                
                JLabel AddCourseDesc = new JLabel("This will allow you to add a course (up to 5).");
                AddCourseDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_1.add(AddCourseDesc, BorderLayout.CENTER);
                
                JPanel panel_2 = new JPanel();
                panel.add(panel_2);
                panel_2.setLayout(new BorderLayout(0, 0));
                
                JButton btnDropCourse = new JButton("Drop Course         ");
                btnDropCourse.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        {
                            JTextField id_entry = new JTextField();
                            JTextField name_entry = new JTextField();

                            Object[] info = {"Enter student ID: " , id_entry, "Enter course name:", name_entry};
                            int selected_option = JOptionPane.showConfirmDialog(null, info, "Please enter a student ID and Course Name", JOptionPane.OK_CANCEL_OPTION);
                
                            if (selected_option == JOptionPane.OK_OPTION) {
                                Student s = Controller.findByID(Integer.parseInt(id_entry.getText()));
                                if(s == null) {JOptionPane.showMessageDialog(null,
                                     "Error, cannot find the student!", "Error",
                                JOptionPane.ERROR_MESSAGE);}

                                Controller.removeCourse(s, name_entry.getText());
                            }
                        }
                    }
                });
                btnDropCourse.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_2.add(btnDropCourse, BorderLayout.WEST);
                
                JLabel DropCourseDesc = new JLabel("This will allow you to drop a course.");
                DropCourseDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_2.add(DropCourseDesc);
                
                JPanel panel_3 = new JPanel();
                panel.add(panel_3);
                panel_3.setLayout(new BorderLayout(0, 0));
                
                JButton btnShowStudentInfo = new JButton("Show Student Info");
                btnShowStudentInfo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try
                        {
                        String id = JOptionPane.showInputDialog("Enter student's ID: ");
                        Student s = Controller.findByID(Integer.parseInt(id));
                        if(s==null)
                        {
                            throw new UserNotFoundException();
                        }
                        
                        
                        String columnNames[] = { "Name", s.name, "ID", String.valueOf(s.ID), "Major", s.get_major() };
                        String dataValues[][] = new String [3 + s.get_num_courses()][6];
                        dataValues[0][0] = "Semester"; dataValues[0][1] = "Spring 2022";
                        dataValues[1][0] = "Courses"; dataValues[1][1] = "Name"; dataValues[1][2] = "Number";
                        dataValues[1][3] = "Credits"; dataValues[1][4] = "Grade";
                        for(int i = 2;i<dataValues.length - 1;i++)
                        {
                            dataValues[i][0] = String.valueOf((i-1));
                            dataValues[i][1] = s.courses.get(i-2).getName();
                            dataValues[i][2] = s.courses.get(i-2).getNumber();
                            dataValues[i][3] = String.valueOf(s.courses.get(i-2).getCredits());
                            if(s.grades.size()!=0)
                            {
                                dataValues[i][4] = String.valueOf(s.get_grades(i-2).getGrade());
                            }
                        }
                        dataValues[dataValues.length-1][3] = "GPA";
                        dataValues[dataValues.length-1][4] = String.valueOf(Controller.calcGPA(s));
                        JTable view_results = new JTable(dataValues, columnNames);
                        view_results.setRowHeight(50);
                        TableColumn column = null;
                        for(int i = 0;i<columnNames.length;i++)
                        {
                            column = view_results.getColumnModel().getColumn(i);
                            column.setPreferredWidth(500);
                        }
                        JScrollPane scrollPane = new JScrollPane(view_results);
                        JPanel resultsPane = new JPanel();
                        resultsPane.add(scrollPane);
                        JFrame resultsFrame = new JFrame("Information");
                        resultsFrame.getContentPane().add(resultsPane);
                        resultsFrame.setVisible(true);
                        resultsFrame.setBounds(100, 100, 691, 523);
                    }
       catch (UserNotFoundException e2)
      {
        e2.errorPopUp();
      }
      }});

                btnShowStudentInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_3.add(btnShowStudentInfo, BorderLayout.WEST);
                
                JLabel ShowInfoDesc = new JLabel("This will allow you to see a student's information.");
                ShowInfoDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_3.add(ShowInfoDesc);
                
                JPanel panel_4 = new JPanel();
                panel.add(panel_4);
                panel_4.setLayout(new BorderLayout(0, 0));
                
                JLabel secondaryLabel = new JLabel("Please choose from the following instructor settings: ");
                secondaryLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
                panel_4.add(secondaryLabel, BorderLayout.CENTER);
                
                JPanel panel_5 = new JPanel();
                panel.add(panel_5);
                panel_5.setLayout(new BorderLayout(0, 0));
                
                JButton ViewINSInfoBtn = new JButton("View INS Info");
                ViewINSInfoBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try
                        {
                        String name = JOptionPane.showInputDialog("Enter instructor's full name:");
                        
                        Instructor ins = Controller.findByName(name);
                        if (ins == null) {
                            throw new UserNotFoundException();
                        }
                        

                        String columnNames[] = { "Name", ins.name, "ID", String.valueOf(ins.ID), "Department", ins.department };
                        String dataValues[][] = new String [3 + ins.get_num_courses()][6];
                        dataValues[0][0] = "Term"; dataValues[0][1] = "Spring 2022";
                        dataValues[1][0] = "Courses"; 
                        dataValues[2][0] = "Name"; dataValues[2][1] = "Number";
                        for(int i = 3; i<dataValues.length; i++)
                        {
                            dataValues[i][0] = ins.courses.get(i-3).getName();
                            dataValues[i][1] = ins.courses.get(i-3).getNumber();
                        }
                        
                        JTable view_results = new JTable(dataValues, columnNames);
                        view_results.setRowHeight(50);
                        TableColumn column = null;
                        for(int i = 0;i<columnNames.length;i++)
                        {
                            column = view_results.getColumnModel().getColumn(i);
                            column.setPreferredWidth(500);
                        }
                        JScrollPane scrollPane = new JScrollPane(view_results);
                        JPanel resultsPane = new JPanel();
                        resultsPane.add(scrollPane);
                        JFrame resultsFrame = new JFrame("Information");
                        resultsFrame.getContentPane().add(resultsPane);
                        resultsFrame.setVisible(true);
                        resultsFrame.setBounds(100, 100, 691, 523);
                    }
                    catch (UserNotFoundException e2)
                    {
                        e2.errorPopUp();
                    }
                }});
                ViewINSInfoBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_5.add(ViewINSInfoBtn, BorderLayout.WEST);
                
                JLabel INSInfoDesc = new JLabel("This will allow you to view an instructor's information.");
                INSInfoDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_5.add(INSInfoDesc, BorderLayout.CENTER);
                
                JPanel panel_6 = new JPanel();
                panel.add(panel_6);
                panel_6.setLayout(new BorderLayout(0, 0));
                
                JButton ViewCrsInfoBtn = new JButton("View Course Info");
                ViewCrsInfoBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                        String courseChoice = JOptionPane.showInputDialog("Enter the course code to view its information:");
                
                        Course c = Controller.findCourse(courseChoice);
                        
                        if (c == null) {
                            throw new CourseRegistrationException("Course not found!");
                        }
                        
                        String columnNames[] = { "Name", c.getInstructor().name, 
                                                "ID", String.valueOf(c.getInstructor().ID), 
                                                "Department", c.getInstructor().department };
                        String dataValues[][] = new String [3 + c.students.size()][6];
                        dataValues[0][0] = "Term"; dataValues[0][1] = "Spring 2022";
                        dataValues[1][0] = "Course"; dataValues[1][1] = c.getNumber(); dataValues[1][2] = c.getName();
                        dataValues[2][0] = "ID"; dataValues[2][1] = "Name"; dataValues[2][2] = "Grade";
                        if(c.students.size()!=0)
                        {
                            for(int i = 3; i<dataValues.length; i++)
                            {
                                dataValues[i][0] = String.valueOf(c.students.get(i-3).getID());
                                dataValues[i][1] = c.students.get(i-3).getName();
                                dataValues[i][2] = String.valueOf(c.grades.get(i-3).getGrade()); 
                            }
                        }
                        JTable view_results = new JTable(dataValues, columnNames);
                        view_results.setRowHeight(50);
                        TableColumn column = null;
                        for(int i = 0;i<columnNames.length;i++)
                        {
                            column = view_results.getColumnModel().getColumn(i);
                            column.setPreferredWidth(500);
                        }
                        JScrollPane scrollPane = new JScrollPane(view_results);
                        JPanel resultsPane = new JPanel();
                        resultsPane.add(scrollPane);
                        
                        JButton saveInfo = new JButton("Save Information");
                        
                        
                        saveInfo.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                            Controller.saveCourseInfo(c.getInstructor(), c); 
                            }
                        });

                        resultsPane.add(saveInfo);
                        
                        
                        JFrame resultsFrame = new JFrame("Information");
                        resultsFrame.getContentPane().add(resultsPane);

                        resultsFrame.setVisible(true);
                        resultsFrame.setBounds(100, 100, 691, 523);
                    } catch (CourseRegistrationException err)
                    {
                        err.errorPopUp();
                    }
                }
                });
                ViewCrsInfoBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_6.add(ViewCrsInfoBtn, BorderLayout.WEST);
                
                JLabel CrsInfoDesc = new JLabel("This will allow you to view a course's information.");
                CrsInfoDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_6.add(CrsInfoDesc, BorderLayout.CENTER);

                JPanel panel_8 = new JPanel();
                panel.add(panel_8);
                panel_8.setLayout(new BorderLayout(0, 0));
                
                JButton AddCrsINSBtn = new JButton("Add Course");
                AddCrsINSBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                        String name = JOptionPane.showInputDialog("Enter the instructor's full name:");
                        
                        Instructor ins = Controller.findByName(name);

                        if (ins == null) {
                            throw new UserNotFoundException();
                        }
                        
                        Controller.addINSCourse(ins);
                        } catch (UserNotFoundException err)
                    {
                        err.errorPopUp();
                    }
      }});
                AddCrsINSBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_8.add(AddCrsINSBtn, BorderLayout.WEST);
                
                JLabel AddCrsINSDesc = new JLabel("This will allow you to add an instructor to a course.");
                AddCrsINSDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_8.add(AddCrsINSDesc, BorderLayout.CENTER);
                
                JPanel panel_7 = new JPanel();
                panel.add(panel_7);
                panel_7.setLayout(new BorderLayout(0, 0));
                
                JButton ChangeINSNameBtn = new JButton("Change INS Name");
                ChangeINSNameBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = JOptionPane.showInputDialog("Enter the instructor's full name:");
                        Instructor ins = Controller.findByName(name);
                 
                        String newName = JOptionPane.showInputDialog("Their current name is " + ins.name
                    + ". What would you like their name to be changed to?");
                        Controller.changeINSName(ins, newName);
                    
                        }}
                    );
                    ChangeINSNameBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    panel_7.add(ChangeINSNameBtn, BorderLayout.WEST);
                    
                    JLabel INSNameDesc = new JLabel("This will allow you to change an instructor's name.");
                    INSNameDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    panel_7.add(INSNameDesc, BorderLayout.CENTER);
                    
                    JPanel panel_7_1 = new JPanel();
                    panel.add(panel_7_1);
                    panel_7_1.setLayout(new BorderLayout(0, 0));
                    
                    JLabel ChangePassDesc = new JLabel("This will allow you to change an instructor's password.");
                    ChangePassDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    panel_7_1.add(ChangePassDesc, BorderLayout.CENTER);
                    
                    JButton ChangePassBtn = new JButton("Change INS Password");
                ChangePassBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                        String name = JOptionPane.showInputDialog("Enter the instructor's full name:");
                        Instructor ins = Controller.findByName(name);
                        if(ins == null)
                        {
                            throw new UserNotFoundException();
                        }
                        String pass = JOptionPane.showInputDialog("Enter the instructor's new password:");
                        ins.password = pass;
                    } catch (UserNotFoundException e2)
                    {
                        e2.errorPopUp();
                    }
                   } });
                ChangePassBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
                panel_7_1.add(ChangePassBtn, BorderLayout.WEST);
            }
        

      }  