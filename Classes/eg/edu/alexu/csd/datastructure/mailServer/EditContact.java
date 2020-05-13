package eg.edu.alexu.csd.datastructure.mailServer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;
import eg.edu.alexu.csd.datastructure.mailServer.Contact;
import eg.edu.alexu.csd.datastructure.mailServer.ContactFolder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditContact extends JFrame {

	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField textField;
	private App app;



	/**
	 * Create the frame.
	 */
	public EditContact(Contact current, App app) {
		this.app = app;
		String[] arr = current.getAddresses();
		for(String str : arr) {
			System.out.println(str);
		}
		System.out.println();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nameLabel = new JLabel("Name : ");
		nameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		nameLabel.setBounds(61, 47, 78, 36);
		contentPane.add(nameLabel);
		
		nameTextField = new JTextField(current.getName());
		nameTextField.setBounds(145, 47, 264, 36);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel lblAddresses = new JLabel("Addresses :");
		lblAddresses.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		lblAddresses.setBounds(24, 126, 125, 36);
		contentPane.add(lblAddresses);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(145, 126, 264, 115);
		contentPane.add(scrollPane);
		
		JList list = new JList(current.getAddresses());
		scrollPane.setViewportView(list);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(145, 276, 264, 36);
		contentPane.add(textField);
		
		JLabel lblAdd = new JLabel("add new address");
		lblAdd.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblAdd.setBounds(14, 276, 125, 36);
		contentPane.add(lblAdd);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String address = textField.getText();
				if(address.length() != 0) {
					current.setAddress(address);
					if(!current.setAddress(address)) {
						JOptionPane.showMessageDialog(null, "existing address");
					}
				}
		}});
		addButton.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		addButton.setBounds(425, 276, 160, 36);
		contentPane.add(addButton);
		
		JButton btnDeleteSelected = new JButton("delete selected");
		btnDeleteSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = list.getSelectedIndex();
				if(selected != -1) {
					current.removeAddress(selected);
					list.setListData(current.getAddresses());
				}
			}
		});
		btnDeleteSelected.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnDeleteSelected.setBounds(425, 126, 160, 36);
		contentPane.add(btnDeleteSelected);
		
		JButton refreshButton = new JButton("refresh");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setListData(current.getAddresses());
			}
		});
		refreshButton.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		refreshButton.setBounds(61, 359, 106, 31);
		contentPane.add(refreshButton);
		
		JButton btnConfirm = new JButton("confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = nameTextField.getText();
				if(newName.length() == 0) {
					JOptionPane.showMessageDialog(null, "Invalid name");
					return;
				}
				if(!current.getName().equals(newName)) {
					String[] emails = current.getAddresses();
					ContactFolder folder = (ContactFolder) app.signedInUser.getContactsPath();
					folder.remove(current.getName());
					Contact c = new Contact(newName,emails[0],folder);
					for(int i = 1; i< emails.length; i++) {
						c.setAddress(emails[i]);
					}
				}
				dispose();
			}
		});
		btnConfirm.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		btnConfirm.setBounds(242, 359, 106, 31);
		contentPane.add(btnConfirm);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		btnCancel.setBounds(430, 359, 106, 31);
		contentPane.add(btnCancel);
	}
}