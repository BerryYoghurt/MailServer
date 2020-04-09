package eg.edu.alexu.csd.datastructure.mailServer;

public interface IIndex {
	/**
	 * uploads the index file to a list
	 */
	void uploadsIndex();
	/**
	 * divide the index into pages
	 */
	void setPages();	//linkedlists of IMail[] each IMail[] represents a page 
}
