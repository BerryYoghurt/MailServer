package eg.edu.alexu.csd.datastructure.mailServer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.datastructure.mailServer.App;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignInWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private App app;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInWindow frame = new SignInWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignInWindow() {
		// this.app = app;
		setTitle("Sign in");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 506, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("User address");
		lblNewLabel.setForeground(new Color(30, 144, 255));
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		lblNewLabel.setBounds(36, 57, 115, 29);
		contentPane.add(lblNewLabel);

		JLabel lblPassword = new JLabel("password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(new Color(30, 144, 255));
		lblPassword.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		lblPassword.setBounds(46, 113, 105, 29);
		contentPane.add(lblPassword);

		JLabel lblNewLabel_1 = new JLabel("@system.com");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.ITALIC, 15));
		lblNewLabel_1.setBounds(350, 58, 105, 29);
		contentPane.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(161, 61, 186, 27);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(161, 117, 186, 27);
		contentPane.add(passwordField);

		JButton button = new JButton("reset");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				passwordField.setText("");
			}
		});
		button.setForeground(new Color(255, 20, 147));
		button.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		button.setBackground(new Color(216, 191, 216));
		button.setBounds(192, 178, 99, 29);
		contentPane.add(button);

		JButton btnSignIn = new JButton("sign in");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean check = app.signin(textField.getText(), passwordField.getText());
				if (check) {
					dispose();
					OptionWindow o = new OptionWindow();
					o.setApp(app);
					o.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Wronge UserName OR Password.");
				}
			}
		});
		btnSignIn.setForeground(new Color(255, 20, 147));
		btnSignIn.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnSignIn.setBackground(new Color(216, 191, 216));
		btnSignIn.setBounds(192, 217, 99, 29);
		contentPane.add(btnSignIn);

		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Welcome newWindow = new Welcome();
				newWindow.app = null;
				newWindow.frame.setVisible(true);
			}
		});
		btnBack.setForeground(new Color(255, 20, 147));
		btnBack.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnBack.setBackground(new Color(216, 191, 216));
		btnBack.setBounds(192, 256, 99, 29);
		contentPane.add(btnBack);
	}

	public void setApp(App app) {
		this.app = app;
	}
}
