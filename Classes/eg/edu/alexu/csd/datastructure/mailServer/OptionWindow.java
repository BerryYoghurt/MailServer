package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.datastructure.mailServer.App;
import eg.edu.alexu.csd.datastructure.mailServer.MailFolder;
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
		nameLabel.setBounds(23, 176, 226, 37);
		contentPane.add(nameLabel);
		
		JLabel addressLabel = new JLabel(app.signedInUser.getAddresses()[0] + "@system.com");
		addressLabel.setForeground(new Color(0, 0, 51));
		addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addressLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		addressLabel.setBounds(58, 109, 312, 37);
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
				PasswordEdit p = new PasswordEdit(app);
				p.setVisible(true);
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
      		// open new Inbox window
				MailBox m = new MailBox(app,(MailFolder)app.signedInUser.getInboxPath());
				m.setVisible(true);
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
      		// open new sent window
				MailBox m = new MailBox(app,(MailFolder)app.signedInUser.getSentPath());
				m.setVisible(true);
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
      		// open new draft window
				MailBox m = new MailBox(app,(MailFolder)app.signedInUser.getDraftPath());
				m.setVisible(true);
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
      		// open new trash window
				MailBox m = new MailBox(app,(MailFolder)app.signedInUser.getTrashPath());
				m.setVisible(true);
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
      		// open new contacts window
				Contacts c = new Contacts(app);
				c.setVisible(true);
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
				NameEdit n = new NameEdit(app,nameLabel);
				//System.out.println(n.getName());
				n.setVisible(true);
				/*if(n.FN != null && n.LN != null) {
					nameLabel.setText(n.FN + n.LN);
				}*/
			}
		});
		nameEdit.setForeground(new Color(0, 0, 51));
		nameEdit.setFont(new Font("Tahoma", Font.PLAIN, 23));
		nameEdit.setBounds(295, 177, 75, 34);
		contentPane.add(nameEdit);
		
		
		JButton genderButton = new JButton("edit");
		genderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenderEdit g = new GenderEdit(app,genderLabel);
				g.setVisible(true);
			}
		});
		genderButton.setForeground(new Color(0, 0, 51));
		genderButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		genderButton.setBounds(295, 228, 75, 34);
		contentPane.add(genderButton);
		
		JButton birthDateButton = new JButton("edit");
		birthDateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateEdit d = new DateEdit(app,birthDateLabel);
				d.setVisible(true);
			}
		});
		birthDateButton.setForeground(new Color(0, 0, 51));
		birthDateButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		birthDateButton.setBounds(295, 276, 75, 34);
		contentPane.add(birthDateButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		panel.setBounds(0, 18, 451, 418);
		contentPane.add(panel);
	}
	
	public void setApp(App app) {
		this.app = app;
	}

}
