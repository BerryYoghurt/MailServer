package eg.edu.alexu.csd.datastructure.mailServer;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;
import eg.edu.alexu.csd.datastructure.queue.IQueue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Gui {
		static App a = new App();
		static boolean closed = false;
		public static void main(String args[]){
	    	//TODO
	    	
	    	if(a.signin("shholmes", "awesome34")) {
	    		Mail m = new Mail(App.db.loadUser("shholmes"));
	    		m.saveMail();
	    		editMail(m);
	    	}
	    }
		
	    /**@param m
	     * 	mail, cannot be null
	     * @return edited mail
	     * */
	    private static Mail editMail(Mail mail) {
	    	final Mail m = mail.clone();
	    	JFrame frame = new JFrame();
	    	
	    	
	    	JTextField subject = new JTextField(m.getSubject() == ""?"Subject":m.getSubject(),25);
	    	subject.setBounds(0, 0, 50, 50);
	    	subject.setName("Subject: ");
	    	subject.setBorder(BorderFactory.createLineBorder(Color.black, 2));
	    	//done
	    	
	    	JTextField date = new JTextField(m.getDate().toString());
	    	date.setName("Date Modified: ");//here, modified.. in view, only edit
	    	date.setEditable(false);
	    	//done
	    	
	    	JList<String> receiverList = new JList<String>(new MyList<String>(m.getReceivers()));
	    	JScrollPane receiversPane = new JScrollPane(receiverList);
	    	receiversPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    	
	    	JTextArea area = new JTextArea(50,50);//could be changed as appropriate
	    	area.setEditable(true);
	    	area.setLineWrap(true);
	    	area.setWrapStyleWord(true);
	    	area.setLocation(10,20);
	    	JScrollPane scrollPane = new JScrollPane(area);
	    	scrollPane.setBorder(BorderFactory.createLineBorder(Color.black,20));
	    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    	try(BufferedReader rdr = new BufferedReader(new FileReader(m.getBody()))){//initialise area with current body
	    		while(rdr.ready())
	    			area.append(rdr.readLine()+System.lineSeparator());
	    	} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	//done
	    	
	    	//JLabel label = new JLabel("Attachements: ");
	    	//label.setLocation(5, 0);
	    	//how to use linked list as a super class???
	    	JList<Attachement> attachementList = new JList<>(new MyList<Attachement>((SLinkedList)m.getAttachements()));
	    	//attachementList.setSize(25, 25);
	    	attachementList.setName("Atts");
	    	JScrollPane attachementPane = new JScrollPane(attachementList);
	    	//attachementPane.add(label);
	    	attachementPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	    	
	    	//populate list with existing attachements
	    	JFileChooser attChooser = new JFileChooser();
	    	JButton addAttachement = new JButton("add an attachement");
	    	addAttachement.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int returnVal = attChooser.showOpenDialog(frame);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						Attachement a = new Attachement(attChooser.getSelectedFile());
						m.addAttachement(a);
					}
					//add to mail and to list
				}
	    		
	    	});
	    	
	    	
	    	JButton save = new JButton("save as draft");
	    	save.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					m.saveMail();					
				}
	    		
	    	});
	    	
	    	JButton deleteAttachement = new JButton("delete selected attachement");
	    	deleteAttachement.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					m.removeAttachement(0);//TODO depends on list
				}
	    		
	    	});
	    	JButton send = new JButton("send");
	    	send.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					a.compose(m);
				}
	    		
	    	});
	    	/*JButton discard = new JButton("discard Changes");
	    	discard.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));					
				}
	    		
	    	});*///if we have time
	    	//JButton addReceiver = new JButton(""); text field.. or list..?
	    	//remove receiver
	    	//scrollPane.add(send);
	    	//scrollPane.add(saveButton);
	    	//frame.add(scrollPane);
	    	
	    	/*panels*/
	    	JPanel textPanel = new JPanel();//text area
	    	JPanel receiversPanel = new JPanel();
	    	JPanel attPanel = new JPanel();
	    	JPanel misc = new JPanel(); //subject, date(not editable), priority + all others
	    	
	    	/*Groups*/
	    	GroupLayout textLayout = new GroupLayout(textPanel);
	    	textLayout.setHorizontalGroup(
	    			textLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
	    			.addComponent(area)
	    			.addGroup(textLayout.createSequentialGroup()
	    					.addComponent(send)
	    					.addComponent(save)
	    					/*.addComponent(discard)*/));
	    	textLayout.setVerticalGroup(
	    			textLayout.createSequentialGroup()
	    			.addComponent(area)
	    			.addGroup(textLayout.createParallelGroup()
	    					.addComponent(send)
	    					.addComponent(save)
	    					/*.addComponent(discard)*/));
	    	textPanel.setLayout(textLayout);
	    	
	    	GroupLayout receiversLayout = new GroupLayout(receiversPanel);
	    	
	    	GroupLayout attLayout = new GroupLayout(attPanel);
	    	attLayout.setHorizontalGroup(
	    			attLayout.createParallelGroup()
	    			.addComponent(attachementPane)
	    			.addGroup(attLayout.createSequentialGroup()
	    					.addComponent(addAttachement)
	    					.addComponent(deleteAttachement))
	    			);
	    	attLayout.setVerticalGroup(
	    			attLayout.createSequentialGroup()
	    			.addComponent(attachementPane)
	    			.addGroup(attLayout.createParallelGroup()
	    					.addComponent(addAttachement)
	    					.addComponent(deleteAttachement)));
	    	attPanel.setLayout(attLayout);
	    	
	    	GroupLayout miscLayout = new GroupLayout(misc);
	    	miscLayout.setHorizontalGroup(
	    			miscLayout.createParallelGroup()
	    			.addComponent(subject)
	    			.addComponent(date)
	    			.addGroup(miscLayout.createSequentialGroup()
	    					.addComponent(receiversPanel)
	    					.addComponent(textPanel)
	    					.addComponent(attPanel)));
	    	miscLayout.setVerticalGroup(
	    			miscLayout.createSequentialGroup()
	    			.addComponent(subject)
	    			.addComponent(date)
	    			.addGroup(miscLayout.createParallelGroup()
	    					.addComponent(receiversPanel)
	    					.addComponent(textPanel)
	    					.addComponent(attPanel)));
	    	misc.setLayout(miscLayout);
	    	
	    	
	    	frame.add(misc);
	    	//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    	frame.addWindowListener(new java.awt.event.WindowAdapter() {
	    	    @Override
	    	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	    	        try {
						area.write(new PrintWriter(m.getBody()));
						m.saveMail();
						frame.dispose();
						closed = true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    }
	    	});
	    	frame.pack();
	    	frame.validate();
	    	frame.setVisible(true);
	    	//area.getDocument().addDocumentListener(JTextArea.Acce);
	    	return m;
	    }
	    
	   /* private JList<String> queueToList(IQueue q){
	    	JList<String> list = new JList<String>();
	    	
	    }*/
}

class MyList<E> extends SLinkedList implements ListModel<E>{
	
	MyList(SLinkedList list){
		list.resetNext();
		while(list.hasNext()) {
			this.add(list.getNext());
		}
	}
	
	MyList(IQueue q){
		while(!q.isEmpty()) {
			this.add(q.dequeue());
		}
	}
	
	@Override
	public int getSize() {
		return super.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E getElementAt(int index) {
		return (E) super.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO	
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}
	
}

