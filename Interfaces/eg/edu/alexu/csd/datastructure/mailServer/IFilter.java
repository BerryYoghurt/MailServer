package eg.edu.alexu.csd.datastructure.mailServer;

public interface IFilter {
	/**
	 * chooses the filter to be applied
	 * @param line a line read from index file  
	 */
	public boolean applyFilter(String line);
	
}
