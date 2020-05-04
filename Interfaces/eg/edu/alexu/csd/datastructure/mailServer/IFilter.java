package eg.edu.alexu.csd.datastructure.mailServer;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;

public interface IFilter {
	/**
	 * chooses the filter to be applied
	 * @param line a line read from index file  
	 */
	public DLinkedList applyFilter(DLinkedList list);
	/**
	 * sets the field of filtering
	 * @param parameter
	 */
	public void setParameter(String parameter);
}
