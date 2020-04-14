package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	
	public static IMail loadMail(IFolder thisMailFolder) {return null;}	
	
	public boolean setPriority(Priority p);
	
	public Priority getPriority();
	
	/**
	 * appends s to the end of the body of the email
	 * maximum length of s is 256 characters (1 KB)
	 * @param s
	 * @return true if saved successfully */
	public boolean appendBody(String s);
	/**
	 * deletes part of body, seek to <b>character<\b> number n and delete k characters after it
	 * @param n offset
	 * @param k number of characters to be deleted
	 * @return if deletion successful*/
	public boolean deleteBody(long n, long k);
	/**
	 * adds text after n characters of body
	 * @param n offset
	 * @param addendum string to be added*/
	public boolean addBody(long n, String addendum);
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
	 * should be implemented
	 * @param receiverEmail
	 * 			the receiver's email
	 * @throws IOException 
	 * @throws FileNotFoundException */
	public boolean addReceiver(String receiverEmail);
	/**
	 * removes receiver #index
	 * @param index of removed receiver
	 * @throws IOException 
	 * @throws FileNotFoundException */
	public String removeReceiver(int index) ;
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
