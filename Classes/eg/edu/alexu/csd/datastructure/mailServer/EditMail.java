package eg.edu.alexu.csd.datastructure.mailServer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;
import eg.edu.alexu.csd.datastructure.queue.IQueue;


@SuppressWarnings("serial")
public class EditMail extends JPanel implements ActionListener {
	
	private Pattern pattern = Pattern.compile("([A-Za-z0-9_\\\\-\\\\.]+)@([A-Za-z0-9_\\\\-\\\\.].*)");
	final private Mail mail;
	private JFrame frame;
	private JPanel self = this;
	private JPanel previousPanel;
	
	private App a;
	
	private JButton addAttachement, deleteAttachement, send, save, addReceiver, removeReceiver;
	
	private JRadioButton highest, high, normal, low;
	private ButtonGroup priority;
	
	private JPanel textPanel, attPanel, receiverPanel, priorityPanel;
	
	private JLabel subjectLabel, dateLabel, priorityLabel, textLabel, attLabel, recLabel;
	
	private JTextField subject, date; 
	
	private JTextArea area;
	
	private AttList attListModel;
	private JList<Attachement> attList;
	
	private JTextField recChooser;
	private ReceiverList recListModel;
	private JList<String> receiverList;
	
	private JScrollPane textPane, attPane, receiverPane;
	
	private JFileChooser chooser;
	
	private GroupLayout selfLayout, textLayout, attLayout, receiverLayout, priorityLayout;
	
	private SwingWorker<Void,Void> saveTask, sendTask;
	
	public EditMail(Mail m, App a, JFrame f, JPanel previousPanel) {
		super();
		this.mail = m.clone();//has to clone.. so that if exited without clicking save as draft, the changes will not be permanent
		this.a = a;
		this.previousPanel = previousPanel;
		
		selfLayout = new GroupLayout(this);
		frame = f;
		


    	initialiseTextFields();
    	initialiseTextArea();
    	initialiseAttachements();
    	initialiseReceivers();
    	
    	
    	subjectLabel = new JLabel("Subject: ");
    	dateLabel = new JLabel("Date Modified: ");
    	
    	selfLayout.setHorizontalGroup(
    			selfLayout.createParallelGroup()
    			.addGroup(selfLayout.createSequentialGroup()
    					.addComponent(subjectLabel)
    					.addComponent(subject))
    			.addGroup(selfLayout.createSequentialGroup()
    					.addComponent(dateLabel)
    					.addComponent(date))
    			.addComponent(priorityPanel)
    			.addGroup(selfLayout.createSequentialGroup()
    					.addComponent(receiverPanel)
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
    			.addComponent(priorityPanel)
    			.addGroup(selfLayout.createParallelGroup()
    					.addComponent(receiverPanel)
    					.addComponent(textPanel)
    					.addComponent(attPanel)));
    	this.setLayout(selfLayout);
    	
    	frame.setVisible(true);
	}
	
	private void initialiseReceivers() {
		receiverPanel = new JPanel();
		
		recLabel = new JLabel("Reciever Email: ");
		
		recChooser = new JTextField();
		recChooser.setEditable(true);
		
		addReceiver = new JButton("Add new reciever");
		addReceiver.addActionListener(this);
		
		removeReceiver = new JButton("Remove this receiver");
		removeReceiver.addActionListener(this);
		
		recListModel = new ReceiverList(mail);
		receiverList = new JList<String>(recListModel);
		receiverList.setSelectedIndex(recListModel.getSize()==0?0:recListModel.getSize()-1);
		receiverList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		receiverPane = new JScrollPane(receiverList);
		receiverPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		receiverLayout = new GroupLayout(receiverPanel);
		receiverLayout.setHorizontalGroup(
				receiverLayout.createParallelGroup()
				.addGroup(receiverLayout.createSequentialGroup()
						.addComponent(recLabel)
						.addComponent(recChooser))
				.addComponent(receiverPane)
				.addGroup(receiverLayout.createSequentialGroup()
						.addComponent(addReceiver)
						.addComponent(removeReceiver)));
		receiverLayout.setVerticalGroup(
				receiverLayout.createSequentialGroup()
				.addGroup(receiverLayout.createParallelGroup()
						.addComponent(recLabel)
						.addComponent(recChooser))
				.addComponent(receiverPane)
				.addGroup(receiverLayout.createParallelGroup()
						.addComponent(addReceiver)
						.addComponent(removeReceiver)));
		receiverPanel.setLayout(receiverLayout);
	}

	private void initialiseAttachements() {
		attPanel = new JPanel();
		attLabel = new JLabel("Attachements:");
		
		chooser = new JFileChooser();
		
		addAttachement = new JButton("add an attachement");
    	addAttachement.addActionListener(this);
    	
    	deleteAttachement = new JButton("delete selected attachement");
    	deleteAttachement.addActionListener(this);
    	
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
    			.addGroup(attLayout.createSequentialGroup()
    					.addComponent(addAttachement)
    					.addComponent(deleteAttachement))
    			);
    	attLayout.setVerticalGroup(
    			attLayout.createSequentialGroup()
    			.addComponent(attLabel)
    			.addComponent(attPane)
    			.addGroup(attLayout.createParallelGroup()
    					.addComponent(addAttachement)
    					.addComponent(deleteAttachement)));
    	attPanel.setLayout(attLayout);
	}

