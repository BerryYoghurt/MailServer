package eg.edu.alexu.csd.datastructure.mailServer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class ViewMail extends JPanel implements ActionListener, Closeable{
	
	final private Mail mail;
	private JDialog frame;
	
	private GroupLayout selfLayout, textLayout, attLayout, recLayout;
	
	private JPanel textPanel, attPanel, recPanel;
	
	private JScrollPane attPane, recPane, textPane;
	
	private AttList attListModel;
	private JList<Attachement> attList;
	private JButton viewAtt;
	
	private JList<String> recList;
	private ReceiverList recListModel;
	
	private JTextField subject, date, from, priority;
	private JLabel subjectLabel, dateLabel, priorityLabel, fromLabel, attLabel, recLabel;
	
	private JTextArea area;
	
	public ViewMail(Mail m, App a, JFrame f) {
		super();
		this.mail = m;
		
		selfLayout = new GroupLayout(this);
		frame = new JDialog(f);


    	initialiseTextFields();
    	initialiseTextArea();
    	initialiseAttachements();
    	initialiseReceivers();
    	initialiseFrame();
    	
    	
    	subjectLabel = new JLabel("Subject: ");
    	dateLabel = new JLabel("Date Modified: ");
    	priorityLabel = new JLabel("Priority: ");
    	fromLabel = new JLabel("From: ");
    	
    	selfLayout.setHorizontalGroup(
    			selfLayout.createParallelGroup()
    			.addGroup(selfLayout.createSequentialGroup()
    					.addComponent(subjectLabel)
    					.addComponent(subject))
    			.addGroup(selfLayout.createSequentialGroup()
    					.addComponent(dateLabel)
    					.addComponent(date))
    			.addGroup(selfLayout.createParallelGroup()
    					.addComponent(fromLabel)
    					.addComponent(from))
    			.addGroup(selfLayout.createSequentialGroup()
    					.addComponent(priorityLabel)
    					.addComponent(priority))
    			.addGroup(selfLayout.createSequentialGroup()
    					.addComponent(recPanel)
    					.addComponent(textPanel)
    					.addComponent(attPanel)));
    	selfLayout.setVerticalGroup(
    			selfLayout.createSequentialGroup()
    			.addGroup(selfLayout.createParallelGroup()
    					.addComponent(subjectLabel)
    					.addComponent(subject))
    			.addGroup(selfLayout.createParallelGroup()
    					.addComponent(dateLabel)
    					.addComponent(date))
    			.addGroup(selfLayout.createParallelGroup()
    					.addComponent(fromLabel)
    					.addComponent(from))
    			.addGroup(selfLayout.createSequentialGroup()
    					.addComponent(priorityLabel)
    					.addComponent(priority))
    			.addGroup(selfLayout.createParallelGroup()
    					.addComponent(recPanel)
    					.addComponent(textPanel)
    					.addComponent(attPanel)));
    	this.setLayout(selfLayout);
	}
	
	
	private void initialiseFrame() {
		frame.add(this);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		frame.pack();
    	frame.setVisible(true);
	}


	private void initialiseReceivers() {
		recPanel = new JPanel();
		recLabel = new JLabel("Receivers");
		
		recListModel = new ReceiverList(mail);
		recList = new JList<String>(recListModel);
		recList.setSelectedIndex(recListModel.getSize()-1);
		recList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		recPane = new JScrollPane(recList);
		recPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		recLayout = new GroupLayout(recPanel);
		recLayout.setHorizontalGroup(
				recLayout.createParallelGroup()
				.addComponent(recLabel)
				.addComponent(recPane));
		recLayout.setVerticalGroup(
				recLayout.createSequentialGroup()
				.addComponent(recLabel)
				.addComponent(recPane));
		recPanel.setLayout(recLayout);
	}


	private void initialiseAttachements() {
		attPanel = new JPanel();
		viewAtt = new JButton("View Attachement");
		attLabel = new JLabel("Attachements");
		
    	attListModel = new AttList(mail);
    	attList = new JList<Attachement>(attListModel);
    	attList.setSelectedIndex(0);
    	attList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	
    	attPane = new JScrollPane(attList);
    	attPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	
    	attLayout = new GroupLayout(attPanel);
    	attLayout.setHorizontalGroup(
    			attLayout.createParallelGroup()
    			.addComponent(attLabel)
    			.addComponent(attPane)
    			.addComponent(viewAtt)
    			);
    	attLayout.setVerticalGroup(
    			attLayout.createSequentialGroup()
    			.addComponent(attLabel)
    			.addComponent(attPane)
    			.addComponent(viewAtt));
    	attPanel.setLayout(attLayout);		
	}


	private void initialiseTextArea() {
		textPanel = new JPanel();
		
		area = new JTextArea();
    	area.setEditable(false);
    	area.setLineWrap(true);
    	area.setWrapStyleWord(true);
    	area.setLocation(10,20);
    	textPane = new JScrollPane(area);
    	textPane.setBorder(BorderFactory.createLineBorder(Color.black,2));
    	textPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	try(BufferedReader rdr = new BufferedReader(new FileReader(mail.getBody()))){//initialise area with current body
    		while(rdr.ready())
    			area.append(rdr.readLine()+System.lineSeparator());
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	textLayout = new GroupLayout(textPanel);
    	textLayout.setHorizontalGroup(
    			textLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
    			.addComponent(area));
    	textLayout.setVerticalGroup(
    			textLayout.createSequentialGroup()
    			.addComponent(area));
    	textPanel.setLayout(textLayout);
		
	}


	private void initialiseTextFields() {
		subject = new JTextField(mail.getSubject());
    	subject.setName("subject");
    	subject.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    	subject.setEditable(false);
    	
    	date = new JTextField(mail.getDate().toString());
    	date.setName("date");//here, modified.. in view, only edit
    	date.setEditable(false);
    	
    	from = new JTextField(mail.getSenderName()+"<"+mail.getSenderAddress()+">");
    	from.setEditable(false);
    	
    	priority = new JTextField(mail.getPriority().toString());
    	priority.setEditable(false);
	}


	@Override
	public void close() throws IOException {
		frame.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int index = attList.getSelectedIndex();
		Attachement a = attListModel.getElementAt(index);
		a.view();
	}

}
