package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.Serializable;
import java.util.Date;

import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;
import eg.edu.alexu.csd.datastructure.queue.IQueue;

/**
 * Interface of Mail. Sender and date should be set in constructor*/
public interface IMail extends Serializable{
	
	/**
	 *sets the subject of the email
	 *constraints: Range
	 * @param s
	 */
	public void setSubject(String s);
	
	public String getSubject();
	
	/**
	 * appends s to the end of the body of the email
	 * @param s
	 * @return true if saved successfully */
	public boolean appendBody(String s);
	/**
	 * copies the email to newFolder
	 * @param newFolder destination*/
	public boolean copy(IFolder newFolder);
	/**
	 * moves the email to newFolder. If newFolder is null, the email is deleted.
	 * @param newFolder the destination*/
	public boolean move(IFolder newFolder);
	
	/**
	 * sets and edits the receivers, it is up to the author how the queue
	 * should be implemented*/
	public boolean addReceiver(IContact receiver);
	/**
	 * removes receiver #index
	 * @param index of removed receiver*/
	public IContact removeReceiver(int index);
	/**
	 * @return receiver queue*/
	public IQueue getReceivers();
	/**
	 * adds attachement*/
	public boolean addAttachement(IAttachement attachement);
	/**
	 * removes specified attachement*/
	public IAttachement removeAttachement(int index);
	/**
	 * @return list of attachements*/
	public ILinkedList getAttachements();
	
	public Date getDate();
	
	public IContact getSender();
}
