package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.datastructure.mailServer.App;

public class GenderEdit extends JFrame {

	private JPanel contentPane;
	private final JRadioButton rdbtnNewRadioButton = new JRadioButton("Male");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private App app;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenderEdit frame = new GenderEdit();
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
	public GenderEdit(App app, JLabel genderLabel) {
		setTitle("Gender edit");
		this.app = app;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 323, 167);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setFont(new Font("Century Gothic", Font.PLAIN, 26));
		rdbtnNewRadioButton.setForeground(new Color(0, 139, 139));
		rdbtnNewRadioButton.setBounds(34, 22, 120, 21);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnFemale = new JRadioButton("Female");
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setForeground(new Color(0, 139, 139));
		rdbtnFemale.setFont(new Font("Century Gothic", Font.PLAIN, 26));
		rdbtnFemale.setBounds(170, 22, 120, 21);
		contentPane.add(rdbtnFemale);
		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNewRadioButton.isSelected()) {
					app.signedInUser.setGender(true);
				}
				else if(rdbtnFemale.isSelected()) {
					app.signedInUser.setGender(false);
				}
				genderLabel.setText(app.signedInUser.getGender());
				app.signedInUser.writeToFile();
				dispose();	
			}
		});
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setForeground(new Color(0, 206, 209));
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		btnNewButton.setBounds(22, 90, 115, 30);
		contentPane.add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setForeground(new Color(0, 206, 209));
		btnCancel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		btnCancel.setBackground(Color.WHITE);
		btnCancel.setBounds(170, 90, 115, 30);
		contentPane.add(btnCancel);
	}

}
