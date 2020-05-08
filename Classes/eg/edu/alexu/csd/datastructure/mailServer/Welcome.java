package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import eg.edu.alexu.csd.datastructure.mailServer.App;

public class Welcome {

	protected JFrame frame;
	protected App app = new App();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome window = new Welcome();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Welcome() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Mail server");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 549, 359);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton signInButton = new JButton("Sign In");
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				SignInWindow newWindow = new SignInWindow();
				newWindow.setApp(app);
				newWindow.setVisible(true);
			}
		});
		signInButton.setBackground(Color.WHITE);
		signInButton.setForeground(Color.BLUE);
		signInButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		signInButton.setBounds(219, 148, 94, 34);
		frame.getContentPane().add(signInButton);
		
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setBackground(Color.WHITE);
		signUpButton.setForeground(Color.MAGENTA);
		signUpButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				SignUpWindow newWindow = new SignUpWindow();
				newWindow.setApp(app);
				newWindow.setVisible(true);
			}
		});
		signUpButton.setBounds(219, 192, 94, 34);
		frame.getContentPane().add(signUpButton);
		
		JLabel welcomeText = new JLabel("Welcome");
		welcomeText.setForeground(Color.DARK_GRAY);
		welcomeText.setFont(new Font("Agency FB", Font.PLAIN, 60));
		welcomeText.setBounds(178, 48, 175, 65);
		frame.getContentPane().add(welcomeText);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		exitButton.setBackground(Color.WHITE);
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		exitButton.setBounds(219, 270, 94, 27);
		frame.getContentPane().add(exitButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.PINK);
		panel.setBounds(205, 122, 120, 126);
		frame.getContentPane().add(panel);
	}
}
