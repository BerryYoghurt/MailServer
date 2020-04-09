package eg.edu.alexu.csd.datastructure.mailServer;

public interface ISort {
	/**
	 * @return the type of sorting
	 */
	String getType();
	/**
	 * chooses the sort to be applied
	 * @param sortNum 
	 */
	void applySort(int sortNum, IIndex index);
	void bySender(IIndex index);
	void bySubject(IIndex index);
	void byReciver(IIndex index);
	void byDate(IIndex index);
	/*
	 ...etc
	 */
}
