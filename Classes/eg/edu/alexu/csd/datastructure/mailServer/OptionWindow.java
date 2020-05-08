package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.datastructure.mailServer.App;
import eg.edu.alexu.csd.datastructure.mailServer.User;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptionWindow extends JFrame {

	private JPanel contentPane;
	private App app;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OptionWindow frame = new OptionWindow();
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
	public OptionWindow(App app) {
		this.app = app;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 798, 579);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nameLabel = new JLabel(app.signedInUser.getName());
		nameLabel.setForeground(new Color(0, 0, 51));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		nameLabel.setBounds(23, 131, 226, 37);
		contentPane.add(nameLabel);
		
		JLabel addressLabel = new JLabel(app.signedInUser.getAddresses()[0]);
		addressLabel.setForeground(new Color(0, 0, 51));
		addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addressLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		addressLabel.setBounds(23, 178, 226, 37);
		contentPane.add(addressLabel);
		
		JLabel genderLabel = new JLabel(app.signedInUser.getGender());
		genderLabel.setForeground(new Color(0, 0, 51));
		genderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		genderLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		genderLabel.setBounds(23, 223, 226, 37);
		contentPane.add(genderLabel);
		
		JLabel birthDateLabel = new JLabel(app.signedInUser.getBirthDate());
		birthDateLabel.setForeground(new Color(0, 0, 51));
		birthDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		birthDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		birthDateLabel.setBounds(23, 277, 226, 37);
		contentPane.add(birthDateLabel);
		
		JButton LogOutButton = new JButton("log out");
		LogOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      	dispose();
				Welcome newWindow = new Welcome();
				newWindow.app = null;
				newWindow.frame.setVisible(true);
			}
		});
		LogOutButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		LogOutButton.setBounds(23, 497, 121, 35);
		contentPane.add(LogOutButton);
		
		JButton editPasswordButton = new JButton("edit password");
		editPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      		/* new window >> write new password 
           String >> take text 
      		if(!app.signedInUser.setPassword()){
          		JOptionPane.showMessageDialog(null, "Invalid password");
          }
          app.signedInUser.writeToFile(); //will be edited in data base?*/
			}
		});
		editPasswordButton.setForeground(new Color(0, 0, 51));
		editPasswordButton.setBackground(Color.WHITE);
		editPasswordButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		editPasswordButton.setBounds(58, 338, 312, 35);
		contentPane.add(editPasswordButton);
		
		JLabel profileLabel = new JLabel("profile");
		profileLabel.setForeground(new Color(0, 0, 51));
		profileLabel.setFont(new Font("Century Gothic", Font.BOLD, 55));
		profileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		profileLabel.setBounds(108, 10, 246, 84);
		contentPane.add(profileLabel);
		
		JButton compose = new JButton("Compose +");
		compose.setBackground(new Color(224, 224, 224));
		compose.setForeground(new Color(219, 112, 147));
		compose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      		// open new compose window
			}
		});
		compose.setFont(new Font("Century Gothic", Font.BOLD, 25));
		compose.setBounds(587, 10, 187, 56);
		contentPane.add(compose);
		
		JButton inbox = new JButton("Inbox");
		inbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      		// open new compose window
			}
		});
		inbox.setBackground(new Color(224, 224, 224));
		inbox.setForeground(new Color(199, 21, 133));
		inbox.setFont(new Font("Century Gothic", Font.BOLD, 25));
		inbox.setBounds(587, 98, 187, 56);
		contentPane.add(inbox);
		
		JButton sent = new JButton("Sent");
		sent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      		// open new compose window
			}
		});
		sent.setBackground(new Color(224, 224, 224));
		sent.setForeground(new Color(199, 21, 133));
		sent.setFont(new Font("Century Gothic", Font.BOLD, 25));
		sent.setBounds(587, 189, 187, 56);
		contentPane.add(sent);
		
		JButton draft = new JButton("Draft");
		draft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      		// open new compose window
			}
		});
		draft.setBackground(new Color(224, 224, 224));
		draft.setForeground(new Color(199, 21, 133));
		draft.setFont(new Font("Century Gothic", Font.BOLD, 25));
		draft.setBounds(587, 287, 187, 56);
		contentPane.add(draft);
		
		JButton trash = new JButton("Trash");
		trash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      		// open new compose window
			}
		});
		trash.setBackground(new Color(224, 224, 224));
		trash.setForeground(new Color(199, 21, 133));
		trash.setFont(new Font("Century Gothic", Font.BOLD, 25));
		trash.setBounds(587, 380, 187, 56);
		contentPane.add(trash);
		
		JButton contacts = new JButton("Contacts");
		contacts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      		// open new compose window
			}
		});
		contacts.setBackground(new Color(224, 224, 224));
		contacts.setForeground(new Color(199, 21, 133));
		contacts.setFont(new Font("Century Gothic", Font.BOLD, 25));
		contacts.setBounds(587, 476, 187, 56);
		contentPane.add(contacts);
		
		JButton nameEdit = new JButton("edit");
		nameEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      		/* new window >> write new name
          String >> take text 
      		if(!app.signedInUser.setName(first,last)){
          		JOptionPane.showMessageDialog(null, "Invalid name");
          }
          app.signedInUser.writeToFile(); //will be edited in data base?*/
			}
		});
		nameEdit.setForeground(new Color(0, 0, 51));
		nameEdit.setFont(new Font("Tahoma", Font.PLAIN, 23));
		nameEdit.setBounds(295, 132, 75, 34);
		contentPane.add(nameEdit);
		
		JButton addressButton = new JButton("edit");
		addressButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      		/* new window >> write new address
          String >> take text 
      		if(!app.signedInUser.setAddress(address)){
          		JOptionPane.showMessageDialog(null, "Invalid name");
          }
          app.signedInUser.writeToFile(); //will be edited in data base?*/
			}
		});
		addressButton.setForeground(new Color(0, 0, 51));
		addressButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		addressButton.setBounds(295, 180, 75, 34);
		contentPane.add(addressButton);
		
		JButton genderButton = new JButton("edit");
		genderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      /* new window >> select new gender 
      		if(!app.signedInUser.setGender()){
          		JOptionPane.showMessageDialog(null, "Invalid name");
          }
          app.signedInUser.writeToFile(); //will be edited in data base?*/
			}
		});
		genderButton.setForeground(new Color(0, 0, 51));
		genderButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		genderButton.setBounds(295, 228, 75, 34);
		contentPane.add(genderButton);
		
		JButton birthDateButton = new JButton("edit");
		birthDateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
      		/* new window >>get new date 
      		if(!app.signedInUser.setBirthDate(first,last)){
          		JOptionPane.showMessageDialog(null, "Invalid name");
          }
          app.signedInUser.writeToFile(); //will be edited in data base?*/
			}
		});
		birthDateButton.setForeground(new Color(0, 0, 51));
		birthDateButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		birthDateButton.setBounds(295, 276, 75, 34);
		contentPane.add(birthDateButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		panel.setBounds(10, 10, 451, 418);
		contentPane.add(panel);
	}
	
	public void setApp(App app) {
		this.app = app;
	}

}
