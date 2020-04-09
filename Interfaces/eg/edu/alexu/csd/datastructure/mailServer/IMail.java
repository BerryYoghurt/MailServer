package eg.edu.alexu.csd.datastructure.mailServer;

/**
 * Interface of Mail. Sender and date should be set in constructor*/
public interface IMail {
	
	public boolean changePath(IFolder newFolder);
	
	//public boolean setSender(IContact sender);
	/**
	 * sets and edits the receivers, it is up to the author how the queue
	 * should be implemented*/
	public boolean addReceivers(IContact receiver);

	public IContact removeReceiver(int index);
	
	public IContact[] getReceivers();
	
	public boolean addAttachement(IAttachement attachement);
	
	public IAttachement removeAttachement(int index);
	
	public IAttachement[] getAttachements();

}
