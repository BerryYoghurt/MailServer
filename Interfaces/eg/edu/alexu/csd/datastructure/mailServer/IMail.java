package eg.edu.alexu.csd.datastructure.mailServer;

/**
 * Interface of Mail. Sender and date should be set in constructor*/
public interface IMail {
	
	/**
	 *sets the subject of the email
	 *constraints: Range
	 * @param s
	 */
	public void setSubject(String s);
	
	/**
	 * sets the body of the email
	 * constraints: Range
	 * @param s
	 */
	void setBody(String s);
	
	public boolean changePath(IFolder newFolder);
	
	/**
	 * sets and edits the receivers, it is up to the author how the queue
	 * should be implemented*/
	public boolean addReceiver(IContact receiver);

	public IContact removeReceiver(int index);
	
	public IContact[] getReceivers();
	
	public boolean addAttachement(IAttachement attachement);
	
	public IAttachement removeAttachement(int index);
	
	public IAttachement[] getAttachements();

}
