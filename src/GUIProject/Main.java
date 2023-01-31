package GUIProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        try {
            // make sure to change file path
            JOptionPane.showMessageDialog(null, "Please select student file", "",
            JOptionPane.INFORMATION_MESSAGE);
            JFileChooser chooser = new JFileChooser("F: ");
                    int r = chooser.showOpenDialog(null);
                    File file = null;
                    if (r == JFileChooser.APPROVE_OPTION) {
                        file = new File(chooser.getSelectedFile().getAbsolutePath());
                    }
             Scanner in = new Scanner(file);

            int numStudents = Integer.parseInt(in.nextLine());

            for (int i = 0; i < numStudents; i++) {
                String line = in.nextLine();
                StringTokenizer st = new StringTokenizer(line, ";");
                String name = st.nextToken();
                String id = st.nextToken();
                String gender = id.substring(0, 1);
                int ID = Integer.parseInt(id.substring(1));
                String major = st.nextToken();
                String password = st.nextToken();

                Student temp = new Student(name, ID, gender, major, password);
                Database.students.add(temp);
               
            }
            in.close();
            JOptionPane.showMessageDialog(null, "Please select Employee file", "",
            JOptionPane.INFORMATION_MESSAGE);

            JFileChooser chooser2 = new JFileChooser("F: ");
                    int r2 = chooser.showOpenDialog(null);
                    File file2 = null;
                    if (r2 == JFileChooser.APPROVE_OPTION) {
                        file2 = new File(chooser.getSelectedFile().getAbsolutePath());
                    }
             Scanner in2 = new Scanner(file2);
            // make sure to change file path
            int numINS = Integer.parseInt(in2.nextLine());

            for (int i = 0; i < numINS; i++) {
                String line = in2.nextLine();
                StringTokenizer st2 = new StringTokenizer(line, ";");
                String name = st2.nextToken();
                String email = st2.nextToken();
                String id = st2.nextToken();
                String dept = st2.nextToken();
                int ID = Integer.parseInt(id);               
                String password = st2.nextToken();

                Instructor temp = new Instructor(name, email, ID, dept, password);
                Database.instructors.add(temp);
            }
            in2.close();
            JOptionPane.showMessageDialog(null, "Please select admin file", "",
            JOptionPane.INFORMATION_MESSAGE);

            JFileChooser chooser32 = new JFileChooser("F: ");
                    int r3 = chooser.showOpenDialog(null);
                    File file3 = null;
                    if (r3 == JFileChooser.APPROVE_OPTION) {
                        file3 = new File(chooser.getSelectedFile().getAbsolutePath());
                    }
             Scanner in3 = new Scanner(file3);
            // make sure to change file path
            int numAdmin = Integer.parseInt(in3.nextLine());

            for (int i = 0; i < numAdmin; i++) {
                String line = in3.nextLine();
                StringTokenizer st3 = new StringTokenizer(line, ";");
                String name = st3.nextToken();
                String email = st3.nextToken();
                String id = st3.nextToken();
                int ID = Integer.parseInt(id);               
                String password = st3.nextToken();

                Admin temp = new Admin(name, email, ID, password);
                Database.admins.add(temp);
            }
            in3.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error! File not found.");
        }

        for (int i = 0; i < Database.students.size(); i++) {
            System.out.println(Database.students.get(i));
        }
        
        try {
            GUIThread t1 = new GUIThread();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}