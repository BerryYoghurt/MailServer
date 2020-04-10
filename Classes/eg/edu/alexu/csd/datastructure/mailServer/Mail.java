package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.util.Date;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;
import eg.edu.alexu.csd.datastructure.queue.IQueue;

public class Mail implements IMail{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3338519294912333094L;
	//private static final int lifetime = 30; not needed, will be used other way
	transient private IFolder containingFolder = null;
	transient private IFolder receiversFolder = null;
	transient private IFolder attFolder = null;
	private IContact composer;
	private Date date;
	private String subject;
	transient private SLinkedList attachements;
	private File bodyTxt;
	
	
	public Mail(IContact from) {
		this.date = new Date();
		this.composer = from;
		containingFolder = from.getDraftPath().add(this);
		bodyTxt = new File(containingFolder.getPath(), "body.txt");
		this.attachements = new SLinkedList();
	}
	
	public Mail() {
		
	}
	
	@Override
	public void setSubject(String s) {
		this.subject = s;
	}
	
	@Override
	public boolean appendBody(String s) {//keep to the end
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean copy(IFolder newFolder) {
		try {
			Mail newMail = (Mail)super.clone();
			newMail.containingFolder = newFolder.add(newMail);
			newMail.bodyTxt = new File(newMail.containingFolder.getPath(), "body.txt");
			FileWriter w = new FileWriter(newMail.bodyTxt);
			FileReader r = new FileReader(this.bodyTxt);
			r.transferTo(w);//jre10
			w.close();
			r.close();
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
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public IContact removeReceiver(int index) {
		// TODO Auto-generated method stub
		return null;
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
		return false;
	}
	@Override
	public IAttachement removeAttachement(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ILinkedList getAttachements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQueue getReceivers() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Date getDate() {
		return this.date;
	}

	@Override
	public IContact getSender() {
		return this.composer;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException{
		
	}
}
