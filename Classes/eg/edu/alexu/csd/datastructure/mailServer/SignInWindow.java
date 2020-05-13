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
import java.awt.Component;
import java.awt.Container;

import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;

public class SignInWindow extends JPanel {

	private JTextField textField;
	private JPasswordField passwordField;
	private App app;
	private JPanel self = this;
	private JPanel previousPanel;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInWindow frame = new SignInWindow(app);
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
	public SignInWindow(App app,JFrame frame, JPanel previousPanel) {
		frame.setBounds(100, 100, 549, 359);

		
		
		this.app = app;
		this.previousPanel = previousPanel;
		
		frame.setTitle("Sign in");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 506, 353);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.add(this);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("User address");
		lblNewLabel.setForeground(new Color(30, 144, 255));
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		lblNewLabel.setBounds(36, 57, 115, 29);
		add(lblNewLabel);

		JLabel lblPassword = new JLabel("password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(new Color(30, 144, 255));
		lblPassword.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		lblPassword.setBounds(46, 113, 105, 29);
		add(lblPassword);

		JLabel lblNewLabel_1 = new JLabel("@system.com");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.ITALIC, 15));
		lblNewLabel_1.setBounds(350, 58, 105, 29);
		add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(161, 61, 186, 27);
		add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(161, 117, 186, 27);
		add(passwordField);

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
		add(button);

		JButton btnSignIn = new JButton("sign in");
		btnSignIn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean,Void>(){
					@Override
					protected Boolean doInBackground() throws Exception {
						boolean check = app.signin(textField.getText(), passwordField.getText());
						return check;
					}
					@Override
					public void done() {
						boolean check;
						try {
							check = get();
							if (check) {
								setEnabled(false);
								setVisible(false);
								frame.add(new OptionWindow(app,frame,previousPanel));//the panel which should be returned to;
							} else {
								JOptionPane.showMessageDialog(null, "Wronge UserName OR Password.");
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				worker.execute();
			}
		});
		btnSignIn.setForeground(new Color(255, 20, 147));
		btnSignIn.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnSignIn.setBackground(new Color(216, 191, 216));
		btnSignIn.setBounds(192, 217, 99, 29);
		add(btnSignIn);

		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnabled(false);
				setVisible(false);
				
				previousPanel.setEnabled(true);
				previousPanel.setVisible(true);
				
				frame.setVisible(true);
			}
		});
		btnBack.setForeground(new Color(255, 20, 147));
		btnBack.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnBack.setBackground(new Color(216, 191, 216));
		btnBack.setBounds(192, 256, 99, 29);
		add(btnBack);
		
		frame.setVisible(true);
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

