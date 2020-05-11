package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.datastructure.mailServer.App;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class passwordEdit extends JFrame {

	private JPanel contentPane;
	private final JLabel CurrentPassword = new JLabel("Current PassWord");
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
  private App app;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					passwordEdit frame = new passwordEdit(app);
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
	public passwordEdit(App app) {
  	this.app = app;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 521, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		CurrentPassword.setForeground(new Color(0, 139, 139));
		CurrentPassword.setFont(new Font("Century Gothic", Font.BOLD, 16));
		CurrentPassword.setBounds(56, 28, 135, 36);
		contentPane.add(CurrentPassword);
		
		JLabel lblNewPassword = new JLabel("New PassWord");
		lblNewPassword.setForeground(new Color(0, 139, 139));
		lblNewPassword.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewPassword.setBounds(56, 88, 135, 36);
		contentPane.add(lblNewPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm PassWord");
		lblConfirmPassword.setForeground(new Color(0, 139, 139));
		lblConfirmPassword.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblConfirmPassword.setBounds(56, 147, 140, 36);
		contentPane.add(lblConfirmPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(233, 34, 216, 31);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(233, 94, 216, 31);
		contentPane.add(passwordField_1);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(233, 152, 216, 31);
		contentPane.add(passwordField_2);
		
		JButton ok = new JButton("Confirm");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if (!passwordField_1.getText().equals(passwordField_2.getText())) {
          	JOptionPane.showMessageDialog(null, "passwords do not match");
          }
          if(!app.signedInUser.matchPassword(passwordField.getText())){
          	JOptionPane.showMessageDialog(null, "wrong password");
          }
         else if (!app.signedInUser.setPassword(passwordField_1.getText())){
           JOptionPane.showMessageDialog(null, "Invalid password");
         } else {
        	    app.signedInUser.writeToFile();
         		dispose();
         }
			}
		});
		ok.setBackground(new Color(255, 255, 255));
		ok.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ok.setForeground(new Color(0, 206, 209));
		ok.setBounds(106, 244, 107, 36);
		contentPane.add(ok);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBackground(new Color(255, 255, 255));
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCancel.setForeground(new Color(0, 206, 209));
		btnCancel.setBounds(277, 244, 107, 36);
		contentPane.add(btnCancel);
	}
}