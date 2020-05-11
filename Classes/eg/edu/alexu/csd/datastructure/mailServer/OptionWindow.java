package eg.edu.alexu.csd.datastructure.mailServer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;

public class OptionWindow extends JPanel {

	private App app;
	private JPanel previousPanel;
	private JPanel self = this;
	private JFrame frame;
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
	public OptionWindow(App app,JFrame frame, JPanel previousPanel) {
		this.frame = frame;
		
		/*SwingWorker<Mail, Void> mailViewer = new SwingWorker<Mail,Void>(){

			@Override
			protected Mail doInBackground() throws Exception {
				//Mail m = Mail.loadMail(thisMailFolder, numberOfReceivers)
				return null;
			}
			
			@Override
			public void done() {
				try {
					setEnabled(false);
					setVisible(false);
					frame.add(new ViewMail(get(), app,frame));
				}catch(InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				return;
			}
			
		};*/
		
		
		this.app = app;
		this.previousPanel = previousPanel;
		
		frame.setTitle("Options");
		setBounds(100, 100, 798, 579);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel nameLabel = new JLabel(app.signedInUser.getName());
		nameLabel.setForeground(new Color(0, 0, 51));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		nameLabel.setBounds(23, 176, 226, 37);
		add(nameLabel);
		
		JLabel addressLabel = new JLabel(app.signedInUser.getAddresses()[0] + "@system.com");
		addressLabel.setForeground(new Color(0, 0, 51));
		addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addressLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		addressLabel.setBounds(58, 109, 312, 37);
		add(addressLabel);
		
		JLabel genderLabel = new JLabel(app.signedInUser.getGender());
		genderLabel.setForeground(new Color(0, 0, 51));
		genderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		genderLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		genderLabel.setBounds(23, 223, 226, 37);
		add(genderLabel);
		
		JLabel birthDateLabel = new JLabel(app.signedInUser.getBirthDate());
		birthDateLabel.setForeground(new Color(0, 0, 51));
		birthDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		birthDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		birthDateLabel.setBounds(23, 277, 226, 37);
		add(birthDateLabel);
		
		JButton LogOutButton = new JButton("log out");
		LogOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnabled(false);
				setVisible(false);
				
				previousPanel.setEnabled(true);
				previousPanel.setVisible(true);
			}
		});
		LogOutButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		LogOutButton.setBounds(23, 497, 121, 35);
		add(LogOutButton);
		
		JButton editPasswordButton = new JButton("edit password");
		editPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnabled(false);
				setVisible(false);
				
	      		frame.add(new PasswordEdit(app,frame,self));
			}
		});
		editPasswordButton.setForeground(new Color(0, 0, 51));
		editPasswordButton.setBackground(Color.WHITE);
		editPasswordButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		editPasswordButton.setBounds(58, 338, 312, 35);
		add(editPasswordButton);
		
		JLabel profileLabel = new JLabel("profile");
		profileLabel.setForeground(new Color(0, 0, 51));
		profileLabel.setFont(new Font("Century Gothic", Font.BOLD, 55));
		profileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		profileLabel.setBounds(108, 10, 246, 84);
		add(profileLabel);
		
		JButton compose = new JButton("Compose +");
		compose.setBackground(new Color(224, 224, 224));
		compose.setForeground(new Color(219, 112, 147));
		compose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SwingWorker<Mail, Void> mailMaker = new SwingWorker<Mail,Void>(){

					@Override
					protected Mail doInBackground() throws Exception {
						Mail m = new Mail(app.signedInUser);
						m.saveMail();
						return m;
					}
					
					@Override
					public void done() {
						try {
							setEnabled(false);
							setVisible(false);
							
							frame.add(new EditMail(get(), app,frame, self));
						}catch(InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
						return;
					}
					
				};
				mailMaker.execute();
			}
		});
		compose.setFont(new Font("Century Gothic", Font.BOLD, 25));
		compose.setBounds(587, 10, 187, 56);
		add(compose);
		
		JButton inbox = new JButton("Inbox");
		inbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailBox m = new MailBox(app,(MailFolder)app.signedInUser.getInboxPath(),frame);
				frame.setEnabled(false);
				frame.setVisible(false);
			}
		});
		inbox.setBackground(new Color(224, 224, 224));
		inbox.setForeground(new Color(199, 21, 133));
		inbox.setFont(new Font("Century Gothic", Font.BOLD, 25));
		inbox.setBounds(587, 98, 187, 56);
		add(inbox);
		
		JButton sent = new JButton("Sent");
		sent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailBox m = new MailBox(app,(MailFolder)app.signedInUser.getSentPath(),frame);
				frame.setEnabled(false);
				frame.setVisible(false);
			}
		});
		sent.setBackground(new Color(224, 224, 224));
		sent.setForeground(new Color(199, 21, 133));
		sent.setFont(new Font("Century Gothic", Font.BOLD, 25));
		sent.setBounds(587, 189, 187, 56);
		add(sent);
		
		JButton draft = new JButton("Draft");
		draft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailBox m = new MailBox(app,(MailFolder)app.signedInUser.getDraftPath(),frame);
				frame.setEnabled(false);
				frame.setVisible(false);
			}
		});
		draft.setBackground(new Color(224, 224, 224));
		draft.setForeground(new Color(199, 21, 133));
		draft.setFont(new Font("Century Gothic", Font.BOLD, 25));
		draft.setBounds(587, 287, 187, 56);
		add(draft);
		
		JButton trash = new JButton("Trash");
		trash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailBox m = new MailBox(app,(MailFolder)app.signedInUser.getTrashPath(),frame);
				frame.setEnabled(false);
				frame.setVisible(false);
			}
		});
		trash.setBackground(new Color(224, 224, 224));
		trash.setForeground(new Color(199, 21, 133));
		trash.setFont(new Font("Century Gothic", Font.BOLD, 25));
		trash.setBounds(587, 380, 187, 56);
		add(trash);
		
		JButton contacts = new JButton("Contacts");
		contacts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contacts editWindow = new Contacts(app, frame);
				frame.setEnabled(false);
				frame.setVisible(false);
			}
		});
		contacts.setBackground(new Color(224, 224, 224));
		contacts.setForeground(new Color(199, 21, 133));
		contacts.setFont(new Font("Century Gothic", Font.BOLD, 25));
		contacts.setBounds(587, 476, 187, 56);
		add(contacts);
		
		JButton nameEdit = new JButton("edit");
		nameEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NameEdit editWindow = new NameEdit(app, frame);
				frame.setEnabled(false);
				frame.setVisible(false);
			}
		});
		nameEdit.setForeground(new Color(0, 0, 51));
		nameEdit.setFont(new Font("Tahoma", Font.PLAIN, 23));
		nameEdit.setBounds(295, 177, 75, 34);
		add(nameEdit);
		
		JButton genderButton = new JButton("edit");
		genderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenderEdit editWindow = new GenderEdit(app, frame);
				frame.setEnabled(false);
				frame.setVisible(false);
			}
		});
		genderButton.setForeground(new Color(0, 0, 51));
		genderButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		genderButton.setBounds(295, 228, 75, 34);
		add(genderButton);
		
		JButton birthDateButton = new JButton("edit");
		birthDateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateEdit editWindow = new DateEdit(app, birthDateLabel ,frame);
				frame.setEnabled(false);
				frame.setVisible(false);
			}
		});
		birthDateButton.setForeground(new Color(0, 0, 51));
		birthDateButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		birthDateButton.setBounds(295, 276, 75, 34);
		add(birthDateButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		panel.setBounds(0, 18, 451, 418);
		add(panel);
		
		setEnabled(true);
		setVisible(true);
	}
	
	public void setApp(App app) {
		this.app = app;
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
		if(e) {
			frame.setBounds(100, 100, 798, 579);
			frame.setVisible(true);
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
