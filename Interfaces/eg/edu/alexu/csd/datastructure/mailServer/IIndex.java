package eg.edu.alexu.csd.datastructure.mailServer;

import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;
import java.io.File;
import java.io.FileNotFoundException;
public interface IIndex {
	/**
	 * reads the index file and uploads it to a linked list
	 * @return linked list containing all the elements of the index file
	 */
	public ILinkedList readIndex();
	/**
	 * write in the index file from the list after all modifications done in it
	 * finalizes the modifications
	 */
	public void writeToIndex() throws FileNotFoundException;
	/**
	 * adds an item to the list
	 * @param o
	 */
	public void add(Object item);
	/**
	 * removes an item from the list
	 * @param o
	 * @return the removed item if found, null if not found
	 */
	public Object remove(Object o);		//we still don't know how items are stored in the index file
	/**
	 * searches for a specific item
	 * @param o
	 * @return the item if found, null if not found
	 */
	public Object find(Object o);	//we still don't know how items are stored in the index file
	/**
	 * @return number of items stored in the index
	 */
	public int getSize();
	
	public File getPath();

}