	private void initialiseTextFields() {
		subject = new JTextField(mail.getSubject() == ""?"Subject":mail.getSubject(),25);
    	subject.setBounds(0, 0, 50, 50);
    	subject.setName("subject");
    	subject.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    	    	
    	date = new JTextField(mail.getDate().toString());
    	date.setName("date");//here, modified.. in view, only edit
    	date.setEditable(false);
    	
    	priority = new ButtonGroup();
    	
    	highest = new JRadioButton("HIGHEST");
    	highest.setSelected(false);
    	highest.addActionListener(this);
    	
    	high = new JRadioButton("HIGH");
    	high.setSelected(false);
    	high.addActionListener(this);
    	
    	normal = new JRadioButton("NORMAL");
    	normal.setSelected(true);
    	normal.addActionListener(this);
    	
    	low = new JRadioButton("LOW");
    	low.setSelected(false);
    	low.addActionListener(this);
    	
    	priority.add(highest);
    	priority.add(high);
    	priority.add(normal);
    	priority.add(low);
    	
    	priorityPanel = new JPanel();
    	priorityPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    	
    	priorityLabel = new JLabel("Priority: ");
    	
    	priorityLayout = new GroupLayout(priorityPanel);
    	priorityLayout.setVerticalGroup(
    			priorityLayout.createParallelGroup()
    			.addComponent(priorityLabel)
    			.addComponent(highest)
    			.addComponent(high)
    			.addComponent(normal)
    			.addComponent(low));
    	priorityLayout.setHorizontalGroup(
    			priorityLayout.createSequentialGroup()
    			.addComponent(priorityLabel)
    			.addComponent(highest)
    			.addComponent(high)
    			.addComponent(normal)
    			.addComponent(low));
    	priorityPanel.setLayout(priorityLayout);
    	}
	
