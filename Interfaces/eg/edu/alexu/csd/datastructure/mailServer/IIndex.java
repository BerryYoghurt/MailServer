package eg.edu.alexu.csd.datastructure.mailServer;

public interface IIndex {
	/**
	 * reads the index file and uploads it to a linked list
	 * @return linked list containing all the elements of the index file
	 */
	public void readIndex();
	/**
	 * write in the index file from the list after all modifications done in it
	 * finalizes the modifications
	 */
	public void writeToIndex();
	/**
	 * adds an item to the list
	 * @param o
	 */
	public void add(IMail mail);
	/**
	 * removes an item from the list
	 * @param o
	 * @return the removed item if found, null if not found
	 */
	public Object remove(Object o);		//we still don't know how items are stored in the index file
	/**
	 * setting sort for set viewing options()
	 * @param s
	 */
	 public void setSort(ISort s);
	 
	 /**
		 * setting filter for set viewing options()
		 * @param s
		 */
	 public void setfilter(IFilter f);
	
	/**
	 * applies a certain sorting to list 
	 * the sort class specifies the sorting type (by date, by sender ..etc)
	 * @param sort (the sort class)
	 */
	public void sort();
	/**
	 * applies a certain filtering to list 
	 * the filter class specifies the filtering type (by date, by sender ..etc)
	 * @param filter (the filter class)
	 */
	public void filter();
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
}
