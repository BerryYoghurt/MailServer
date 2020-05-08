package eg.edu.alexu.csd.datastructure.mailServer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.jupiter.engine.config.JupiterConfiguration;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;
import eg.edu.alexu.csd.datastructure.queue.IQueue;


@SuppressWarnings("serial")
public class EditMail extends JPanel implements ActionListener, Closeable {
	
	final private Mail mail;
	private JDialog frame;
	
	private App a;
	
	private JButton addAttachement, deleteAttachement, send, save, addReceiver, removereceiver;
	
	private JPanel textPanel, attPanel, receiverPanel;
	
	private JTextField subject, date; 
	
	private JTextArea area;
	
	private AttList attListModel;
	private JList<Attachement> attList;
	
	private JDialog recChooser;
	private ReceiverList recListModel;
	private JList<String> receiverList;
	
	private JScrollPane textPane, attPane, receiverPane;
	
	private JFileChooser chooser;
	
	private GroupLayout selfLayout, textLayout, attLayout, receiverLayout;
	
	public EditMail(Mail mail, App a, JFrame f) {
		super();
		this.mail = mail.clone();
		this.a = a;
		
		selfLayout = new GroupLayout(this);
		frame = new JDialog(f);


    	initialiseTextFields();
    	initialiseTextArea();
    	initialiseAttachements();
    	initialiseReceivers();
    	initialiseFrame();
    	
    	
    	
    	selfLayout.setHorizontalGroup(
    			selfLayout.createParallelGroup()
    			.addGroup(selfLayout.createSequentialGroup()
    					.addComponent(new JLabel("Subject: "))
    					.addComponent(subject))
    			.addGroup(selfLayout.createSequentialGroup()
    					.addComponent(new JLabel("Date Modified: "))
    					.addComponent(date))
    			.addGroup(selfLayout.createSequentialGroup()
    					.addComponent(receiverPanel)
    					.addComponent(textPanel)
    					.addComponent(attPanel)));
    	selfLayout.setVerticalGroup(
    			selfLayout.createSequentialGroup()
    			.addGroup(selfLayout.createParallelGroup()
    					.addComponent(new JLabel("Subject: "))
    					.addComponent(subject))
    			.addGroup(selfLayout.createParallelGroup()
    					.addComponent(new JLabel("Date Modified: "))
    					.addComponent(date))
    			.addGroup(selfLayout.createParallelGroup()
    					.addComponent(receiverPanel)
    					.addComponent(textPanel)
    					.addComponent(attPanel)));
    	this.setLayout(selfLayout);
	}
	
	private void initialiseReceivers() {
		recChooser = new JDialog();
		
		
		recListModel = new ReceiverList(mail);
		receiverList
	}

	private void initialiseAttachements() {
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
    			.addComponent(attPane)
    			.addGroup(attLayout.createSequentialGroup()
    					.addComponent(addAttachement)
    					.addComponent(deleteAttachement))
    			);
    	attLayout.setVerticalGroup(
    			attLayout.createSequentialGroup()
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
	}
	
	private void initialiseTextArea() {
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
    	textPane.setBorder(BorderFactory.createLineBorder(Color.black,20));
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
	
	private void initialiseFrame() {
		frame.add(this);
		frame.setResizable(true);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
    	    @Override
    	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
    	        try {
					area.write(new PrintWriter(mail.getBody()));
					mail.updateDate();
					mail.saveMail();
					frame.setVisible(false);
					//closed = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    }
    	});
		
		frame.pack();
    	frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == send) {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			a.compose(mail);
		}
		else if(e.getSource() == save) {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
		else if(e.getSource() == addAttachement) {
			int returnVal = chooser.showOpenDialog(frame);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				Attachement a = new Attachement(chooser.getSelectedFile());
				attListModel.add(a);
				attList.setSelectedIndex(attListModel.getSize()-1);
			}
		}
		else if(e.getSource() == deleteAttachement) {
			int index = attList.getSelectedIndex();
			attListModel.remove(index);
			attList.setSelectedIndex(index == attListModel.getSize()?index-1:index);
		}
	}

	@Override
	public void close() throws IOException {
		frame.dispose();
	}

}



class AttList implements ListModel<Attachement>{
	private SLinkedList list;
	private ListDataListener listener;
	private Mail mail;
	
	AttList(Mail mail){
		this.list = (SLinkedList)mail.getAttachements();
	}
	
	public void add(Attachement o) {
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