	private void initialiseTextArea() {
		textPanel = new JPanel();
		textPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		
		
		send = new JButton("Send");
    	send.addActionListener(this);
    	    	    	
    	save = new JButton("Save as draft");
    	save.addActionListener(this);
		
		area = new JTextArea();
    	area.setEditable(true);
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
    			.addComponent(area)
    			.addGroup(textLayout.createSequentialGroup()
    					.addComponent(send)
    					.addComponent(save)));
    	textLayout.setVerticalGroup(
    			textLayout.createSequentialGroup()
    			.addComponent(area)
    			.addGroup(textLayout.createParallelGroup()
    					.addComponent(send)
    					.addComponent(save)));
    	textPanel.setLayout(textLayout);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == send) {
			sendTask = new SwingWorker<Void,Void>(){

				@Override
				protected Void doInBackground() throws Exception {
					 try {
							area.write(new PrintWriter(mail.getBody()));
							mail.setSubject(subject.getText());
							mail.updateDate();
							mail.saveMail();
							a.compose(mail);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					return null;
				}
				
				@Override
				public void done() {
					frame.remove(self);
					
					previousPanel.setEnabled(true);
					previousPanel.setVisible(true);
				}
			};
			
			
			sendTask.execute();
			
			if(sendTask.isDone())
				sendTask = null; //for garbage collection
		}
		else if(source == save) {
			saveTask = new SwingWorker<Void,Void>(){

				@Override
				protected Void doInBackground() throws Exception {
					 try {
							area.write(new PrintWriter(mail.getBody()));
							mail.setSubject(subject.getText());
							mail.updateDate();
							mail.saveMail();
							//closed = true;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					return null;
				}
				
				@Override
				public void done() {
					frame.remove(self);
					previousPanel.setEnabled(true);
					previousPanel.setVisible(true);
				}
			};
			
			saveTask.execute();
			if(saveTask.isDone())
				saveTask = null; //for garbage collection
		}
		else if(source == addAttachement) {
			int returnVal = chooser.showOpenDialog(frame);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				Attachement a = new Attachement(chooser.getSelectedFile());
				attListModel.add(a);
				attList.setSelectedIndex(attListModel.getSize()-1);
			}
		}
		else if(source == deleteAttachement) {
			int index = attList.getSelectedIndex();
			attListModel.remove(index);
			attList.setSelectedIndex(index == attListModel.getSize()?index-1:index);
		}
		else if(source == addReceiver) {
			String email = recChooser.getText();
			if(pattern.matcher(email).matches())
			{
				recListModel.add(email);
				receiverList.setSelectedIndex(recListModel.getSize()==0?0:recListModel.getSize()-1);
			}
		}
		else if(source == removeReceiver) {
			int index = receiverList.getSelectedIndex();
			recListModel.remove(index);
			receiverList.setSelectedIndex(recListModel.getSize()==0?0:recListModel.getSize()-1);
		}
		else if(source instanceof JRadioButton) {
			JRadioButton s = (JRadioButton)source;
			mail.setPriority(Priority.valueOf(s.getText()));
		}
	}


}



class AttList implements ListModel<Attachement>{
	private SLinkedList list;
	private ListDataListener listener;
	private Mail mail;
	
	AttList(Mail mail){
		this.mail = mail;
		this.list = (SLinkedList)mail.getAttachements();
	}
	
	public void add(Attachement o) {
		if(list.contains(o))
			return;
		try {
			o.copy(new File(mail.getDirectory()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mail.addAttachement(o);//includes adding to list and its copy
		if(listener != null)
			listener.intervalAdded(new ListDataEvent(this,ListDataEvent.INTERVAL_ADDED,this.getSize()-1,this.getSize()-1));
	}
	
	public void remove(int index) {
		mail.removeAttachement(index);
		if(listener != null)
			listener.intervalRemoved(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index,index));
	}
	
	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public Attachement getElementAt(int index) {
		return (Attachement) list.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listener = l;	
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listener = l;
	}
	
}

class ReceiverList implements ListModel<String>{

	private ListDataListener listener;
	private SLinkedList list;
	private Mail m;
	
	public ReceiverList(Mail mail) {
		m = mail;
		IQueue q = m.getReceivers();
		list = new SLinkedList();
		while(!q.isEmpty()) {
			list.add(q.dequeue());
		}
	}
	
	public void add(String s) {
		if(list.contains(s))
			return;
		m.addReceiver(s);
		list.add(s);
		if(listener!=null)
			listener.intervalAdded(new ListDataEvent(this,ListDataEvent.INTERVAL_ADDED,this.getSize()-1,this.getSize()-1));
	}

	public void remove(int index) {
		m.removeReceiver(index);
		list.remove(index);
		if(listener != null)
			listener.intervalRemoved(new ListDataEvent(this,ListDataEvent.INTERVAL_REMOVED,index,index));

	}
	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public String getElementAt(int index) {
		return (String)list.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listener = l;		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listener = l;		
	}
	
}