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
import javax.swing.JPasswordField;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PasswordEdit extends JPanel {

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
	public PasswordEdit(App app, JFrame frame, JPanel previousPanel) {
		
		SwingWorker<Void, Void> userEditor = new SwingWorker<Void,Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				app.signedInUser.writeToFile();
				return null;
			}			
		};
		
		this.app = app;
		setBounds(100, 100, 521, 357);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		CurrentPassword.setForeground(new Color(0, 139, 139));
		CurrentPassword.setFont(new Font("Century Gothic", Font.BOLD, 16));
		CurrentPassword.setBounds(56, 28, 135, 36);
		add(CurrentPassword);
		
		JLabel lblNewPassword = new JLabel("New PassWord");
		lblNewPassword.setForeground(new Color(0, 139, 139));
		lblNewPassword.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewPassword.setBounds(56, 88, 135, 36);
		add(lblNewPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm PassWord");
		lblConfirmPassword.setForeground(new Color(0, 139, 139));
		lblConfirmPassword.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblConfirmPassword.setBounds(56, 147, 140, 36);
		add(lblConfirmPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(233, 34, 216, 31);
		add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(233, 94, 216, 31);
		add(passwordField_1);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(233, 152, 216, 31);
		add(passwordField_2);
		
		JButton ok = new JButton("Confirm");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if (!passwordField_1.getPassword().equals(passwordField_2.getPassword())) {
			          	JOptionPane.showMessageDialog(null, "passwords do not match");
			         }
			         if(!app.signedInUser.matchPassword(passwordField.getPassword().toString())){
			          	JOptionPane.showMessageDialog(null, "wrong password");
			         }
			         else if (!app.signedInUser.setPassword(passwordField_1.getPassword().toString())){
			           JOptionPane.showMessageDialog(null, "Invalid password");
			         } else {
			        	    userEditor.execute();
			        	    
			        	    setEnabled(false);
							setVisible(false);
							
							previousPanel.setEnabled(true);
							previousPanel.setVisible(true);
			         }
					}
		});
		ok.setBackground(new Color(255, 255, 255));
		ok.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ok.setForeground(new Color(0, 206, 209));
		ok.setBounds(106, 244, 107, 36);
		add(ok);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnabled(false);
				setVisible(false);
				
				previousPanel.setEnabled(true);
				previousPanel.setVisible(true);
			}
		});
		btnCancel.setBackground(new Color(255, 255, 255));
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCancel.setForeground(new Color(0, 206, 209));
		btnCancel.setBounds(277, 244, 107, 36);
		add(btnCancel);
		
		frame.setBounds(100, 100, 521, 357);;
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