package eg.edu.alexu.csd.datastructure.mailServer;

public interface IFilter {
	/**
	 * @return the type of this filter
	 */
	String getType();
	/**
	 * chooses the filter to be applied
	 * @param filterNum 
	 */
	void applyFilter(int filterNum, IIndex index);
	void bySender(IIndex index);
	void bySubject(IIndex index);
	void byReciver(IIndex index);
	/*
	 . . . etc
	 */
}
