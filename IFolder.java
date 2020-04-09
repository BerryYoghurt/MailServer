package eg.edu.alexu.csd.datastructure.mailServer;

public interface IFolder {
	/**
	 * @return true if the folder is empty
	 */
	boolean isEmpty();
	/**
	 * adds an item into this folder and its index file
	 * @param item (email, contact, ..etc)  
	 */
	void add(Object item);
	/**
	 * removes an item address whose order is give as parameter
	 * @param order
	 */
	void remove(int order);
	/**
	 * adds a list of items to the index file of the folder
	 * @param L
	 */
	void uploadToIndex(ILinkedList L);
}
