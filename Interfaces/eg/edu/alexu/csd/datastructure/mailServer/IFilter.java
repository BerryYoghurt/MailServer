package eg.edu.alexu.csd.datastructure.mailServer;

import eg.edu.alexu.csd.datastructure.linkedList.DoublyLinkedList;

public interface IFilter {
	/**
	 * chooses the filter to be applied
	 * @param line a line read from index file  
	 */
	public DoublyLinkedList applyFilter(DoublyLinkedList list);
	/**
	 * sets the field of filtering
	 * @param parameter
	 */
	public void setParameter(String parameter);
}
