package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.datastructure.mailServer.App;
import eg.edu.alexu.csd.datastructure.mailServer.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class SignUpWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private App app;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpWindow frame = new SignUpWindow();
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
	public SignUpWindow() {
		this.app = app;
		setTitle("Sign up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 644);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setForeground(new Color(75, 0, 130));
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblNewLabel.setBounds(80, 60, 82, 39);
		contentPane.add(lblNewLabel);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(new Color(75, 0, 130));
		lblLastName.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblLastName.setBounds(80, 109, 82, 39);
		contentPane.add(lblLastName);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddress.setForeground(new Color(75, 0, 130));
		lblAddress.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblAddress.setBounds(80, 158, 82, 39);
		contentPane.add(lblAddress);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(new Color(75, 0, 130));
		lblPassword.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblPassword.setBounds(82, 207, 82, 39);
		contentPane.add(lblPassword);

		JLabel lblConfirmPassword = new JLabel("Confirm password");
		lblConfirmPassword.setForeground(new Color(75, 0, 130));
		lblConfirmPassword.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblConfirmPassword.setBounds(28, 256, 136, 39);
		contentPane.add(lblConfirmPassword);

		JLabel lblGendre = new JLabel("Gendre");
		lblGendre.setHorizontalAlignment(SwingConstants.CENTER);
		lblGendre.setForeground(new Color(75, 0, 130));
		lblGendre.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblGendre.setBounds(80, 305, 82, 39);
		contentPane.add(lblGendre);

		JLabel lblBirthDate = new JLabel("Birth date");
		lblBirthDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblBirthDate.setForeground(new Color(75, 0, 130));
		lblBirthDate.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblBirthDate.setBounds(80, 355, 82, 39);
		contentPane.add(lblBirthDate);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(216, 191, 216));
		panel.setBounds(15, 53, 164, 350);
		contentPane.add(panel);

		textField = new JTextField();
		textField.setBounds(201, 60, 185, 31);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(201, 109, 185, 31);
		contentPane.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(201, 164, 185, 31);
		contentPane.add(textField_2);

		JLabel lblNewLabel_1 = new JLabel("@system.com");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.ITALIC, 15));
		lblNewLabel_1.setBounds(387, 167, 132, 24);
		contentPane.add(lblNewLabel_1);

		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(201, 215, 185, 31);
		contentPane.add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setEchoChar('*');
		passwordField_1.setBounds(201, 265, 185, 31);
		contentPane.add(passwordField_1);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Male");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		rdbtnNewRadioButton.setBounds(201, 316, 70, 21);
		contentPane.add(rdbtnNewRadioButton);

		JRadioButton rdbtnFemale = new JRadioButton("Female");
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		rdbtnFemale.setBounds(287, 316, 82, 21);
		contentPane.add(rdbtnFemale);

		JButton btnGoBack = new JButton("back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Welcome newWindow = new Welcome();
				newWindow.frame.setVisible(true);
			}
		});
		btnGoBack.setForeground(new Color(255, 20, 147));
		btnGoBack.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnGoBack.setBackground(new Color(216, 191, 216));
		btnGoBack.setBounds(201, 564, 99, 29);
		contentPane.add(btnGoBack);

		String[] days = new String[31];
		for (int i = 1; i <= 31; i++) {
			days[i - 1] = Integer.toString(i);
		}

		JComboBox comboBox = new JComboBox(days);
		comboBox.setForeground(new Color(139, 0, 139));
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setMaximumRowCount(7);
		comboBox.setBounds(207, 366, 42, 26);
		contentPane.add(comboBox);

		String[] months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

		JComboBox comboBox_1 = new JComboBox(months);
		comboBox_1.setMaximumRowCount(7);
		comboBox_1.setForeground(new Color(139, 0, 139));
		comboBox_1.setBackground(Color.WHITE);
		comboBox_1.setBounds(288, 366, 50, 26);
		contentPane.add(comboBox_1);

		String[] years = new String[80];
		for (int i = 0; i < 80; i++) {
			years[i] = Integer.toString(i + 1941);
		}

		JComboBox comboBox_2 = new JComboBox(years);
		comboBox_2.setMaximumRowCount(7);
		comboBox_2.setForeground(new Color(139, 0, 139));
		comboBox_2.setBackground(Color.WHITE);
		comboBox_2.setBounds(396, 366, 52, 26);
		comboBox_2.setSelectedItem("2020");
		contentPane.add(comboBox_2);

		JLabel lblNewLabel_2 = new JLabel("day");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(255, 366, 34, 24);
		contentPane.add(lblNewLabel_2);

		JLabel lblMonth = new JLabel("month");
		lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMonth.setBounds(347, 366, 40, 24);
		contentPane.add(lblMonth);

		JLabel lblYear = new JLabel("year");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblYear.setBounds(458, 366, 40, 24);
		contentPane.add(lblYear);

		JButton btnNewButton = new JButton("reset");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				passwordField.setText("");
				passwordField_1.setText("");
				buttonGroup.clearSelection();
				comboBox.setSelectedIndex(0);
				comboBox_1.setSelectedIndex(0);
				comboBox_2.setSelectedIndex(79);
			}
		});
		btnNewButton.setForeground(new Color(255, 20, 147));
		btnNewButton.setBackground(new Color(216, 191, 216));
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnNewButton.setBounds(201, 482, 99, 29);
		contentPane.add(btnNewButton);

		JButton btnSignUp = new JButton("sign up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // 1-Fname 2-Lname 3-address 4-date 5-gender 6-password 7-salt
				System.out.println("ok!");
				String date = comboBox_1.getSelectedObjects() + "-" + comboBox.getSelectedObjects() + "-"
						+ comboBox_2.getSelectedObjects();
				boolean gender = true;

				User user;
				user = new User(textField_2.getText(),true);
				user.setGender(gender);

				if (rdbtnNewRadioButton.isSelected()) {
					gender = true;
				} else if (rdbtnFemale.isSelected()) {
					gender = false;
				} else if (!rdbtnNewRadioButton.isSelected() && !rdbtnFemale.isSelected()) {
					JOptionPane.showMessageDialog(null, "Set Gender.");
				} else if (passwordField.getText() != passwordField_1.getText()) {
					JOptionPane.showMessageDialog(null, "passwords does not match");
				} else if (!user.setName(textField.getText(),textField_1.getText())) {
					JOptionPane.showMessageDialog(null, "Invalid name");
				} else if (!user.setBirthDate(date)) {
					JOptionPane.showMessageDialog(null, "Invalid date");
				} else if (!user.setAddress(textField_2.getText())) {
					JOptionPane.showMessageDialog(null, "Invalid address");
				} else if (!user.setPassword(passwordField.getText())) {
					JOptionPane.showMessageDialog(null, "Invalid password");
				} else {
					user.writeToFile();
					app.signup(user); 
					dispose();
					OptionWindow o = new OptionWindow();
					o.setApp(app);
					o.setVisible(true);
				}

			}
		});
		btnSignUp.setForeground(new Color(255, 20, 147));
		btnSignUp.setBackground(new Color(216, 191, 216));
		btnSignUp.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnSignUp.setBounds(201, 525, 99, 29);
		contentPane.add(btnSignUp);

	}

	public void setApp(App app) {
		this.app = app;
	}
}
