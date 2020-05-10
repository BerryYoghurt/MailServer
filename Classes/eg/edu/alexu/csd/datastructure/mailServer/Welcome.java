package eg.edu.alexu.csd.datastructure.mailServer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import eg.edu.alexu.csd.datastructure.mailServer.App;

@SuppressWarnings("serial")
public class Welcome extends JPanel{

	protected JFrame frame;
	protected App app;
	private JPanel self = this;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome window = new Welcome();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public Welcome(App a, JFrame frame) {
		this.app = a;
		this.frame = frame;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	//EDT
	private void initialize() {
		/*TODO
		frame = new JFrame("Mail server");
		frame.getContentPane().setBackground(Color.WHITE);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);*/
		frame.setBounds(100, 100, 549, 359);
		JButton signInButton = new JButton("Sign In");
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//frame.dispose();
				self.setVisible(false);
				frame.add(new SignInWindow(app,frame,self));
				//newWindow.setVisible(true);
			}
		});
		signInButton.setBackground(Color.WHITE);
		signInButton.setForeground(Color.BLUE);
		signInButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		signInButton.setBounds(219, 148, 94, 34);
		this.add(signInButton);
		
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setBackground(Color.WHITE);
		signUpButton.setForeground(Color.MAGENTA);
		signUpButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnabled(false);
				setVisible(false);
				
				frame.add(new SignUpWindow(app,frame, self));
			}
		});
		signUpButton.setBounds(219, 192, 94, 34);
		this.add(signUpButton);
		
		JLabel welcomeText = new JLabel("Welcome");
		welcomeText.setForeground(Color.DARK_GRAY);
		welcomeText.setFont(new Font("Agency FB", Font.PLAIN, 60));
		welcomeText.setBounds(178, 48, 175, 65);
		this.add(welcomeText);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		exitButton.setBackground(Color.WHITE);
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		exitButton.setBounds(219, 270, 94, 27);
		this.add(exitButton);
		
		//JPanel panel = new JPanel();
		this.setBackground(Color.PINK);
		this.setBounds(205, 122, 120, 126);
		
		setEnabled(true);
		//frame.pack();
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
		if(e) {
			frame.setBounds(100, 100, 549, 359);
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
