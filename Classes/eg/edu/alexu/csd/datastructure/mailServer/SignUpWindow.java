package eg.edu.alexu.csd.datastructure.mailServer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.datastructure.mailServer.App;
import eg.edu.alexu.csd.datastructure.mailServer.MailFolder;
import eg.edu.alexu.csd.datastructure.mailServer.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SignUpWindow extends JPanel {

	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private App app;
	private JPanel self = this;
		
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
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
	}*/

	/**
	 * Create the frame.
	 */
	public SignUpWindow(App app, JFrame frame,JPanel previousPanel) {
		this.app = app;
		frame.setTitle("Sign up");
		
		setBounds(100, 100, 530, 644);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setForeground(new Color(75, 0, 130));
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblNewLabel.setBounds(80, 60, 82, 39);
		add(lblNewLabel);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(new Color(75, 0, 130));
		lblLastName.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblLastName.setBounds(80, 109, 82, 39);
		add(lblLastName);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddress.setForeground(new Color(75, 0, 130));
		lblAddress.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblAddress.setBounds(80, 158, 82, 39);
		add(lblAddress);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(new Color(75, 0, 130));
		lblPassword.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblPassword.setBounds(82, 207, 82, 39);
		add(lblPassword);

		JLabel lblConfirmPassword = new JLabel("Confirm password");
		lblConfirmPassword.setForeground(new Color(75, 0, 130));
		lblConfirmPassword.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblConfirmPassword.setBounds(28, 256, 136, 39);
		add(lblConfirmPassword);

		JLabel lblGendre = new JLabel("Gendre");
		lblGendre.setHorizontalAlignment(SwingConstants.CENTER);
		lblGendre.setForeground(new Color(75, 0, 130));
		lblGendre.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblGendre.setBounds(80, 305, 82, 39);
		add(lblGendre);

		JLabel lblBirthDate = new JLabel("Birth date");
		lblBirthDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblBirthDate.setForeground(new Color(75, 0, 130));
		lblBirthDate.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblBirthDate.setBounds(80, 355, 82, 39);
		add(lblBirthDate);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(216, 191, 216));
		panel.setBounds(15, 53, 164, 350);
		add(panel);

		textField = new JTextField();
		textField.setBounds(201, 60, 185, 31);
		add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(201, 109, 185, 31);
		add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(201, 164, 185, 31);
		add(textField_2);

		JLabel lblNewLabel_1 = new JLabel("@system.com");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.ITALIC, 15));
		lblNewLabel_1.setBounds(387, 167, 132, 24);
		add(lblNewLabel_1);

		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(201, 215, 185, 31);
		add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setEchoChar('*');
		passwordField_1.setBounds(201, 265, 185, 31);
		add(passwordField_1);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Male");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		rdbtnNewRadioButton.setBounds(201, 316, 70, 21);
		add(rdbtnNewRadioButton);

		JRadioButton rdbtnFemale = new JRadioButton("Female");
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		rdbtnFemale.setBounds(287, 316, 82, 21);
		add(rdbtnFemale);

		JButton btnGoBack = new JButton("back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnabled(false);
				setVisible(false);
				
				previousPanel.setVisible(true);
				previousPanel.setEnabled(true);
			}
		});
		btnGoBack.setForeground(new Color(255, 20, 147));
		btnGoBack.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnGoBack.setBackground(new Color(216, 191, 216));
		btnGoBack.setBounds(201, 564, 99, 29);
		add(btnGoBack);

		String[] days = new String[31];
		for (int i = 1; i <= 31; i++) {
			days[i - 1] = Integer.toString(i);
		}

		JComboBox comboBox = new JComboBox(days);
		comboBox.setForeground(new Color(139, 0, 139));
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setMaximumRowCount(7);
		comboBox.setBounds(207, 366, 42, 26);
		add(comboBox);

		String[] months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

		JComboBox comboBox_1 = new JComboBox(months);
		comboBox_1.setMaximumRowCount(7);
		comboBox_1.setForeground(new Color(139, 0, 139));
		comboBox_1.setBackground(Color.WHITE);
		comboBox_1.setBounds(288, 366, 50, 26);
		add(comboBox_1);

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
		add(comboBox_2);

		JLabel lblNewLabel_2 = new JLabel("day");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(255, 366, 34, 24);
		add(lblNewLabel_2);

		JLabel lblMonth = new JLabel("month");
		lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMonth.setBounds(347, 366, 40, 24);
		add(lblMonth);

		JLabel lblYear = new JLabel("year");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblYear.setBounds(458, 366, 40, 24);
		add(lblYear);

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
		add(btnNewButton);

		JButton btnSignUp = new JButton("sign up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // 1-Fname 2-Lname 3-address 4-date 5-gender 6-password 7-salt
				String date = comboBox_1.getSelectedItem() + "-" + comboBox.getSelectedItem() + "-"
						+ comboBox_2.getSelectedItem();
				//System.out.println(date);
				boolean gender = true;

				User user;
				try {
					user = new User(textField_2.getText(),true);
				}catch(RuntimeException exep) {
					JOptionPane.showMessageDialog(null, "address already exists");
					return;
				}
				user.setGender(gender);

				if (rdbtnNewRadioButton.isSelected()) {
					gender = true;
				} else if (rdbtnFemale.isSelected()) {
					gender = false;
				} else if (!rdbtnNewRadioButton.isSelected() && !rdbtnFemale.isSelected()) {  //gender setting
					JOptionPane.showMessageDialog(null, "Set Gender.");

					try {
						MailFolder.removeDir(user.getPath());
					} catch (IOException e1) {
						System.out.println("problem");
						//e1.printStackTrace();
					}
					return;
				}/* else */
				if (!passwordField.getText().equals(passwordField_1.getText())) { 		//passwords >> check match
					JOptionPane.showMessageDialog(null, "passwords do not match");
					System.out.println(passwordField.getPassword().toString());
					System.out.println(passwordField_1.getPassword().toString());
					try {
						MailFolder.removeDir(user.getPath());
					} catch (IOException e1) {
						System.out.println("problem");
						//e1.printStackTrace();
					}
					return;
				} /*else*/ if (!user.setName(textField.getText(),textField_1.getText())) {	//name setting
					JOptionPane.showMessageDialog(null, "Invalid name");
					try {
						MailFolder.removeDir(user.getPath());
					} catch (IOException e1) {
						System.out.println("problem");
						//e1.printStackTrace();
					}
					return;
				} /*else*/ if (!user.setBirthDate(date)) {		//date setting
					JOptionPane.showMessageDialog(null, "Invalid date");
					try {
						MailFolder.removeDir(user.getPath());
					} catch (IOException e1) {
						System.out.println("problem");
						//e1.printStackTrace();
					}
					return;
				} /*else*/ if (!user.setAddress(textField_2.getText())) {		//address setting
					JOptionPane.showMessageDialog(null, "Invalid address");
					try {
						MailFolder.removeDir(user.getPath());
					} catch (IOException e1) {
						System.out.println("problem");
						//e1.printStackTrace();
					}
					return;
				} /*else*/ if (!user.setPassword(passwordField.getText())) {	//password setting
					JOptionPane.showMessageDialog(null, "Invalid password");
					try {
						MailFolder.removeDir(user.getPath());
					} catch (IOException e1) {
						System.out.println("problem");
						//e1.printStackTrace();
					}
					return;
				} /*else {*/
					user.writeToFile();
					if(!app.signup(user)) {
						//there is a problem >> user is not signed up >> what kind of problems?
					}
					frame.remove(self);//not returning to it again
					frame.add(new OptionWindow(app,frame,previousPanel));//if options ever returns, it returns to welcome
					//o.setApp(app);
					//o.setVisible(true);
				//}

			}
		});
		btnSignUp.setForeground(new Color(255, 20, 147));
		btnSignUp.setBackground(new Color(216, 191, 216));
		btnSignUp.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnSignUp.setBounds(201, 525, 99, 29);
		add(btnSignUp);
		
		setEnabled(true);
		setVisible(true);
		
		frame.setBounds(100, 100, 530, 644);
		frame.setVisible(true);
	}
	
	@Override
	public void setEnabled(boolean e) {
		super.setEnabled(e);
		for(Component c : this.getComponents()) {
			if(c instanceof Container) {
				recSetEnabled((Container)c, e);
			}else
				c.setEnabled(e);
		}
	}
	
	private void recSetEnabled(Container c, boolean e) {
		for(Component comp : c.getComponents()) {
			if(comp instanceof Container) {
				recSetEnabled((Container)comp,e);
			}
			else
				comp.setEnabled(e);
		}
		c.setEnabled(e);
	}

}
