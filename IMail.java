package eg.edu.alexu.csd.datastructure.mailServer;

public interface IMail {
	/**
	 *sets the subject of the email
	 *constraints: Range
	 * @param s
	 */
	void setSubject(String s);
	/**
	 * adds a receiver's address to the receivers list
	 * @param email
	 */
	void addReciever(String email);
	/**
	 * removes a receiver's address from the receivers list
	 * @param order
	 */
	void removeReciever(int order);
	/**
	 * sets the body of the email
	 * constraints: Range
	 * @param s
	 */
	void setBody(String s);
	/**
	 * sets the email sender
	 * @param email
	 */
	void setSender(String email);
	/**
	 * add an attachment to the attachments list
	 * @param attachment
	 */
	void addAttachment(Object attachment);
	/**
	 * add an attachment to the attachments list
	 * @param order 
	 */
	void removeAttachment(int order);
	/**
	 * @return true if the composition of the email is valid
	 */
	boolean isValid();
	/**
	 * checks if the email should be permanently deleted
	 */
	boolean isExpired(); //for trash folders //timer??
}
