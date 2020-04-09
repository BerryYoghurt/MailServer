package eg.edu.alexu.csd.datastructure.mailServer;

public interface IContact {
	/**
	 * adds a new email address for this contact
	 * @param email
	 */
	void addAddress(String email);
	/**
	 * removes an email address whose order is give as parameter
	 * @param order
	 */
	void removeAddress(int order);
	/**
	 * edits an email address whose order is give as parameter
	 * @param order
	 */
	void editAddress(int order);
	/**
	 * sets the contact's name
	 */
	void setName();
}
