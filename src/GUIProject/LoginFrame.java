package GUIProject;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame 
{
	private JPanel contentPane;
	private JTextField userField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// 	EventQueue.invokeLater(new Runnable() {
	// 		public void run() {
	// 			try {
	// 				LoginFrame frame = new LoginFrame();
	// 				frame.setVisible(true);
	// 			} catch (Exception e) {
	// 				e.printStackTrace();
	// 			}
	// 		}
	// 	});
	// }
	/**
	 * Create the frame.
	 */

	public LoginFrame() 
	{
		LoginFrame this_frame = this;
		setTitle("AUS Banner");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 688, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel header = new JLabel("AUS Banner");
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(new Font("Tahoma", Font.PLAIN, 22));
		header.setForeground(Color.RED);
		contentPane.add(header, BorderLayout.NORTH);
		
		JPanel MainPanel = new JPanel();
		contentPane.add(MainPanel, BorderLayout.CENTER);
		MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));
		
		JPanel SubPanel1 = new JPanel();
		MainPanel.add(SubPanel1);
		SubPanel1.setLayout(new BoxLayout(SubPanel1, BoxLayout.X_AXIS));
		
		JLabel username_label = new JLabel("Username:");
		username_label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SubPanel1.add(username_label);
		
		userField = new JTextField();
		SubPanel1.add(userField);
		userField.setColumns(10);
		
		JPanel SubPanel2 = new JPanel();
		MainPanel.add(SubPanel2);
		SubPanel2.setLayout(new BoxLayout(SubPanel2, BoxLayout.X_AXIS));
		
		JLabel pass_label = new JLabel("Password: ");
		pass_label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SubPanel2.add(pass_label);
		
		passwordField = new JPasswordField();
		SubPanel2.add(passwordField);
		
		JPanel SubPanel3 = new JPanel();
		MainPanel.add(SubPanel3);
		
		JCheckBox checkBox = new JCheckBox("Show Password");
		checkBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (checkBox.isSelected()) 
				{
	                passwordField.setEchoChar((char) 0);
	            } else 
				{
	                passwordField.setEchoChar('*');
	            }
			}
		});

		checkBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		SubPanel3.add(checkBox);
		JPanel SubPanel4 = new JPanel();
		contentPane.add(SubPanel4, BorderLayout.SOUTH);
		SubPanel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String user, pass;
				user = userField.getText();
				pass = String.valueOf(passwordField.getPassword());
				Controller.findUser(this_frame, user.toLowerCase(), pass);
			}
		});


		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SubPanel4.add(loginButton);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				userField.setText("");
				passwordField.setText("");
			}
		});
	
		resetButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SubPanel4.add(resetButton);

		JButton newUser = new JButton("Add Another User");
		newUser.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				GUIThread t2 = new GUIThread();
			}
		});
	
		newUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SubPanel4.add(newUser);
	}
}