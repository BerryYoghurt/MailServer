package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.IOException;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;

public interface IFolder {
	/**
	 * @return the path of this folder
	 */
	public File getPath();
	/**
	 * @return true if the folder is empty
	 */
	public boolean isEmpty();
	/**
	 * adds an item into this folder and its index file
	 * @param item (email, contact, ..etc)  
	 * @return the new folder created inside this folder, could be null if the added item is 
	 * just a file
	 * @throws IOException */
	public File add(Object item);
	/**
	 * removes an item address whose order is give as parameter
	 * @param order
	 */
	public Object remove(Object item);
	/**
	 * deletes the whole folder and removes it from the index file (if any)
	 * @return if deleted successfully*/
	public boolean delete();
	/**
	 * @return number of items in the folder
	 */
	public int getSize();
	
	public DLinkedList getIndex();
	//public boolean copy(IFolder to);
}
