package eg.edu.alexu.csd.datastructure.mailServer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AddContact extends JFrame {

	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField addTextField;
	private App app;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddContact frame = new AddContact();
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
	public AddContact(App app) {
		this.app = app;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nameLabel = new JLabel("Name :");
		nameLabel.setForeground(new Color(0, 139, 139));
		nameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 25));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(22, 63, 128, 32);
		contentPane.add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(170, 63, 191, 32);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel addressLabel = new JLabel("Address :");
		addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addressLabel.setForeground(new Color(0, 139, 139));
		addressLabel.setFont(new Font("Century Gothic", Font.PLAIN, 25));
		addressLabel.setBounds(22, 141, 128, 32);
		contentPane.add(addressLabel);
		
		addTextField = new JTextField();
		addTextField.setColumns(10);
		addTextField.setBounds(170, 141, 191, 32);
		contentPane.add(addTextField);
		
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				String add = addTextField.getText();
				if(name.length() == 0) {
					JOptionPane.showMessageDialog(null, "Invalid name");
					return;
				}
				if(add.length() == 0) {
					JOptionPane.showMessageDialog(null, "Invalid address");
					return;
				}
				
				try {
					Contact c = new Contact(name, add, app.signedInUser.getContactsPath());
					//((ContactFolder)app.signedInUser.getContactsPath()).index.writeToIndex();
					dispose();
				}catch(RuntimeException ex) {
					JOptionPane.showMessageDialog(null, "contact already exists");
					return;
				}
				
				
			}
		});
		confirm.setForeground(new Color(0, 191, 255));
		confirm.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		confirm.setBounds(63, 221, 128, 32);
		contentPane.add(confirm);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setForeground(new Color(0, 191, 255));
		btnCancel.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		btnCancel.setBounds(250, 221, 128, 32);
		contentPane.add(btnCancel);
	}

}
