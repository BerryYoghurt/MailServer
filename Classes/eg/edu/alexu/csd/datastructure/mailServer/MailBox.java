package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;
import eg.edu.alexu.csd.datastructure.mailServer.App;
import eg.edu.alexu.csd.datastructure.mailServer.IFilter;
import eg.edu.alexu.csd.datastructure.mailServer.IMail;
import eg.edu.alexu.csd.datastructure.mailServer.ISort;
import eg.edu.alexu.csd.datastructure.mailServer.MailFolder;
import eg.edu.alexu.csd.datastructure.mailServer.PrioritySort;
import eg.edu.alexu.csd.datastructure.mailServer.SenderFilter;
import eg.edu.alexu.csd.datastructure.mailServer.SenderSort;
import eg.edu.alexu.csd.datastructure.mailServer.SubjectFilter;
import eg.edu.alexu.csd.datastructure.mailServer.SubjectSort;

import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class MailBox extends JFrame {
	
	private class MyListModel extends AbstractListModel<Object>{
		
		private IMail[] myList;
		
		public MyListModel(IMail[] iMails) {
			this.myList = iMails;
		}

		@Override
		public int getSize() {
			if(myList != null) return this.myList.length;
			return 0;
		}

		@Override
		public Object getElementAt(int index) {
			if(myList != null) return this.myList[index];
			return null;
		}
		
	}
	
	private JPanel contentPane;
	private App app;
	private DLinkedList indexList;
	private JTextField filterTextField;
	private MailFolder m;
	private int currentPage = 1;
	private int maxPages1 = 1; //>> for original list (index) 
	private int maxPages2 = 1; //>> for filtered list
	private boolean viewed = false; // true >> the viewed mails are filtered & sorted     false >> the viewed mail are neither filtered nor sorted  


	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inbox frame = new Inbox();
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
	public MailBox(App app, MailFolder m) {
		this.app = app;
		//this.indexList = app.signedInUser.getInboxPath().getIndex();
		maxPages1 = m.getIndex().size()/10 + 1; 
		app.setViewingOptions(m, null, null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 824, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 62, 765, 353);
		contentPane.add(scrollPane);
		
		JList<Object> list = new JList<Object>();
		list.setToolTipText("");
		list.setFont(new Font("Century Gothic", Font.PLAIN, 23));
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);		
		list.setModel(new MyListModel(app.listEmails(currentPage)));
		scrollPane.setViewportView(list);
		
		JLabel filterLabel = new JLabel("Filter by :");
		filterLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		filterLabel.setBounds(10, 10, 100, 26);
		contentPane.add(filterLabel);
		
		String[] filters = {"non","subject","sender"};
		
		JComboBox filterCB = new JComboBox(filters);
		filterCB.setForeground(new Color(25, 25, 112));
		filterCB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		filterCB.setBounds(104, 11, 83, 28);
		contentPane.add(filterCB);
		
		JLabel sortLabel = new JLabel("Sort by :");
		sortLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		sortLabel.setBounds(335, 10, 100, 26);
		contentPane.add(sortLabel);
		
		String[] sorts = {"date","sender","subject","priority"};
		
		JComboBox sortCB = new JComboBox(sorts);
		sortCB.setForeground(new Color(25, 25, 112));
		sortCB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sortCB.setBounds(420, 11, 83, 28);
		contentPane.add(sortCB);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] temp = list.getSelectedValues();
				DLinkedList d = new DLinkedList();
				for(Object o : temp) {
					d.add(o);
				}
				app.deleteEmails(d);
			}
		});
		btnDelete.setBackground(new Color(245, 245, 245));
		btnDelete.setForeground(new Color(0, 206, 209));
		btnDelete.setFont(new Font("Century Gothic", Font.PLAIN, 19));
		btnDelete.setBounds(685, 490, 100, 26);
		contentPane.add(btnDelete);
		
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//>>jehad >> view an email
			}
		});
		btnView.setBackground(new Color(245, 245, 245));
		btnView.setForeground(new Color(0, 206, 209));
		btnView.setFont(new Font("Century Gothic", Font.PLAIN, 19));
		btnView.setBounds(465, 490, 100, 26);
		contentPane.add(btnView);
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				OptionWindow o = new OptionWindow(app);
				o.setVisible(true);
			}
		});
		btnBack.setBackground(new Color(245, 245, 220));
		btnBack.setFont(new Font("Century Gothic", Font.PLAIN, 19));
		btnBack.setBounds(10, 490, 100, 26);
		contentPane.add(btnBack);
		
		JButton btnRefresh = new JButton("refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPage = 1;
				viewed = false;
				maxPages1 = m.getIndex().size()/10 + 1; 
				app.setViewingOptions(m, null, null);
				filterCB.setSelectedIndex(0);
				sortCB.setSelectedIndex(0);
				list.setModel(new MyListModel(app.listEmails(currentPage)));
			}
		});
		btnRefresh.setBackground(new Color(255, 255, 0));
		btnRefresh.setFont(new Font("Century Gothic", Font.PLAIN, 19));
		btnRefresh.setBounds(120, 490, 100, 26);
		contentPane.add(btnRefresh);
		
		
		filterTextField = new JTextField();
		filterTextField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		filterTextField.setBounds(197, 10, 128, 31);
		contentPane.add(filterTextField);
		filterTextField.setColumns(10);
		
		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filter = (String) filterCB.getSelectedItem();
				String sort = (String) sortCB.getSelectedItem();
				
				try {
					MailFolder.cleanDir(new File("system\\FILTER FOLDER"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				IFilter sf = null;
				
				if(filter.equals("subject")) {
					sf = new SubjectFilter(new File("system\\FILTER FOLDER"));
					sf.setParameter(filterTextField.getText());
				}else if (filter.equals("sender")) {
					sf = new SenderFilter(new File("system\\FILTER FOLDER"));
					sf.setParameter(filterTextField.getText());
				}
				
				ISort d = null;
				//"date","sender","subject","priority"
				if(sort.equals("sender")) {
					d = new SenderSort();
				}else if(sort.equals("subject")) {
					d = new SubjectSort();
				}else if(sort.equals("priority")) {
					d = new PrioritySort();
				}
				
				if(sf != null || d != null) {
					viewed = true;
				}
				
				app.setViewingOptions(m, sf, d);
				currentPage = 1;
				list.setModel(new MyListModel(app.listEmails(currentPage)));
				
			}
		});
		btnApply.setForeground(new Color(0, 206, 209));
		btnApply.setFont(new Font("Century Gothic", Font.PLAIN, 19));
		btnApply.setBackground(new Color(245, 245, 245));
		btnApply.setBounds(560, 10, 100, 26);
		contentPane.add(btnApply);
		
		JButton btnRemove = new JButton("remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				viewed = false;
				currentPage = 1;
				app.setViewingOptions(m, null, null);
				filterCB.setSelectedIndex(0);
				sortCB.setSelectedIndex(0);
				list.setModel(new MyListModel(app.listEmails(currentPage)));
				
			}
		});
		btnRemove.setForeground(new Color(0, 206, 209));
		btnRemove.setFont(new Font("Century Gothic", Font.PLAIN, 19));
		btnRemove.setBackground(new Color(245, 245, 245));
		btnRemove.setBounds(670, 10, 115, 26);
		contentPane.add(btnRemove);
		
		JButton btnMove = new JButton("move");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnMove.setForeground(new Color(0, 206, 209));
		btnMove.setFont(new Font("Century Gothic", Font.PLAIN, 19));
		btnMove.setBackground(new Color(245, 245, 245));
		btnMove.setBounds(575, 490, 100, 26);
		btnMove.setVisible(false);
		contentPane.add(btnMove);
		
		JLabel page = new JLabel("1");
		page.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
		page.setHorizontalAlignment(SwingConstants.CENTER);
		page.setBounds(379, 431, 56, 26);
		contentPane.add(page);
		
		JButton rightPage = new JButton(">");
		rightPage.setBackground(new Color(245, 245, 245));
		rightPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( (!viewed && currentPage < maxPages1) || (viewed && currentPage < maxPages2) ) {
					currentPage++;
					page.setText(Integer.toString(currentPage));
					//get page & view it;
					list.setModel(new MyListModel(app.listEmails(currentPage)));
				}
			}
		});
		rightPage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rightPage.setBounds(434, 430, 56, 30);
		contentPane.add(rightPage);
		
		JButton leftPage = new JButton("<");
		leftPage.setBackground(new Color(245, 245, 245));
		leftPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentPage > 1) {
					currentPage--;
					page.setText(Integer.toString(currentPage));
					//get page & view it;
					list.setModel(new MyListModel(app.listEmails(currentPage)));
				}
			}
		});
		leftPage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		leftPage.setBounds(326, 431, 56, 30);
		contentPane.add(leftPage);
		
		/*new AbstractListModel<Object>() {
			String[] values = new String[] {"manar", "roaa", "adham", "amr", "samah", "amal", "amany", "mohammed", "abeer", "ola", "azza", "grandma", "grandpa"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		}*/
	}
}