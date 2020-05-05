package eg.edu.alexu.csd.datastructure.mailServer;
import javax.swing.*;
import javax.swing.event.ListDataListener;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
public class Gui {
	    public static void main(String args[]){
	    	//TODO
	    	
	    }
	    /**
	     * @return true if the email is editable (in draft)
	     * 	false otherwise*/
	    private boolean editMail(Mail m) {
	    	JFrame frame = new JFrame();
	    	
	    	JTextArea area = new JTextArea(50,50);//could be changed as appropriate
	    	area.setEditable(true);
	    	area.setLineWrap(true);
	    	area.setWrapStyleWord(true);
	    	JScrollPane scrollPane = new JScrollPane(area);
	    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    	try(BufferedReader rdr = new BufferedReader(new FileReader(m.getBody()))){//initialise area with current body
	    		while(rdr.ready())
	    			area.append(rdr.readLine());
	    	} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	JLabel label = new JLabel("Attachements: ");
	    	AttList list = new AttList();
	    	//how to use linked list as a super class???
	    	JList<Attachement> attachementList = new JList<>();
	    	JScrollPane attachementPane = new JScrollPane(attachementList);
	    	attachementPane.add(label);
	    	label.setLocation(5, 0);
	    	//populate list with existing attachements
	    	JButton addAttachement = new JButton("add an attachement");
	    	JButton saveButton = new JButton("save as draft");
	    	JButton deleteAttachement = new JButton("delete selected attachement");
	    	JButton send = new JButton("send");
	    	//JButton addReceiver = new JButton(""); text field.. or list..?
	    	//remove receiver
	    	frame.add(scrollPane);
	    	frame.add(attachementPane);
	    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    	frame.addWindowListener(new java.awt.event.WindowAdapter() {
	    	    @Override
	    	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	    	        try {
						area.write(new PrintWriter(m.getBody()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    }
	    	});
	    	frame.setVisible(true);
	    	
	    	//area.getDocument().addDocumentListener(JTextArea.Acce);
	    	return false;
	    }
}

class AttList<E> extends SLinkedList implements ListModel<E>{
	
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
