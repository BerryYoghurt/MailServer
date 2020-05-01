package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;

import eg.edu.alexu.csd.datastructure.linkedList.ILinkedList;

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
	public void writeToIndex();
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
	/**
	 * divide the index into pages (arrays of IMails of specific size)
	 * @param size (the desired size of pages)
	 * @return linked list of pages
	 */
	public ILinkedList setPages(int size);	//linkedlists of IMail[] each IMail[] represents a page 
	
	public File getPath();
	 
}
