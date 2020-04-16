package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;
import eg.edu.alexu.csd.datastructure.queue.AQueue;
import eg.edu.alexu.csd.datastructure.queue.IQueue;
import sun.security.action.GetBooleanAction;

public class Mail implements IMail{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3338519294912333094L;
	//private static final int lifetime = 30; not needed, will be used other way
	transient private IFolder containingFolder = null;// no need to save
	transient private IFolder attFolder = null;//no need to save
	transient private File receiversFile;
	private IContact composer;//how to read or write?
	private Date date;//serial
	private String subject;//serial
	private Priority p;//serial
	transient private String identifier;
	transient private SLinkedList attachements;//no need to save
	transient private DLinkedList receivers;
	transient private File metadata;//no need to save, carries the serial data
	transient private File bodyTxt;//no need to save
	
	
	public Mail(IContact from) {
		this.date = new Date();
		this.composer = from;
		this.p = Priority.NORMAL;
		containingFolder = from.getDraftPath().add(this);
		bodyTxt = new File(containingFolder.getPath(), "bodyTxt.txt");
		metadata = new File(containingFolder.getPath(), "metadata.ser");
		this.attachements = new SLinkedList();
		this.receivers = new DLinkedList();
		StringBuilder s = new StringBuilder(from.getAddresses()[0]);
		s.append(this.date.toString());
		this.identifier = s.toString();
		this.identifier.replaceAll("[ \\\\.@:]", "");
	}
	
	public Mail() {
		
	}
	
	public static IMail loadMail(IFolder thisMailFolder) {
		Mail m = new Mail();
		m.containingFolder = thisMailFolder;
		File[] list = m.containingFolder.getPath().listFiles();
		for(File f : list) {
			if(f.getName().contentEquals("metadata.ser")) {//load subject and date
				m.metadata = f;
				try {
					FileInputStream fin = new FileInputStream(m.metadata);
					ObjectInputStream in = new ObjectInputStream(fin);
					m.readObject(in);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(f.getName().equals("bodyTxt.txt")){//load body
				m.bodyTxt = f;
			}else if(f.getName().equals("attachements")){
				//m.attFolder = f; Constructor of IFolder?
				m.attachements = new SLinkedList();
				for(File temp : f.listFiles()) {
					m.attachements.add(new Attachement(temp));
				}
			}else if(f.getName().equals("receivers.txt")) {
				m.receiversFile = f;
				try(RandomAccessFile stream = new RandomAccessFile(f, "rw")){
					m.receivers = new DLinkedList();
					for(String line = stream.readLine(); line != null; line = stream.readLine()) {
						if(line.charAt(0) =='D') {
							continue;
						}
						else {
							m.receivers.add(line);
						}
					}
				} catch (FileNotFoundException e) {//SHOULD THESE EVEN BE THROWN!!
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		StringBuilder s = new StringBuilder(m.composer.getAddresses()[0]);
		s.append(m.date.toString());
		m.identifier = s.toString().replaceAll("[ \\\\.@:]", "");
		return m;
	}
	
	@Override
	public void setSubject(String s) {//limit length
		if(s.length() > 20)
			throw new IllegalArgumentException("subject too long");
		this.subject = s;
	}
	
	public String getSubject() {
		return this.subject;
	}
	

	@Override
	public File getBody() {
		return bodyTxt;
	}
	
	@Override
	public boolean copy(IFolder to) {
		return this.containingFolder.copy(to);
	}

	@Override
	public boolean move(IFolder newFolder) {
		if(this.copy(newFolder))
			return this.containingFolder.delete();
		else return false;
	}

	@Override
	public boolean addReceiver(String receiverEmail) {
		receiverEmail = receiverEmail.toLowerCase();
		receivers.add(receiverEmail);
		if(receiversFile == null) {
			receiversFile = new File(containingFolder.getPath(), "receivers.txt");
		}
		try(RandomAccessFile f = new RandomAccessFile(receiversFile, "rw")){
			f.seek(f.length());
			f.writeChars(receiverEmail);
			f.writeChars(System.lineSeparator());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public String removeReceiver(int index) {
		String r = (String)receivers.get(index);
		receivers.remove(index);
		//receiversFolder.remove(r);
		try(RandomAccessFile f = new RandomAccessFile(receiversFile, "rw")){
			for(int i = 0; i < index; i++) {
				f.readLine();
			}
			f.writeChar('D');//EMAILS SHOULD BE LOWER CASE
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	
	@Override
	public IQueue getReceivers() {
		IQueue q = new AQueue(receivers.size());
		for(Object o : receivers) {
			q.enqueue(o);
		}
		return q;
	}
	
	@Override
	public boolean addAttachement(IAttachement attachement) {
		if(this.attFolder == null) {
			attFolder = containingFolder.add(attachement);
		}
		else {
			attFolder.add(attachement);
		}
		this.attachements.add(attachement);
		return true;
	}
	
	@Override
	public IAttachement removeAttachement(int index) {
		IAttachement att = (IAttachement)attachements.get(index);
		attFolder.remove(att);
		att.delete();
		attachements.remove(index);
		return att;
	}
	
	@Override
	public ILinkedList getAttachements() {
		return this.attachements;
	}

	@Override
	public Date getDate() {
		return this.date;
	}

	@Override
	public IContact getSender() {
		return this.composer;
	}
	
	
	/**
	 * reads */
	private void writeObject(ObjectOutputStream oos) throws IOException{
		oos.defaultWriteObject();
		//write rest
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject();
		
	}

	@Override
	public boolean setPriority(Priority p) {
		this.p = p;
		return true;
	}

	@Override
	public Priority getPriority() {
		return this.p;
	}
	
	@Override
	public String toString() {		
		return this.identifier;
	}
}
