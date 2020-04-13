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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;
import eg.edu.alexu.csd.datastructure.queue.AQueue;
import eg.edu.alexu.csd.datastructure.queue.IQueue;

public class Mail implements IMail{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3338519294912333094L;
	//private static final int lifetime = 30; not needed, will be used other way
	transient private IFolder containingFolder = null;// no need to save
	transient private IFolder receiversFolder = null;//no need to save, do we need index here and in att?
	transient private IFolder attFolder = null;//no need to save
	private IContact composer;//how to read or write?
	private Date date;//serial
	private String subject;//serial
	transient private SLinkedList attachements;//no need to save
	transient private DLinkedList receivers;
	transient private File metadata;//no need to save, carries the serial data
	transient private File bodyTxt;//no need to save
	
	
	public Mail(IContact from) {
		this.date = new Date();
		this.composer = from;
		containingFolder = from.getDraftPath().add(this);
		bodyTxt = new File(containingFolder.getPath(), "body.txt");
		metadata = new File(containingFolder.getPath(), "metadata.ser");
		this.attachements = new SLinkedList();
		this.receivers = new DLinkedList();
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
				//load attachements--How to get them from folder?
			}else if(f.getName().equals("receivers")) {
				//load receivers--How to get them from folder?
			}
		}
		
		return m;
	}
	
	@Override
	public void setSubject(String s) {//limit length
		if(s.length() > 20)
			throw new IllegalArgumentException("message too long");
		this.subject = s;
	}
	
	@Override
	public boolean appendBody(String s) {//keep to the end
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean copy(IFolder to) {//make a shallow copy of date, subject, composer
		//then reassign containingFolder, receiversFolder, attFolder, bodyTxt
		try {
			Mail newMail = (Mail)super.clone();
			newMail.containingFolder = to.add(newMail);
			Files.walkFileTree(this.containingFolder.getPath().toPath(), Files.copy);
			
			return true;
		}
		catch (RuntimeException e){
			return false;
		}catch(CloneNotSupportedException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public boolean move(IFolder newFolder) {
		if(this.copy(newFolder))
			return this.containingFolder.delete();
		else return false;
	}

	@Override
	public boolean addReceiver(IContact receiver) {
		receivers.add(receiver);
		if(receiversFolder == null) {
			containingFolder.add(receiver);
		}else {
			receiversFolder.add(receiver);
		}
		return true;
	}
	
	@Override
	public IContact removeReceiver(int index) {
		IContact r = (IContact)receivers.get(index);
		receivers.remove(index);
		receiversFolder.remove(r);
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
	
	public String getSubject() {
		return this.subject;
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
}

class copier implements SimpleFileVisitor<Path>{
	
}