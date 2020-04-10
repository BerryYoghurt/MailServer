package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;

public interface IFolder {
	/**
	 * @return the path of this folder*/
	public File getPath();
	/**
	 * @return true if the folder is empty
	 */
	public boolean isEmpty();
	/**
	 * adds an item into this folder and its index file
	 * @param item (email, contact, ..etc)  
	 */
	public <T> boolean add(T item);
	/**
	 * removes an item address whose order is give as parameter
	 * @param order
	 */
	public <T> T remove(T item);
	/**
	 * deletes the whole folder and removes it from the index file (if any)
	 * @return if deleted successfully*/
	public boolean delete();
	/**
	 * adds a list of items to the index file of the folder
	 * @param L
	 */
	void uploadToIndex(ILinkedList L); //what does this function do? --jehad
}
