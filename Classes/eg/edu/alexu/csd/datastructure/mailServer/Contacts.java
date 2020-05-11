package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;
import eg.edu.alexu.csd.datastructure.mailServer.App;
import eg.edu.alexu.csd.datastructure.mailServer.CInfo;
import eg.edu.alexu.csd.datastructure.mailServer.Contact;
import eg.edu.alexu.csd.datastructure.mailServer.ContactFolder;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class Contacts extends JFrame {

	private JPanel contentPane;
	private App app;
    private class MyListModel extends AbstractListModel<Object>{
		
		private DLinkedList myList;
		
		public MyListModel(DLinkedList list) {
			this.myList = list;
		}

		@Override
		public int getSize() {
			if(myList != null) return this.myList.size();
			return 0;
		}

		@Override
		public Object getElementAt(int index) {
			if(myList != null) return this.myList.get(index);
			return null;
		}
		
	}

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Contacts frame = new Contacts(app);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	public Contacts(App app) {
		this.app=app;
		ContactFolder folder =  (ContactFolder) app.signedInUser.getContactsPath();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 725, 578);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setModel(new MyListModel(folder.getIndex()));
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 493, 305);
		contentPane.add(scrollPane_1);
		scrollPane_1.setViewportView(list);
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label.setBounds(126, 352, 205, 31);
		contentPane.add(label);
		
		
		JButton btnNewButton = new JButton("add contact");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// window
				//folder.add(IContact);
			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		btnNewButton.setBounds(556, 10, 141, 39);
		contentPane.add(btnNewButton);
		
		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CInfo[] selected = (CInfo[]) list.getSelectedValues();	
				for(CInfo j:selected) {
					folder.remove(j.name);
				}
			}
		});
		btnDelete.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		btnDelete.setBounds(556, 73, 141, 39);
		contentPane.add(btnDelete);
		
		JButton btnEditContact = new JButton("edit contact");
		btnEditContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnEditContact.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		btnEditContact.setBounds(556, 142, 141, 39);
		contentPane.add(btnEditContact);
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			OptionWindow back = new OptionWindow(app);
			back.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		btnBack.setBounds(556, 276, 141, 39);
		contentPane.add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(124, 413, 470, 118);
		contentPane.add(scrollPane);
		JList list_1 = new JList();
		scrollPane.setViewportView(list_1);
		
		JButton btnView = new JButton("view");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CInfo[] selected = (CInfo[]) list.getSelectedValues();	
			label.setText(selected[0].name);
			Contact v = new Contact(new File(selected[0].directory));
			list_1.setListData(v.emails);
			}
		});
		btnView.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		btnView.setBounds(556, 213, 141, 39);
		contentPane.add(btnView);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(35, 352, 51, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblAddress = new JLabel("address");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAddress.setBounds(35, 414, 62, 31);
		contentPane.add(lblAddress);
		
		
	}
}
